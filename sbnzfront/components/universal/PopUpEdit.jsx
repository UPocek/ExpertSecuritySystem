import { toast } from 'sonner';
import { useState } from "react"
import { Input } from "../ui/input";
import { Button } from "../ui/button";
import axios from 'axios';
import { baseUrl } from '@/pages/_app';

export default function PopUpEdit({ sensor, sensors, setSensors, setShow }) {
    const [low, setLow] = useState(sensor['low']);
    const [high, setHigh] = useState(sensor['high']);

    function editSensorConfig() {

        axios.put(`${baseUrl}/api/sensor/update_config?sensorId=${sensor['id']}&criticalLowValue=${low}&criticalHighValue=${high}`)
            .then(_res => {
                const updatedSensors = [...sensors];
                const index = updatedSensors.findIndex(s => s.id == sensor['id']);
                updatedSensors[index] = { ...sensor, low, high };
                setSensors(updatedSensors);
                setShow(false);
                toast.success(`Sensor ${sensor['type']} updated successfully`);
            })
            .catch(err => console.log(err))
    }

    return (
        <div className="fixed top-0 left-0 w-screen h-screen flex items-center justify-center bg-slate-700/[.5] z-10">

            <div className="bg-white p-5 rounded-md w-96">
                <div className='flex items-center justify-between mb-8'>
                    <p className="text-xl font-bold">Update sensor</p>
                    <button className="text-red-500" onClick={() => setShow(null)}>X</button>
                </div>

                <div className="flex items-center flex-wrap gap-4">
                    <Input value={low} onChange={(e) => setLow(e.target.value)} placeholder="Threshold for LOW" className="w-[150px]" />
                    <Input value={high} onChange={(e) => setHigh(e.target.value)} placeholder="Threshold for HIGH" className="w-[150px]" />
                    <Button onClick={editSensorConfig}>Update</Button>
                </div>
            </div>
        </div>)
}