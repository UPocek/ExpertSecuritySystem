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
import { useRouter } from "next/router";

export default function RecommendationPage() {

    const router = useRouter();

    const [building, setBuilding] = useState({});
    const [rooms, setRooms] = useState([]);
    const [roomsConfigurations, setRoomsConfigurations] = useState([]);
    const leafRooms = useMemo(() => getLeafRooms(rooms), [rooms]);

    const [step, setStep] = useState(1);

    const [buildingPurpose, setBuildingPurpose] = useState('')

    useEffect(() => {
        axios.get(`${baseUrl}/api/room/building`)
            .then(res => {
                setRooms(res.data);
                const tempList = []
                for (let room of getLeafRooms(res.data)) {
                    tempList.push({ 'roomId': room.id, 'size': 0, 'securityLevel': 'LOW', 'type': '', 'workRequest': '', 'workValue': false, 'extraGearRequest': '', 'extraGearValue': false, 'sensors': [] })
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

        let tempConf = [...roomsConfigurations];
        for (let conf of tempConf) {
            if (conf['size'] == 0) {
                toast.error("Room size can't be 0")
                return
            }
            conf['type'] = buildingPurpose
        }

        tempConf = tempConf.map(item => {
            return {
                'roomId': item.roomId, 'size': item.size, 'securityLevel': item.securityLevel, 'type': item.type
            }
        })

        axios.post(`${baseUrl}/api/room/config`, { 'config': tempConf })
            .then(response => {
                let newConf = [];
                for (let i = 0; i < roomsConfigurations.length; i++) {
                    let conf = roomsConfigurations[i];
                    if (response.data[i].workRequest != null) {
                        conf['workRequest'] = response.data[i].workRequest[0].type
                    }
                    if (response.data[i].extraGearRequest != null) {
                        conf['extraGearRequest'] = response.data[i].extraGearRequest[0].type
                    }
                    if (response.data[i].sensors != null) {
                        conf['sensors'] = response.data[i].sensors
                    }

                    newConf.push(conf)
                }
                setStep(2);
                setRoomsConfigurations(newConf)
            })
            .catch(err => console.log(err))
    }

    function stepTwo() {
        let tempConf = [...roomsConfigurations];
        tempConf = tempConf.map(item => {
            if (item['workRequest'] == '') {
                return {
                    'roomId': item.roomId, 'extraGearResponse': { 'roomId': item.roomId, 'type': item['extraGearRequest'], 'response': item['extraGearValue'] }
                }
            } else {
                return {
                    'roomId': item.roomId, 'workResponse': { 'roomId': item.roomId, 'type': item['workRequest'], 'success': item['workValue'] }
                }
            }
        }
        )

        let finished = 0;

        axios.post(`${baseUrl}/api/room/config/requests`, { 'config': tempConf })
            .then(response => {
                console.log(response.data)
                let newConf = [];
                for (let i = 0; i < roomsConfigurations.length; i++) {
                    let conf = roomsConfigurations[i];
                    conf['workRequest'] = '';
                    conf['extraGearRequest'] = '';
                    conf['workValue'] = false;
                    conf['extraGearValue'] = false;
                    conf['sensors'] = [];

                    console.log(i)
                    console.log(response.data[i].workRequest == null)
                    console.log(response.data[i].extraGearRequest == null)
                    if (response.data[i].workRequest == null && response.data[i].extraGearRequest == null) {
                        finished += 1;
                        console.log("AAA: " + finished)
                    }
                    if (response.data[i].workRequest != null) {
                        conf['workRequest'] = response.data[i].workRequest[0].type
                    }
                    if (response.data[i].extraGearRequest != null) {
                        conf['extraGearRequest'] = response.data[i].extraGearRequest[0].type
                    }
                    if (response.data[i].sensors != null) {
                        conf['sensors'] = response.data[i].sensors
                    }

                    newConf.push(conf)
                }
                setRoomsConfigurations(newConf)
                if (finished == roomsConfigurations.length) {
                    setStep(3);
                }
            }).catch(err => console.log(err));
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
                    <div className="grid grid-cols-2 gap-4">
                        {leafRooms.map((room, index) => <RoomConfig key={room.name} index={index} room={room} roomsConfigurations={roomsConfigurations} setRoomsConfigurations={setRoomsConfigurations} />)}
                    </div>
                }

                {(step == 1 || step == 2) && <Button className='mt-8' onClick={step == 1 ? stepOne : stepTwo}>Next</Button>}
                {(step == 3) && <Button className='mt-8' onClick={() => { router.replace('/'); toast.success(`Purchase sensors are commign to building ${building.name}`) }}>Purchase</Button>}
            </div>
        </div>
    )
}