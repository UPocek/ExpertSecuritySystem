import { useMemo } from "react";
import { AlertItem } from "../universal/AlertItem";
import Image from "next/image";

export default function Alarms({ alarms }) {

    const policeNotofied = useMemo(() => {
        return alarms.some(alarm => alarm['type'] === 'police')
    }, [alarms])

    return (
        <div className="h-1/2 overflow-y-scroll overflow-x-hidden border-b-2 border-b-slate-200">
            <div className="flex items-center justify-end gap-4">
                <div className={`flex items-center gap-2 p-3 rounded-md border-slate-400 border-2 shadow-md ${!policeNotofied ? 'grayscale-[95%] bg-slate-300' : 'bg-slate-50'}`}>
                    <Image src={'/images/police.png'} width={24} height={24} alt="Police" />
                    <p className="text-cyan-950">Police notified</p>
                </div>
            </div>

            <div className="flex flex-col gap-2 mt-4">
                {alarms.map((alarm, index) => <AlertItem key={index} title={'Alarm'} description={alarm['description']} />)}
            </div>

        </div>

    )
}