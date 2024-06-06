import dynamic from 'next/dynamic';
import 'chart.js/auto';
const Line = dynamic(() => import('react-chartjs-2').then((mod) => mod.Line), {
    ssr: false,
});
const LineChart = ({ name, data }) => {
    return (
        <div className='m-auto' style={{ width: '450px', height: '240px' }}>
            <h1 className='text-2xl font-bold mb-5'>{name}</h1>
            <Line data={data} />
        </div>
    );
};
export default LineChart;