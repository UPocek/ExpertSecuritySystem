import BuildingReports from "./BuildingReports";

export default function MainSection({ rooms }) {
    return (
        <div className="w-full p-4 border-r-2 border-r-slate-200">
            <BuildingReports rooms={rooms} />
        </div>
    )
}