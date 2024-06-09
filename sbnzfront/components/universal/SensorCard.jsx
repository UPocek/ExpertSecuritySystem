import Image from "next/image";
import { useState } from "react";
import { Input } from "../ui/input";
import axios from "axios";
import { baseUrl } from "@/pages/_app";

export default function SensorCard({ sensor, name, action, editAction }) {
    const [value, setValue] = useState(0);

    function sendEvent(type, id) {
        axios.put(`${baseUrl}/api/sensor/camera_reading?type=${type}&sensorId=${id}`)
    }

    return (
        <button onClick={() => action(sensor, value)} className="shadow-md flex flex-col items-center justify-center p-4 gap-2 bg-transparent border-none hover:bg-slate-100 relative">
            {(sensor['type'] == 'sound' || sensor['type'] == 'smoke' || sensor['type'] == 'temperature' || sensor['type'] == 'humidity') &&
                <button onClick={(e) => { e.stopPropagation(); editAction(sensor) }} className="absolute top-2 right-2">
                    <Image src={'/images/edit.png'} width={16} height={16} alt="E" />
                </button>}
            <Image src={`/images/${sensor.type}.png`} alt="Sensor" width={48} height={48} />
            <p>{name}</p>
            {(sensor['type'] == 'sound' || sensor['type'] == 'smoke' || sensor['type'] == 'temperature' || sensor['type'] == 'humidity') &&
                <>
                    <div className="flex items-center gap-1 text-muted-foreground text-xs">
                        <p>{`Low: ${sensor.low}`}</p>
                        <p>{`High: ${sensor.high}`}</p>
                    </div>
                    <Input className='w-28' value={value} onChange={(e) => setValue(e.target.value)} />
                </>}
            {(sensor['type'] == 'camera') &&
                <>
                    <div className="flex items-center gap-1 text-muted-foreground text-xs">

                    </div>


                </>}
        </button>
    )
}