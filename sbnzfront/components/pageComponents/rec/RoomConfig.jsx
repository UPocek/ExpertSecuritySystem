import { Input } from "@/components/ui/input";
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select"

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

    return (
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
    )
}