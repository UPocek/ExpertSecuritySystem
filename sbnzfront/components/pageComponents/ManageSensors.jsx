import { useMemo, useState } from "react";
import { Button } from "../ui/button";
import PopUp from "../universal/PopUp";
import SensorCard from "../universal/SensorCard";
import PopUpEdit from "../universal/PopUpEdit";
import { baseUrl } from '@/pages/_app';
import { toast } from 'sonner';
import axios from "axios";

export default function ManageSensors({ building, leafRooms, sensors, setSensors }) {

    const [showAddSensor, setShowAddSensor] = useState(false);
    const [editSensor, setEditSensor] = useState(null);

    const sensorsByRoom = useMemo(() => groupSensorsByRoom(sensors), [sensors])
    function groupSensorsByRoom(sensors) {
        const groupedSensors = {};

        for (const sensor of sensors) {
            const { roomId, ...sensorData } = sensor;

            if (!groupedSensors[roomId]) {
                groupedSensors[roomId] = [];
            }

            groupedSensors[roomId].push(sensorData);
        }

        return Object.entries(groupedSensors).map(([roomId, sensors]) => ({
            roomId,
            sensors,
        }));
    }

    function fireSensor(sensor, value) {
        if (sensor['type'] == 'sound' || sensor['type'] == 'smoke' || sensor['type'] == 'temperature' || sensor['type'] == 'humidity') {
            axios.put(`${baseUrl}/api/sensor/continuous_reading?sensorId=${sensor['id']}&value=${value}`)
                .then(response => {
                    console.log(response.data)
                    toast.info(`Sensor ${sensor['type']} with id ${sensor['id']} fired successfully`);
                })
                .catch(err => console.log(err))
        } else if (sensor['type'] == 'camera') {
            axios.put(`${baseUrl}/api/sensor/camera_reading?type=${value}&sensorId=${sensor['id']}`)
                .then(response => {
                    console.log(response.data)
                    toast.info(`Sensor ${sensor['type']} with id ${sensor['id']} fired successfully`);
                })
                .catch(err => console.log(err))
        } else if (sensor['type'] == 'security') {
            axios.put(`${baseUrl}/api/sensor/security_reading?type=${value}&sensorId=${sensor['id']}`)
                .then(response => {
                    console.log(response.data)
                    toast.info(`Sensor ${sensor['type']} with id ${sensor['id']} fired successfully`);
                })
                .catch(err => console.log(err))
        } else {
            axios.put(`${baseUrl}/api/sensor/discret_reading?sensorId=${sensor['id']}`)
                .then(response => {
                    console.log(response.data)
                    toast.info(`Sensor ${sensor['type']} with id ${sensor['id']} fired successfully`);
                })
                .catch(err => console.log(err))
        }
    }

    function getRoomNameByRoomId(id) {
        try {
            return leafRooms.find(room => room.id == id)['name']
        } catch {
            return ''
        }

    }

    return (
        <>
            {showAddSensor && <PopUp building={building} leafRooms={leafRooms} setSensors={setSensors} setShow={setShowAddSensor} />}
            {editSensor != null && <PopUpEdit sensor={editSensor} sensors={sensors} setSensors={setSensors} setShow={setEditSensor} />}
            <div className=" h-1/2 w-full mt-4">
                {(leafRooms.length == 0) &&
                    <div className="text-center">
                        <h2 className="mb-4 text-2xl font-bold text-center">Before adding sensors you need to create building first!</h2>
                    </div>
                }
                {(leafRooms.length > 0 && sensors.length == 0 && !showAddSensor) &&
                    <div className="text-center">
                        <h2 className="mb-4 text-2xl font-bold text-center">{`You don't have any sensors added!`}</h2>
                        < Button onClick={() => setShowAddSensor(true)}> Add new sensor</Button >
                    </div >
                }
                {
                    (leafRooms.length > 0 && sensors.length > 0) &&
                    <div className="text-left w-full flex flex-col gap-4">
                        {sensorsByRoom.map((room) => (
                            <div className="w-full" key={room.roomId}>
                                <div className="flex items-center justify-between mb-6">
                                    <h2 className="text-2xl font-bold">{`Room ${getRoomNameByRoomId(room.roomId)} sensors:`}</h2>
                                    {(leafRooms.length > 0) &&
                                        <div className="text-center">
                                            <Button onClick={() => setShowAddSensor(true)}> Add new sensor</Button >
                                        </div>
                                    }
                                </div>
                                <div className="flex items-center gap-2 flex-wrap">
                                    {room.sensors.map((sensor, index) => (
                                        <SensorCard key={sensor.id} sensor={sensor} name={`Sensor${index + 1}`} action={fireSensor} editAction={setEditSensor} />
                                    ))}
                                </div>
                            </div>
                        ))}
                    </div>
                }
            </div >
        </>
    )
}