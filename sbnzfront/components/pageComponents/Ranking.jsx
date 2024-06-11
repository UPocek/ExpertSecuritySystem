export default function Ranking({ data }) {
    return (
        <div className="mt-4">
            {data.map((item, index) =>
                <div key={item.location} className="flex items-center gap-4 p-4 mb-2 shadow-md rounded-sm">
                    <p className="text-2xl text-muted-foreground">{`${index + 1}.`}</p>
                    <div className="flex flex-col">
                        <p>{item.location}</p>
                        <p>{Math.round(item.peopleCount)}</p>
                    </div>
                </div>
            )}
        </div>
    )
}