import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select"
import { toast } from "sonner";
import Store from "./Store";
import GridBuilding from "./GridBuilding";
import axios from "axios";
import { baseUrl } from "@/pages/_app";

export default function BuildingSection({ rooms, setRooms, leafRooms }) {
    const [showCreateBuilding, setShowCreateBuilding] = useState(false);
    const [createBuildingRooms, setCreateBuildingRooms] = useState([{ 'name': '', 'isContainedIn': null, 'confirmed': false }]);

    function handleFormSubmit(e, index, room) {
        e.preventDefault();
        room['confirmed'] ? updateRoom(index, e.target[`room${index}`].value, room['isContainedIn'] === null ? null : e.target.parent.value) : addRoom(e.target[`room${index}`].value, room['isContainedIn'] === null ? null : e.target.parent.value)
    }

    function addRoom(value, parent) {
        if (!value) {
            toast.error('Room must have a name :)');
            return;
        }
        if (parent === '') {
            toast.error("Choose room's parent. Their can only be one ROOT room");
            return;
        }
        if (createBuildingRooms.some(item => item['name'] == value)) {
            toast.error('Room name must be unique in building. Try another name!');
            return;
        }
        // Create a copy of the existing array
        const updatedRooms = [...createBuildingRooms];

        const lastIndex = updatedRooms.length - 1;
        updatedRooms[lastIndex] = {
            name: value,
            isContainedIn: parent,
            confirmed: true,
        };

        // Add a new empty item to the end of the array
        updatedRooms.push({ name: '', isContainedIn: '', confirmed: false });

        // Update the state with the updated array
        setCreateBuildingRooms(updatedRooms);

        toast.success(`Room with name ${value} added sucessfully`);
    }

    function updateRoom(index, value, parent) {
        if (!value) {
            toast.error('Room must have a name :)');
            return;
        }
        if (parent === '') {
            toast.error("Choose room's parent. Their can only be one ROOT room");
            return;
        }
        if (createBuildingRooms.some(item => item['name'] == value)) {
            toast.error('Room name must be unique in building. Try another name!');
            return;
        }

        const updatedRooms = [...createBuildingRooms];

        // Update the item at the specified index
        updatedRooms[index] = {
            ...updatedRooms[index],
            name: value,
            isContainedIn: parent
        };

        // Update the state with the updated array
        setCreateBuildingRooms(updatedRooms);

        toast.success(`Room with new name ${value} updated sucessfully`);
    }

    function createNewBuilding() {
        const newBuildingRooms = createBuildingRooms.filter(item => item['name']).map(item => { return { 'name': item['name'], 'isContainedIn': item['isContainedIn'] } });
        console.log(newBuildingRooms);
        axios.post(`${baseUrl}/api/room/building`, newBuildingRooms)
            .then(res => {
                console.log(res.data);
                setRooms(res.data);
            });

    }

    return (
        <>
            {
                (rooms.length == 0 && !showCreateBuilding) &&
                <div className="flex flex-col items-center justify-center gap-6 h-screen w-full p-6 border-r-2 border-r-slate-200">
                    <h2 className="mb-4 text-2xl font-bold text-center">{`You don't have buidling created!`}</h2>
                    <Button onClick={() => setShowCreateBuilding(true)}>Create one now</Button>
                </div>
            }
            {
                (rooms.length == 0 && showCreateBuilding) &&
                <div className="flex flex-col items-center justify-center gap-4 h-screen w-full p-4 border-r-2 border-r-slate-2000">
                    <h2 className="mb-4 text-2xl font-bold">{`Create a building`}</h2>
                    <div className="flex flex-col gap-2">
                        {createBuildingRooms.map((room, index) => (
                            <form className="flex items-center gap-2" onSubmit={(e) => handleFormSubmit(e, index, room)} key={index}>
                                <Input id={`room${index}`} name={`room${index}`} type="text" placeholder={`Name for room ${index + 1}`} />
                                {room['isContainedIn'] !== null &&
                                    <Select id='parent' name="parent">
                                        <SelectTrigger className="w-[180px]">
                                            <SelectValue placeholder="Parent room" />
                                        </SelectTrigger>
                                        <SelectContent>
                                            {createBuildingRooms.map((otherRooms, otherRoomIndex) => (otherRooms['confirmed'] && otherRooms['name'] != room['name']) && <SelectItem key={otherRoomIndex} value={otherRooms.name}>{otherRooms.name}</SelectItem>
                                            )}
                                        </SelectContent>
                                    </Select>}
                                <Button type="submit">{room['confirmed'] ? 'Update' : 'Add'}</Button>
                            </form>
                        ))}
                    </div>
                    <Button className='mt-6' onClick={createNewBuilding}>Submit new building</Button>
                </div>
            }
            {
                rooms.length != 0 &&
                <div className="flex flex-col items-center h-full w-full p-4 border-r-2 border-r-slate-2000">
                    <GridBuilding leafList={leafRooms} />
                    <Store rooms={leafRooms} />
                </div>
            }
        </>
    )
}