import { baseUrl } from "@/pages/_app";
import axios from "axios";
import { toast } from "sonner";
import { Button } from "../ui/button";
import { useRouter } from "next/router";

const GridBuilding = ({ leafList }) => {

    const router = useRouter();

    const handleCellClick = (roomName) => {
        const numberOfPeople = Math.floor(Math.random() * 5) + 1;

        axios.post(`${baseUrl}/api/people_detection?location=${roomName}&numberOfPeople=${numberOfPeople}`)
            .then(response => {
                console.log(response.data)
            }).catch(error => {
                console.log(error)
            })

        toast.info(`${numberOfPeople} people entered room ${roomName}`)
    };

    const getSpanValues = (roomName) => {
        const firstLetter = roomName.charAt(0).toLowerCase();
        let rowSpan = 1;
        let colSpan = 1;

        if (['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'].includes(firstLetter)) {
            rowSpan = 2;
        } else if (['i', 'j', 'k', 'l', 'm', 'n', 'o', 'p'].includes(firstLetter)) {
            colSpan = 2;
        } else if (['q', 'r', 's', 't', 'u']) {
            rowSpan = 2;
            colSpan = 2;
        }

        return { rowSpan, colSpan }
    };

    return (
        <div className="flex flex-col items-center gap-4 h-1/2 pb-4 w-full border-b-2 border-b-slate-200">
            <h2 className="text-2xl font-bold">Your building</h2>
            <p className="text-muted-foreground mb-2">Click on a cell to simulate PersonDetectionEvent</p>
            <div className="grid grid-cols-4 border-2 border-gray-400 w-full">
                {leafList.map((room, index) => {
                    const { rowSpan, colSpan } = getSpanValues(room.name);
                    return (
                        <button
                            key={index}
                            className={`bg-white hover:bg-gray-200 active:bg-gray-400 text-xl flex items-center justify-center p-10 transition-colors duration-300 border-2 border-gray-400 ${rowSpan > 1 ? 'row-span-2' : ''
                                } ${colSpan > 1 ? 'col-span-2' : ''}`}
                            onClick={() => handleCellClick(room.name)}
                            style={{ gridRow: `span ${rowSpan} / span ${rowSpan}`, gridColumn: `span ${colSpan} / span ${colSpan}` }}
                        >
                            {room.name}
                        </button>
                    );
                })}
            </div>
            <div className="mt-auto flex justify-end items-end w-full">
                <Button onClick={() => router.push('/recommendation')}>Request recommendation</Button>
            </div>
        </div>
    );
};

export default GridBuilding;