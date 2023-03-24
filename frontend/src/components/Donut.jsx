import React from 'react';
import { Chart as ChartJS, ArcElement, Tooltip, Legend, Title } from 'chart.js';
import { Doughnut } from 'react-chartjs-2';

ChartJS.register(ArcElement, Tooltip, Legend, Title);

const alwaysDrawCenterText = (centerText) => ({
  id: 'alwaysDrawCenterText',
  beforeDraw: (chart) => {
    if (!centerText) return;

    const ctx = chart.ctx;
    const chartArea = chart.chartArea;
    const centerX = (chartArea.left + chartArea.right) / 2;
    const centerY = (chartArea.top + chartArea.bottom) / 2;

    ctx.restore();
    ctx.font = '16px semibold';
    ctx.textAlign = 'center';
    ctx.textBaseline = 'middle';
    ctx.fillStyle = '#000'; // Set the text color
    ctx.fillText(centerText, centerX, centerY);
    ctx.save();
  },
});

export default function Donut({ keyList, valueList, title, centerText }) {
  const data = {
    labels: keyList,
    datasets: [
      {
        data: valueList,
        backgroundColor: [
          '#37b7ff',
          '#69b550',
          '#daa24f',
        ],
        borderColor: [
          '#37b7ff',
          '#69b550',
          '#daa24f',
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
      plugins={[alwaysDrawCenterText(centerText)]}
    />
  );
}
