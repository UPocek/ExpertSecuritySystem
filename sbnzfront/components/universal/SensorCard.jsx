import Image from "next/image";
import { useState } from "react";
import { Input } from "../ui/input";
import { Label } from "@/components/ui/label"
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group"

export default function SensorCard({ sensor, name, action, editAction }) {
    const [value, setValue] = useState((sensor['type'] == 'camera' || sensor['type'] == 'security') ? "" : 0);

    return (
        <button onClick={() => action(sensor, value)} className="shadow-md flex flex-col items-center justify-center p-3 gap-1 bg-transparent border-none hover:bg-slate-100 relative">
            {(sensor['type'] == 'sound' || sensor['type'] == 'smoke' || sensor['type'] == 'temperature' || sensor['type'] == 'humidity') &&
                <button onClick={(e) => { e.stopPropagation(); editAction(sensor) }} className="absolute top-2 right-2">
                    <Image src={'/images/edit.png'} width={16} height={16} alt="E" />
                </button>
            }
            <Image src={`/images/${sensor.type}.png`} alt="Sensor" width={48} height={48} />
            <p>{name}</p>
            {(sensor['type'] == 'sound' || sensor['type'] == 'smoke' || sensor['type'] == 'temperature' || sensor['type'] == 'humidity') &&
                <>
                    <div className="flex items-center gap-1 text-muted-foreground text-xs">
                        <p>{`Low: ${sensor.low}`}</p>
                        <p>{`High: ${sensor.high}`}</p>
                    </div>
                    <Input className='w-28' value={value} onClick={(e) => { e.stopPropagation() }} onChange={(e) => setValue(e.target.value)} />
                </>
            }
            {(sensor['type'] == 'camera') &&
                <div className="flex items-center">
                    <RadioGroup defaultValue={value}
                        onValueChange={(newValue) => setValue(newValue)} className="flex items-center">
                        <div className="flex flex-col items-center space-x-2">
                            <RadioGroupItem value={'cameraDetect'} id="r1" />
                            <Label className='text-sm text-muted-foreground mt-1' htmlFor="r1">CamDet</Label>
                        </div>
                        <div className="flex flex-col items-center space-x-2">
                            <RadioGroupItem value={'detectTempWarm'} id="r2" />
                            <Label className='text-sm text-muted-foreground mt-1' htmlFor="r2">DetWar</Label>
                        </div>
                        <div className="flex flex-col items-center space-x-2">
                            <RadioGroupItem value={'detectTempCold'} id="r3" />
                            <Label className='text-sm text-muted-foreground mt-1' htmlFor="r3">DetCol</Label>
                        </div>
                        <div className="flex flex-col items-center space-x-2">
                            <RadioGroupItem value={'faceRecognitionFalse'} id="r4" />
                            <Label className='text-sm text-muted-foreground mt-1' htmlFor="r4">FaceRF</Label>
                        </div>
                        <div className="flex flex-col items-center space-x-2">
                            <RadioGroupItem value={'faceRecognitionTrue'} id="r5" />
                            <Label className='text-sm text-muted-foreground mt-1' htmlFor="r5">FaceRT</Label>
                        </div>
                    </RadioGroup>
                </div>
            }
            {
                (sensor['type'] == 'security') &&
                <div className="flex items-center">
                    <RadioGroup defaultValue={value}
                        onValueChange={(newValue) => setValue(newValue)} className="flex items-center">
                        <div className="flex flex-col items-center space-x-2">
                            <RadioGroupItem value={'securityDetect'} id="r1" />
                            <Label className='text-sm text-muted-foreground mt-1' htmlFor="r1">SecDet</Label>
                        </div>
                        <div className="flex flex-col items-center space-x-2">
                            <RadioGroupItem value={'securityFaceRecognitionTrue'} id="r2" />
                            <Label className='text-sm text-muted-foreground mt-1' htmlFor="r2">SecFRT</Label>
                        </div>
                        <div className="flex flex-col items-center space-x-2">
                            <RadioGroupItem value={'securityFaceRecognitionFalse'} id="r3" />
                            <Label className='text-sm text-muted-foreground mt-1' htmlFor="r3">SecFRF</Label>
                        </div>
                    </RadioGroup>
                </div>
            }
        </button>
    )
}