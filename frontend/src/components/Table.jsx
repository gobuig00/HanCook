import Table from 'react-bootstrap/Table';
import './Table.css'

function TableChart() {
  return (
    <Table striped bordered className='table-text'>
        <thead>
            <tr>
                <th>Nutrients</th>
                <th>Amount<br />(% Daily value)</th>
                <th>Per 100g</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>Calories(kcal)</td>
                <td>1(1%)</td>
                <td>1(1%)</td>
            </tr>
            <tr>
                <td>Carbohydrate(g)</td>
                <td>1(1%)</td>
                <td>1(1%)</td>
            </tr>
            <tr>
                <td>Protein(g)</td>
                <td>1(1%)</td>
                <td>1(1%)</td>
            </tr>
            <tr>
                <td>Fat(g)</td>
                <td>1(1%)</td>
                <td>1(1%)</td>
            </tr>
            <tr>
                <td>Sodium(mg)</td>
                <td>1(1%)</td>
                <td>1(1%)</td>
            </tr>
            <tr>
                <td>Cholesterol(mg)</td>
                <td>1(1%)</td>
                <td>1(1%)</td>
            </tr>
            <tr>
                <td>Sugar(g)</td>
                <td>1(1%)</td>
                <td>1(1%)</td>
            </tr>
        </tbody>
    </Table>
  );
};

export default function NutritionTable() {
    return <TableChart />;
}