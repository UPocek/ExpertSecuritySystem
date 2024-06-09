import { useMemo } from "react";
import { AlertItem } from "../universal/AlertItem";
import Image from "next/image";

export default function Alarms({ alarms, setAlarms }) {

    const policeNotified = useMemo(() => {
        return alarms.some(alarm => alarm['type'] === 'police')
    }, [alarms]);
    const secuirtyNotified = useMemo(() => {
        return alarms.some(alarm => alarm['type'] === 'security')
    }, [alarms]);

    function dismissAlarm(id) {
        const otherAlarms = alarms.filter(alarm => alarm.id !== id);
        setAlarms(otherAlarms);
    }

    return (
        <div className="h-1/2 overflow-y-scroll overflow-x-hidden border-b-2 border-b-slate-200">
            <div className="flex items-center justify-end gap-4">
                <div className={`flex items-center gap-2 p-3 rounded-md border-slate-400 border-2 shadow-md ${!secuirtyNotified ? 'grayscale-[95%] bg-slate-300' : 'bg-slate-50'}`}>
                    <Image src={'/images/security.png'} width={24} height={24} alt="Police" />
                    <p className="text-cyan-950">Security notified</p>
                </div>
                <div className={`flex items-center gap-2 p-3 rounded-md border-slate-400 border-2 shadow-md ${!policeNotified ? 'grayscale-[95%] bg-slate-300' : 'bg-slate-50'}`}>
                    <Image src={'/images/police.png'} width={24} height={24} alt="Police" />
                    <p className="text-cyan-950">Police notified</p>
                </div>
            </div>

            <div className="flex flex-col gap-2 mt-4">
                {alarms.map((alarm, index) => <button onClick={() => dismissAlarm(alarm.id)} className="text-left" key={index}><AlertItem title={'Alarm'} description={alarm['description']} variant={alarm['type'] == 'RED' ? 'destructive' : ''} /></button>)}
            </div>

        </div>

    )
}