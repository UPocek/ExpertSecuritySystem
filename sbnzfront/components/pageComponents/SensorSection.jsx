import { useState } from "react"
import Alarms from "./Alarms";
import ManageSensors from "./ManageSensors";

export default function SensorSection({ rooms, alarms, sensors, setSensors }) {
    console.log(rooms);
    return (
        <div className="flex flex-col h-screen w-full p-4 border-r-2 border-r-slate-200">
            <Alarms alarms={alarms} />
            <ManageSensors rooms={rooms} sensors={sensors} setSensors={setSensors} />
        </div>
    )
}