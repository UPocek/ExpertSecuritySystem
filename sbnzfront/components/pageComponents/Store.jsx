import Items from "./Items";

export default function Store({ leafList }) {
    return (
        <div className="mt-4 h-1/2 pb-10">
            {
                leafList.map(room => (
                    <div className="flex flex-col flex-wrap gap-4 overflow-y-scroll overflow-x-hidden h-full mt-4" key={room.id}>
                        <p className="text-xl font-bold mb-4 ml-2">{room.name}:</p>
                        <Items roomId={room.id} />
                    </div>
                ))
            }
        </div>
    )
}