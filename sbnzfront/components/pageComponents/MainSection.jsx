import BuildingReports from "./BuildingReports";
import ProductReports from "./ProductReports";

export default function MainSection({ rooms, products }) {

    return (
        <div className="w-full p-4 border-r-2 border-r-slate-200">

            <BuildingReports rooms={rooms} />
            <ProductReports products={products} />

        </div>
    )
}