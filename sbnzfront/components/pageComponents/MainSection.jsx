import { useState } from 'react';
import BuildingReports from "./BuildingReports";
import ProductReports from "./ProductReports";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";

export default function MainSection({ rooms, products }) {
    const [typeOfReport, setTypeOfReport] = useState('people')
    return (
        <div className="w-full p-4 border-r-2 border-r-slate-200">
            <Select value={typeOfReport} onValueChange={(newValue) => setTypeOfReport(newValue)}>
                <SelectTrigger className="w-[250px]">
                    <SelectValue placeholder="Report type" />
                </SelectTrigger>
                <SelectContent>
                    <SelectItem value={'people'}>People detection</SelectItem>
                    <SelectItem value={'product'}>Product detection</SelectItem>
                </SelectContent>
            </Select>
            {
                typeOfReport === 'people' ?
                    <BuildingReports rooms={rooms} /> :
                    <ProductReports products={products} />
            }
        </div>
    )
}