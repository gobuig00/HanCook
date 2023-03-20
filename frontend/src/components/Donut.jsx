import './Donut.css'
import { Chart as ChartJS, ArcElement, Tooltip, Legend, Title } from 'chart.js';
import { Doughnut } from 'react-chartjs-2';

ChartJS.register(ArcElement, Tooltip, Legend, Title);

export const data = {
  labels: ['Carbohydrate', 'Protein', 'Fat'],
  datasets: [
    {
      // data에 탄수화물, 단백질, 지방 순서로 변수 할당
      data: [96, 2, 3],
      backgroundColor: [
        'rgba(55, 183, 255, 1)',
        'rgba(105, 181, 80, 1)',
        'rgba(218, 162, 79, 1)',
      ],
      borderColor: [
        'rgba(55, 183, 255, 1)',
        'rgba(105, 181, 80, 1)',
        'rgba(218, 162, 79, 1)',
      ],
      borderWidth: 1,
    },
  ],
};

export const options = {
  plugins: {
    title: {
      display: true,
      text: 'Nutrition',
      align: 'start',
      font: {
        size: 30,
        family: 'bold',
      },
      color: 'black',
    },
    tooltip: {
      callbacks: {
        label: function (context) {
          const value = context.parsed || 0;
          return `${value}% `;
        },
      },
    },
    legend: {
      position: 'right',
      labels: {
        boxWidth: 15,
        boxHeight: 15,
        font: {
          size: 15,
          family: 'semibold',
        }
      }
    }
  },
};

export default function Donut() {
  return <Doughnut data={data} options={options} />;
}