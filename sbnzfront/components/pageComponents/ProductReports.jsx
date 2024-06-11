import { addDays } from "date-fns"
import { useEffect, useState } from "react"
import { DatePickerWithRange } from "../universal/DatePickerRange"
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select"
import { Button } from "../ui/button"
import axios from "axios"
import { baseUrl } from "@/pages/_app"
import LineChart from "../universal/LineChart"
import { toast } from "sonner"
import RankingProduct from "./RankingProduct"

export default function ProductReports({ products }) {
    console.log(products)
    const [typeOfReport, setTypeOfReport] = useState('');

    const [date, setDate] = useState({
        from: addDays(new Date(), -10),
        to: addDays(new Date(), 1),
    })
    const [reportForRoom, setReportForRoom] = useState('');
    const [partOfDay, setPartOfDay] = useState('');
    const [dayOfWeek, setDayOfWeek] = useState(1);

    const [chartData, setChartData] = useState({ labels: [], datasets: [{}] });


    function formatDate(date) {
        const daysOfWeek = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
        const months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

        const dayOfWeek = daysOfWeek[date.getDay()];
        const month = months[date.getMonth()];
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        const seconds = String(date.getSeconds()).padStart(2, '0');
        const year = date.getFullYear();
        const timeZoneMatch = date.toString().match(/\(([^)]+)\)$/);
        const timeZone = timeZoneMatch ? timeZoneMatch[1] : 'GMT';

        return `${dayOfWeek} ${month} ${day} ${hours}:${minutes}:${seconds} ${timeZone} ${year}`;
    }
    function getReport() {

        if (typeOfReport == '') {
            toast.error('Please select a report type');
            return;
        }

        if (typeOfReport == 'selling_trend') {
            axios.get(`${baseUrl}/api/product_detection/selling_trend?product=${reportForRoom}&startDate=${formatDate(date['from'])}&endDate=${formatDate(date['to'])}`)
                .then(res => {
                    console.log(res.data);
                    const data = res.data.toSorted((a, b) => new Date(a.startDate) - new Date(b.startDate));
                    console.log(data);
                    setChartData(
                        {
                            labels: data.map(d => {
                                let date = new Date(d['startDate']);
                                return date.toLocaleString('en-GB', {
                                    day: 'numeric',
                                    month: 'long',
                                    year: 'numeric',
                                })
                            }),
                            datasets: [
                                {
                                    data:
                                        data.map(d => d.value),
                                    label: 'selling trend'
                                }]
                        })
                })
                .catch(err => console.log(err))
        } else if (typeOfReport == 'take_return_rate') {
            axios.get(`${baseUrl}/api/product_detection/take_return_rate?product=${reportForRoom}&startDate=${formatDate(date['from'])}&endDate=${formatDate(date['to'])}`)
                .then(res => {
                    console.log(res.data);
                    const dataReturned = res.data['returned'].toSorted((a, b) => new Date(a.startDate) - new Date(b.startDate));
                    const dataTaken = res.data['taken'].toSorted((a, b) => new Date(a.startDate) - new Date(b.startDate));
                    setChartData(
                        {
                            labels: dataTaken.map(d => {
                                let date = new Date(d['startDate']);
                                return date.toLocaleString('en-GB', {
                                    day: 'numeric',
                                    month: 'long',
                                    year: 'numeric',
                                })
                            }),
                            datasets: [
                                {
                                    data:
                                        dataReturned.map(d => d.value),
                                    label: 'returned',
                                }, {
                                    data:
                                        dataTaken.map(d => d.value),
                                    label: 'taken',
                                }]
                        })
                })
                .catch(err => console.log(err))
        } else if (typeOfReport == 'most_return') {
            axios.get(`${baseUrl}/api/product_detection/most_return`)
                .then(res => {
                    console.log("AAA")
                    console.log(res.data)
                    const data = res.data.toSorted((a, b) => a.value - b.value);
                    setChartData(data)
                })
                .catch(err => console.log(err))
        } else if (typeOfReport == 'money_return_loss') {
            axios.get(`${baseUrl}/api/product_detection/money_return_loss?product=${reportForRoom}&startDate=${formatDate(date['from'])}&endDate=${formatDate(date['to'])}`)
                .then(res => {
                    console.log(res.data);
                    const data = res.data.toSorted((a, b) => new Date(a.startDate) - new Date(b.startDate));
                    setChartData(
                        {
                            labels: data.map(d => {
                                let date = new Date(d['startDate']);
                                return date.toLocaleString('en-GB', {
                                    day: 'numeric',
                                    month: 'long',
                                    year: 'numeric',
                                })
                            }),
                            datasets: [
                                {
                                    data:
                                        data.map(d => d.value),
                                    label: 'money return loss'
                                }]
                        })
                })
                .catch(err => console.log(err))
        }
    }

    return (
        <div className="p-4 w-full overflow-hidden">
            <div className="flex flex-col gap-4 w-full">
                <Select value={typeOfReport} onValueChange={(newValue) => setTypeOfReport(newValue)}>
                    <SelectTrigger className="w-[250px]">
                        <SelectValue placeholder="Product report type" />
                    </SelectTrigger>
                    <SelectContent>
                        <SelectItem value={'most_return'}>Most return</SelectItem>
                        <SelectItem value={'money_return_loss'}>Money return loss</SelectItem>
                        <SelectItem value={'selling_trend'}>Selling trend</SelectItem>

                        <SelectItem value={'take_return_rate'}>Take return rate</SelectItem>
                    </SelectContent>
                </Select>
                <div className="flex items-center gap-2 flex-wrap">
                    {(typeOfReport == 'selling_trend' || typeOfReport == 'take_return_rate' || typeOfReport == 'money_return_loss') &&
                        < DatePickerWithRange date={date} setDate={setDate} />}
                    {(typeOfReport == 'selling_trend' || typeOfReport == 'take_return_rate' || typeOfReport == 'money_return_loss') &&
                        <Select value={reportForRoom} onValueChange={(newValue) => setReportForRoom(newValue)}>
                            <SelectTrigger className="w-[160px]">
                                <SelectValue placeholder="Report for product" />
                            </SelectTrigger>
                            <SelectContent>
                                {products.map((product, index) => <SelectItem key={index} value={product.name}>{product.name}</SelectItem>)}
                            </SelectContent>
                        </Select>}
                </div>
                <Button className="w-40" onClick={getReport}>Generate report</Button>
            </div>

            <div className="mt-10">
                {typeOfReport == 'most_return' ?
                    <RankingProduct data={chartData} /> :
                    <LineChart data={chartData} />
                }
            </div>

        </div>)
}