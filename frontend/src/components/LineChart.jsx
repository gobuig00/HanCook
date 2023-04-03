import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend} from 'chart.js';
import { Line } from 'react-chartjs-2';

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

function getDates() {
  const oneDay = 1000 * 60 * 60 * 24; // 1일을 밀리초로 표현
  const startDate = new Date('2023-04-01');
  const endDate = new Date('2023-03-26');
  const dates = [];

  // 날짜를 1씩 증가시면서 배열에 추가한다
  for (let i = startDate.getTime(); i >= endDate.getTime(); i -= oneDay) {
    const date = new Date(i);
    const month = date.getMonth() + 1;
    const day = date.getDate();
    const dateString = month + '/' + day;
    dates.push(dateString);
  }

  return dates;
}

export const data = (pricedata) => ({
	labels: getDates(),
	datasets: [
		{
			label: 'price',
			data: [pricedata[0].price, pricedata[1].price, pricedata[2].price, pricedata[3].price, pricedata[4].price, pricedata[5].price, pricedata[6].price],
			borderColor: 'rgb(147, 35, 35)', // Line color 변경
			borderWidth: 5, // Line 두께 변경
			// backgroundColor: 'rgba(255, 99, 132, 1)',
			backgroundColor: 'rgba(255, 255, 255, 1)',
			pointRadius: 7, // Label 점 크기를 0으로 설정하여 숨김
		}
	]
});

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
			color: '#4d820e',
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

export default function LineChart({ priceData }) {
  return <Line data={data(priceData)} options={options} />;
}
