import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend} from 'chart.js';
import { Line } from 'react-chartjs-2';

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

export const data = {
	labels: ['January', 'February', 'March', 'April', 'May', 'June'],
	datasets: [
		{
			label: 'price',
			data: [1000, 1050, 1032, 1020, 1015, 1030],
			borderColor: 'rgb(147, 35, 35)', // Line color 변경
			borderWidth: 5, // Line 두께 변경
			// backgroundColor: 'rgba(255, 99, 132, 1)',
			backgroundColor: 'rgba(255, 255, 255, 1)',
			pointRadius: 7, // Label 점 크기를 0으로 설정하여 숨김
		}
	]
};

export const options = {
	responsive: true,
	maintainAspectRatio: false, // 차트의 가로 세로 비율 유지를 해제합니다.
	plugins: {
		title: {
			display: true,
			text: ['Price fluctuation', ' '],
			align: 'start',
			font: {
				size: 30, // 제목 글씨 크기 변경
				family: 'bold', // 제목 폰트 변경
			},
			color: 'black',
		},
		tooltip: {
			callbacks: {
				label: function (context) {
					// Tooltip 내용 변경
					const value = context.parsed.y;
					return `Price: ${value} Won`;
				},
			},
		},
		legend: {
			display: false,
		},
	},
	scales: {
		x: {
			display: true,
			grid: {
				color: (context) => {
					if (context.tick.label === 'January') {
						return 'rgba(77, 130, 14, 1)';
					};
						return 'rgba(0, 0, 0, 0.2)';
					},
				lineWidth: (context) => {
					if (context.tick.label === 'January') {
						return 2; // 가장 바깥쪽 경계선 두께 설정
					};
					return 1;
				},
			},
			ticks: {
				color: 'rgba(77, 130, 14, 1)',
				font: {
					family: 'bold',
				},
			},
		},
		y: {
			display: true,
			grid: {
				color: (context) => {
					if (context.tick.label === '1,000') {
						return 'rgba(77, 130, 14, 1)';
					};
					return 'rgba(0, 0, 0, 0.2)';
				},
				lineWidth: (context) => {
					if (context.tick.label === '1,000') {
						return 2; // 가장 바깥쪽 경계선 두께 설정
					};
					return 1;
				},
			},
			ticks: {
				color: 'rgba(77, 130, 14, 1)',
				font: {
					family: 'bold',
				},
			},
		},			
	},
}

export default function LineChart() {
  return <Line data={data} options={options} />;
}
