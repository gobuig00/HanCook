import Footer from './Footer'
import Donut from './Donut'
import './Ingredient.css'
import Table from './Table'

function Ingredient() {
  return (
    <div className='background-green'>
        <Donut />
        <Table />
        <Footer />
    </div>
  );
}

export default Ingredient;