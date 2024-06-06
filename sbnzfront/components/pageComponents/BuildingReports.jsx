import { addDays } from "date-fns"
import { useState } from "react"
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

export default function BuildingReports({ rooms }) {

    const [typeOfReport, setTypeOfReport] = useState('');

    const [date, setDate] = useState({
        from: addDays(new Date(), -10),
        to: addDays(new Date(), 1),
    })
    const [reportForRoom, setReportForRoom] = useState('');
    const [partOfDay, setPartOfDay] = useState('');
    const [dayOfWeek, setDayOfWeek] = useState(1);

    const [chartData, setChartData] = useState({ labels: [], datasets: [{}] });

    function getReport() {

        if (typeOfReport == '') {
            toast.error('Please select a report type');
            return;
        }

        const reportData = {
            labels: ['January', 'February', 'March', 'April', 'May'],
            datasets: [
                {
                    label: 'GeeksforGeeks Line Chart',
                    data: [65, 59, 80, 81, 56],
                    fill: false,
                    borderColor: 'rgb(75, 192, 192)',
                    tension: 0.1,
                },
            ],
        };

        if (typeOfReport == 'total_daily') {
            axios.get(`${baseUrl}/api/people_detection/total_daily?location=${reportForRoom}&startDate=${date['from']}&endDate=${date['to']}`)
                .then(res => {
                    console.log(res.data);
                })
                .catch(err => console.log(err))
        } else if (typeOfReport == 'total_weekly') {
            axios.get(`${baseUrl}/api/people_detection/total_weekly`)
                .then(res => {
                    console.log(res.data);
                })
                .catch(err => console.log(err))
        } else if (typeOfReport == 'total_monthly') {
            axios.get(`${baseUrl}/api/people_detection/total_monthly`)
                .then(res => {
                    console.log(res.data);
                })
                .catch(err => console.log(err))
        } else if (typeOfReport == 'part_of_day') {
            axios.get(`${baseUrl}/api/people_detection/part_of_day`)
                .then(res => {
                    console.log(res.data);
                })
                .catch(err => console.log(err))
        } else if (typeOfReport == 'ranking') {
            axios.get(`${baseUrl}/api/people_detection/ranking`)
                .then(res => {
                    console.log(res.data);
                })
                .catch(err => console.log(err))
        } else if (typeOfReport == 'average_person_in_store') {
            axios.get(`${baseUrl}/api/people_detection/average_person_in_store`)
                .then(res => {
                    console.log(res.data);
                })
                .catch(err => console.log(err))
        } else if (typeOfReport == 'average_people_reccuring') {
            axios.get(`${baseUrl}/api/people_detection/average_people_reccuring`)
                .then(res => {
                    console.log(res.data);
                })
                .catch(err => console.log(err))
        } else if (typeOfReport == 'max_people_reccuring') {
            axios.get(`${baseUrl}/api/people_detection/max_people_reccuring`)
                .then(res => {
                    console.log(res.data);
                })
                .catch(err => console.log(err))
        } else if (typeOfReport == 'min_people_reccuring') {
            axios.get(`${baseUrl}/api/people_detection/min_people_reccuring`)
                .then(res => {
                    console.log(res.data);
                })
                .catch(err => console.log(err))
        }

        setChartData(reportData)
    }

    return (
        <div className="p-4 w-full overflow-hidden border-b-2 border-b-slate-200">
            <div className="flex flex-col gap-4 w-full">
                <Select value={typeOfReport} onValueChange={(newValue) => setTypeOfReport(newValue)}>
                    <SelectTrigger className="w-[250px]">
                        <SelectValue placeholder="Report type" />
                    </SelectTrigger>
                    <SelectContent>
                        <SelectItem value={'total_daily'}>Total daily report</SelectItem>
                        <SelectItem value={'total_weekly'}>Total weekly report</SelectItem>
                        <SelectItem value={'total_monthly'}>Total monthly report</SelectItem>

                        <SelectItem value={'part_of_day'}>Part of day trend</SelectItem>

                        <SelectItem value={'ranking'}>Ranking</SelectItem>

                        <SelectItem value={'average_person_in_store'}>Average number of people</SelectItem>

                        <SelectItem value={'average_people_reccuring'}>Average people reccuring</SelectItem>
                        <SelectItem value={'max_people_reccuring'}>Max people reccuring</SelectItem>
                        <SelectItem value={'min_people_reccuring'}>Min people reccuring</SelectItem>
                    </SelectContent>
                </Select>
                <div className="flex items-center gap-2 flex-wrap">
                    {(typeOfReport == 'total_daily' || typeOfReport == 'total_weekly' || typeOfReport == 'total_monthly' || typeOfReport == 'part_of_day' || typeOfReport == 'average_person_in_store' || typeOfReport == 'average_people_reccuring' || typeOfReport == 'max_people_reccuring' || typeOfReport == 'min_people_reccuring') &&
                        < DatePickerWithRange date={date} setDate={setDate} />}
                    {(typeOfReport == 'total_daily' || typeOfReport == 'total_weekly' || typeOfReport == 'total_monthly' || typeOfReport == 'part_of_day' || typeOfReport == 'average_people_reccuring' || typeOfReport == 'max_people_reccuring' || typeOfReport == 'min_people_reccuring') &&
                        <Select value={reportForRoom} onValueChange={(newValue) => setReportForRoom(newValue)}>
                            <SelectTrigger className="w-[160px]">
                                <SelectValue placeholder="Report for room" />
                            </SelectTrigger>
                            <SelectContent>
                                {rooms.map((room, index) => <SelectItem key={index} value={room.name}>{room.name}</SelectItem>)}
                            </SelectContent>
                        </Select>}
                    {(typeOfReport == 'average_person_in_store') &&
                        <Select value={reportForRoom} onValueChange={(newValue) => setReportForRoom(newValue)}>
                            <SelectTrigger className="w-[160px]">
                                <SelectValue placeholder="Report for room" />
                            </SelectTrigger>
                            <SelectContent>
                                {rooms.filter(room => room['isContainedIn'] == null).map((room, index) => <SelectItem key={index} value={room.name}>{room.name}</SelectItem>)}
                            </SelectContent>
                        </Select>}
                    {(typeOfReport == 'part_of_day' || typeOfReport == 'average_people_reccuring' || typeOfReport == 'max_people_reccuring' || typeOfReport == 'min_people_reccuring') &&
                        <Select value={partOfDay} onValueChange={(newValue) => setPartOfDay(newValue)}>
                            <SelectTrigger className="w-[180px]">
                                <SelectValue placeholder="Part of day" />
                            </SelectTrigger>
                            <SelectContent>
                                <SelectItem value={'morning'}>Morning</SelectItem>
                                <SelectItem value={'midday'}>Middle of the day</SelectItem>
                                <SelectItem value={'afternoon'}>Afternoon</SelectItem>
                            </SelectContent>
                        </Select>}
                    {(typeOfReport == 'average_people_reccuring' || typeOfReport == 'max_people_reccuring' || typeOfReport == 'min_people_reccuring') &&
                        <Select value={dayOfWeek} onValueChange={(newValue) => setDayOfWeek(+newValue)}>
                            <SelectTrigger className="w-[180px]">
                                <SelectValue placeholder="Report type" />
                            </SelectTrigger>
                            <SelectContent>
                                <SelectItem value={1}>Monday</SelectItem>
                                <SelectItem value={2}>Tuesday</SelectItem>
                                <SelectItem value={3}>Wednesday</SelectItem>
                                <SelectItem value={4}>Thursday</SelectItem>
                                <SelectItem value={5}>Friday</SelectItem>
                                <SelectItem value={6}>Saturday</SelectItem>
                                <SelectItem value={7}>Sunday</SelectItem>
                            </SelectContent>
                        </Select>}
                </div>
                <Button className="w-40" onClick={getReport}>Generate report</Button>
            </div>

            <div className="mt-10">
                <LineChart data={chartData} />
            </div>


        </div>)
}