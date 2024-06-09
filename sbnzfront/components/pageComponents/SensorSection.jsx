import Alarms from "./Alarms";
import ManageSensors from "./ManageSensors";

export default function SensorSection({ rooms, alarms, setAlarms, sensors, setSensors }) {
    return (
        <div className="flex flex-col h-screen w-full p-4 border-r-2 border-r-slate-200">
            <Alarms alarms={alarms} setAlarms={setAlarms} />
            <ManageSensors rooms={rooms} sensors={sensors} setSensors={setSensors} />
        </div>
    )
}