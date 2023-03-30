import Table from 'react-bootstrap/Table';
import './Table.css';

function TableChart({ data }) {
    const createTableHeader = () => {
        if (!data.head || data.head.length === 0) {
          return null;
        }
    
        return (
          <thead>
            <tr>
              {data.head.map((column, index) => {
                const columnWithBreak = column.replace('/', '<br>');
                return (
                  <th key={index} className="key-cell head-cell">
                    <div dangerouslySetInnerHTML={{ __html: columnWithBreak }} />
                  </th>
                );
              })}
            </tr>
          </thead>
        );
    };
    

    const createTableBody = () => {
        if (!data.body) {
          return (
            <tbody>
              <tr>
                <td colSpan={data.head ? data.head.length : 1}>
                  <span className="table-no-data">데이터가 없습니다</span>
                </td>
              </tr>
            </tbody>
          );
        }
      
        return (
          <tbody>
            {Object.entries(data.body).map(([key, values], rowIndex) => (
              <tr key={rowIndex}>
                <td className="key-cell">{key}</td>
                {Array.isArray(values) ? values.map((value, cellIndex) => (
                  <td key={cellIndex} className="value-cell">{value}</td>
                )) : <td className="value-cell">{values}</td>}
              </tr>
            ))}
          </tbody>
        );
      };
  

  return (
    <Table striped bordered className="table-text">
      {createTableHeader()}
      {createTableBody()}
    </Table>
  );
}

export default function TableComponent({ head, body }) {
  const data = { head, body };

  return <TableChart data={data} />;
}
