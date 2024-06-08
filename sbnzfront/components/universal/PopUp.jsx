import { toast } from 'sonner';
import { useState } from "react"
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select"
import { Input } from "../ui/input";
import { Button } from "../ui/button";
import axios from 'axios';
import { baseUrl } from '@/pages/_app';

export default function PopUp({ rooms, setSensors, setShow }) {

    const [sensorType, setSensorType] = useState('');
    const [sensorRoom, setSensorRoom] = useState('');

    const [low, setLow] = useState(1);
    const [high, setHigh] = useState(5);

    function addSensor() {
        if (sensorType == '' || sensorRoom == '') {
            toast.error('Please fill all fields');
            return;
        }
        if (sensorType == 'sound' || sensorType == 'smoke' || sensorType == 'temperature' || sensorType == 'humidity') {
            if (low == '' || high == '') {
                toast.error('Please fill all fields');
                return;
            }
        }
        if (low > high) {
            toast.error('Low threshold must be smaller than high threshold');
            return;
        }

        if (sensorType == 'sound' || sensorType == 'smoke' || sensorType == 'temperature' || sensorType == 'humidity') {
            axios.post(`${baseUrl}/api/sensor/continuous?sensorType=${sensorType}&roomId=${sensorRoom}`)
                .then(response => {
                    setSensors(prev => [...prev, response.data])
                    axios.put(`${baseUrl}/api/sensor/update_config?sensorId=${response.data['id']}&criticalLowValue=${low}&criticalHighValue=${high}`)
                        .then(response => {
                            setShow(false);
                        })
                        .catch(err => console.log(err))
                }).catch(err => console.log(err));
        } else {
            axios.post(`${baseUrl}/api/sensor/discret?sensorType=${sensorType}&roomId=${sensorRoom}`)
                .then(response => {
                    setSensors(prev => [...prev, response.data])
                    setShow(false);
                }).catch(err => console.log(err));
        }

        toast.success(`New ${sensorType} added successfully to room ${sensorRoom}`);

    }

    return (
        <div className="fixed top-0 left-0 w-screen h-screen flex items-center justify-center bg-slate-700/[.5] z-10">

            <div className="bg-white p-5 rounded-md w-96">
                <div className='flex items-center justify-between mb-8'>
                    <p className="text-xl font-bold">Add new sensor</p>
                    <button className="text-red-500" onClick={() => setShow(false)}>X</button>
                </div>

                <div className="flex items-center flex-wrap gap-4">
                    <Select value={sensorType} onValueChange={(newValue) => setSensorType(newValue)}>
                        <SelectTrigger className="w-[150px]">
                            <SelectValue placeholder="Sensor type" />
                        </SelectTrigger>
                        <SelectContent>
                            <SelectItem value={'sound'}>Sound sensor</SelectItem>
                            <SelectItem value={'smoke'}>Smoke sensor</SelectItem>
                            <SelectItem value={'temperature'}>Temperature sensor</SelectItem>
                            <SelectItem value={'humidity'}>Humidity sensor</SelectItem>

                            <SelectItem value={'motion'}>Motion detection</SelectItem>
                            <SelectItem value={'rfid'}>RFID scanner</SelectItem>
                            <SelectItem value={'face'}>Face detection camera</SelectItem>
                        </SelectContent>
                    </Select>
                    <Select value={sensorRoom} onValueChange={(newValue) => setSensorRoom(newValue)}>
                        <SelectTrigger className="w-[150px]">
                            <SelectValue placeholder="Add sensor to room" />
                        </SelectTrigger>
                        <SelectContent>
                            {rooms.map((room, index) => (
                                <SelectItem key={index} value={room.id}>{room.name}</SelectItem>
                            ))}
                        </SelectContent>
                    </Select>
                    {
                        (sensorType == 'sound' || sensorType == 'smoke' || sensorType == 'temperature' || sensorType == 'humidity') &&
                        <>
                            <Input value={low} onChange={(e) => setLow(e.target.value)} placeholder="Threshold for LOW" className="w-[150px]" />
                            <Input value={high} onChange={(e) => setHigh(e.target.value)} placeholder="Threshold for HIGH" className="w-[150px]" />
                        </>
                    }
                    <Button onClick={addSensor}>Confirm</Button>
                </div>
            </div>
        </div>)
}