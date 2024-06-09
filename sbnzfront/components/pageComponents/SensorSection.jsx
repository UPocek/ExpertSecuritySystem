import Alarms from "./Alarms";
import ManageSensors from "./ManageSensors";

export default function SensorSection({ building, leafRooms, alarms, setAlarms, sensors, setSensors }) {
    return (
        <div className="flex flex-col h-screen w-full p-4 border-r-2 border-r-slate-200">
            <Alarms alarms={alarms} setAlarms={setAlarms} />
            <ManageSensors building={building} leafRooms={leafRooms} sensors={sensors} setSensors={setSensors} />
        </div>
    )
}