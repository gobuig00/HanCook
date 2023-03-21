import Footer from './Footer'
import Donut from './Donut'
import './Ingredient.css'
import Table from './Table'
import LineChart from './LineChart'
import Img from '../images/takepicture.jpg'
import AddShoppingCart from '../icons/AddShoppingCart.svg'

function Ingredient() {
  return (
    <div className='background-green'>
      <div className='ingredient-container'>
        <img src={Img} alt="" className='ingredient-image' />
        <span className='ingredient-container-right'>
          <img src={AddShoppingCart} alt="" className='add-shopping-cart-icon'/>
          <div><span className='ingredient-name'>Apple</span><br /><span className='ingredient-pronunciation'>(sa-gwa)</span></div>
        </span>
      </div>
      <Donut />
      <Table />
      <div className='line main-line'></div>
      <div className='line-chart'>
        <LineChart />
      </div>
      <div className='line main-line'></div>
      <div className='related-food-text'>Related food</div>
      <Footer />
    </div>
  );
}

export default Ingredient;