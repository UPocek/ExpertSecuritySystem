import Items from "./Items";

export default function Store({ rooms }) {
    return (
        <div className="mt-4 h-1/2">
            {
                rooms.map(room => (
                    <div className="flex flex-col gap-4 overflow-y-scroll overflow-x-hidden h-full" key={room.id}>
                        <p className="text-xl font-bold mb-4">{room.name}:</p>
                        <Items />
                    </div>
                ))
            }
        </div>
    )
}