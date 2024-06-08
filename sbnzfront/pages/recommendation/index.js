import axios from "axios";
import { useEffect, useMemo, useState } from "react";
import { baseUrl } from "../_app";
import RoomConfig from "@/components/pageComponents/rec/RoomConfig";
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select"
import { Button } from "@/components/ui/button";
import { toast } from "sonner";

export default function RecommendationPage() {

    const [building, setBuilding] = useState({});
    const [rooms, setRooms] = useState([]);
    const [roomsConfigurations, setRoomsConfigurations] = useState([]);
    const leafRooms = useMemo(() => getLeafRooms(rooms), [rooms])

    const [buildingPurpose, setBuildingPurpose] = useState('')

    useEffect(() => {
        axios.get(`${baseUrl}/api/room/building`)
            .then(res => {
                setRooms(res.data);
                const tempList = []
                for (let room of getLeafRooms(res.data)) {
                    tempList.push({ 'roomId': room.id, 'size': 0, 'securityLevel': 'LOW', 'type': '' })
                }
                setRoomsConfigurations(tempList)
                const building = (res.data).find(r => r.isContainedIn == null)
                setBuilding(building)
            })
            .catch(err => console.log(err))
    }, []);

    function getLeafRooms(allRooms) {
        const leafRooms = [];
        for (const room of allRooms) {
            const isParent = allRooms.some(
                (otherRoom) => otherRoom.isContainedIn === room.name
            );

            if (!isParent) {
                leafRooms.push(room);
            }
        }

        return leafRooms;
    }

    function stepOne() {

        if (buildingPurpose == '') {
            toast.error("Please select building purpose")
            return
        }

        const tempConf = [...roomsConfigurations];
        for (let conf of tempConf) {
            if (conf['size'] == 0) {
                toast.error("Room size can't be 0")
                return
            }
            conf['type'] = buildingPurpose
        }
        console.log(tempConf);
        axios.get(`${baseUrl}/api/room/config?roomId=${roomId}&type=${type}&size=${size}&level=${level}`)
    }

    return (
        <div className="m-auto max-w-[1560px] p-8">
            <h1 className='text-2xl mb-5'>Security Recommendation for <span className="font-bold">{building.name || ''}</span></h1>
            <div className="p-6 bg-slate-100 rounded-xl">
                <Select value={buildingPurpose} onValueChange={(newValue) => setBuildingPurpose(newValue)}>
                    <SelectTrigger className="w-[250px]">
                        <SelectValue placeholder="Building purpose" />
                    </SelectTrigger>
                    <SelectContent>
                        <SelectItem value={'work'}>Work building</SelectItem>
                        <SelectItem value={'living'}>Home building</SelectItem>
                    </SelectContent>
                </Select>

                <hr className="my-6" />

                {leafRooms.length > 0 &&
                    <div className="grid grid-cols-2">
                        {leafRooms.map((room, index) => <RoomConfig key={room.name} index={index} room={room} roomsConfigurations={roomsConfigurations} setRoomsConfigurations={setRoomsConfigurations} />)}
                    </div>
                }

                <Button className='mt-4' onClick={stepOne}>Next</Button>
            </div>
        </div>
    )
}