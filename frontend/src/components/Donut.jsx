import { Chart as ChartJS, ArcElement, Tooltip, Legend, Title } from 'chart.js';
import { Doughnut } from 'react-chartjs-2';

ChartJS.register(ArcElement, Tooltip, Legend, Title);

export default function Donut({ keyList, valueList, title }) {
  const data = {
    labels: keyList,
    datasets: [
      {
        data: valueList,
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

  const options = {
    maintainAspectRatio: false,
    plugins: {
      title: {
        display: true,
        text: title,
        align: 'start',
        font: {
          size: 24,
          family: 'bold',
        },
        color: '#4d820e',
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
          },
        },
      },
    },
  };

  return (
    <Doughnut
      data={data}
      options={options}
    />
  );
}

