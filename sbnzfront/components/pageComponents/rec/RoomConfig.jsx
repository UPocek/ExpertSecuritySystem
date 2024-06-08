import { Input } from "@/components/ui/input";
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select"
import { Label } from "@/components/ui/label"
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group"

export default function RoomConfig({ index, room, roomsConfigurations, setRoomsConfigurations }) {

    function updateConfigSize(e) {
        const tempConfig = [...roomsConfigurations];
        tempConfig[index]['size'] = e.target.value
        setRoomsConfigurations(tempConfig)
    }

    function updateConfigSecurityLevel(newValue) {
        const tempConfig = [...roomsConfigurations];
        tempConfig[index]['securityLevel'] = newValue;
        setRoomsConfigurations(tempConfig)
    }

    function updateWorkRequestValue(newValue) {
        const tempConfig = [...roomsConfigurations];
        tempConfig[index]['workValue'] = newValue
        setRoomsConfigurations(tempConfig)
    }

    function updateExtraGearValue(newValue) {
        const tempConfig = [...roomsConfigurations];
        tempConfig[index]['extraGearValue'] = newValue
        setRoomsConfigurations(tempConfig)
    }

    return (
        <>

            {(roomsConfigurations[index].workRequest == '' && roomsConfigurations[index].extraGearRequest == '' && roomsConfigurations[index].sensors.length > 0) &&
                <div>
                    <p className="mb-4">For room <span className="underline">{room.name}</span>  we recommend these sensors:</p>
                    <div className="flex flex-col gap-4">
                        {roomsConfigurations[index].sensors.map((sensor) => <p key={sensor}>{sensor}</p>)}
                    </div>
                </div>
            }

            {(roomsConfigurations[index].workRequest == '' && roomsConfigurations[index].extraGearRequest == '' && roomsConfigurations[index].sensors.length == 0) &&
                <div>
                    <p className="mb-4">Tell us about <span className="underline">{room.name}</span>  room:</p>
                    <div className="flex flex-col gap-4">
                        <div className="flex items-center gap-2">
                            <p className="text-sm text-muted-foreground">Room size (m2):</p>
                            <Input value={roomsConfigurations[index]['size']} onChange={updateConfigSize} className="w-[150px]" />
                        </div>
                        <div className="flex items-center gap-2">
                            <p className="text-sm text-muted-foreground">Room Security Level:</p>
                            <Select value={roomsConfigurations[index]['securityLevel']} onValueChange={(newValue) => updateConfigSecurityLevel(newValue)}>
                                <SelectTrigger className="w-[150px]">
                                    <SelectValue placeholder="Security Level" />
                                </SelectTrigger>
                                <SelectContent>
                                    <SelectItem value={'HIGH'}>HIGH</SelectItem>
                                    <SelectItem value={'MEDIUM'}>MEDIUM</SelectItem>
                                    <SelectItem value={'LOW'}>LOW</SelectItem>
                                </SelectContent>
                            </Select>
                        </div>

                    </div>
                </div>
            }
            {roomsConfigurations[index].workRequest == 'bring_electricity' &&
                <div>
                    <p className="text-xl mb-6">{room.name}: Can you provide extra electricity (220V) to this room?</p>
                    <RadioGroup defaultValue={roomsConfigurations[index].workValue}
                        onValueChange={updateWorkRequestValue} >
                        <div className="flex items-center space-x-2">
                            <RadioGroupItem value={true} id="r2" />
                            <Label htmlFor="r2">Yes, I can</Label>
                        </div>
                        <div className="flex items-center space-x-2">
                            <RadioGroupItem value={false} id="r3" />
                            <Label htmlFor="r3">No, unable to do that</Label>
                        </div>
                    </RadioGroup>
                </div>
            }
            {roomsConfigurations[index].workRequest == 'setup_app' &&
                <div>
                    <p className="text-xl mb-6">{room.name}: Can you install application to monitor whole system?</p>
                    <RadioGroup defaultValue={roomsConfigurations[index].workValue}
                        onValueChange={updateWorkRequestValue} >
                        <div className="flex items-center space-x-2">
                            <RadioGroupItem value={true} id="r2" />
                            <Label htmlFor="r2">Yes, I can</Label>
                        </div>
                        <div className="flex items-center space-x-2">
                            <RadioGroupItem value={false} id="r3" />
                            <Label htmlFor="r3">No, unable to do that</Label>
                        </div>
                    </RadioGroup>
                </div>
            }
            {roomsConfigurations[index].workRequest == 'dedicated_server_for_memory' &&
                <div>
                    <p className="text-xl mb-6">{room.name}: Do you have the space for extra memory storage <span className="text-sm text-muted-foreground">(without setup will work with limited backups)</span>?</p>
                    <RadioGroup defaultValue={roomsConfigurations[index].workValue}
                        onValueChange={updateWorkRequestValue} >
                        <div className="flex items-center space-x-2">
                            <RadioGroupItem value={true} id="r2" />
                            <Label htmlFor="r2">Yes, I can</Label>
                        </div>
                        <div className="flex items-center space-x-2">
                            <RadioGroupItem value={false} id="r3" />
                            <Label htmlFor="r3">No, unable to do that</Label>
                        </div>
                    </RadioGroup>
                </div>
            }
            {roomsConfigurations[index].extraGearRequest == 'extra_battery' &&
                <div>
                    <p className="text-xl mb-6">{room.name}: Can you affort axtra batteries in this room <span className="text-sm text-muted-foreground">(safty)</span>?</p>
                    <RadioGroup defaultValue={roomsConfigurations[index].extraGearValue}
                        onValueChange={updateExtraGearValue} >
                        <div className="flex items-center space-x-2">
                            <RadioGroupItem value={true} id="r2" />
                            <Label htmlFor="r2">Yes, I can</Label>
                        </div>
                        <div className="flex items-center space-x-2">
                            <RadioGroupItem value={false} id="r3" />
                            <Label htmlFor="r3">No, unable to do that</Label>
                        </div>
                    </RadioGroup>
                </div>
            }
            {roomsConfigurations[index].extraGearRequest == 'service_monitoring' &&
                <div>
                    <p className="text-xl mb-6">{room.name}: Can you provide service monitoring <span className="text-sm text-muted-foreground">(safty)</span>?</p>
                    <RadioGroup defaultValue={roomsConfigurations[index].extraGearValue}
                        onValueChange={updateExtraGearValue} >
                        <div className="flex items-center space-x-2">
                            <RadioGroupItem value={true} id="r2" />
                            <Label htmlFor="r2">Yes, I can</Label>
                        </div>
                        <div className="flex items-center space-x-2">
                            <RadioGroupItem value={false} id="r3" />
                            <Label htmlFor="r3">No, unable to do that</Label>
                        </div>
                    </RadioGroup>
                </div>
            }
        </>

    )
}