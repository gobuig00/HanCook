import Footer from './Footer';
import Donut from './Donut';
import './Ingredient.css';
import Table from './Table';
import LineChart from './LineChart';
import Img from '../images/takepicture.jpg';
import AddShoppingCart from '../icons/AddShoppingCart.svg';
import logo from '../images/logo.png'
import { Link } from 'react-router-dom';

function Ingredient() {
  return (
    <div className='background-green'>
      <header>
        <Link to='/main'>
          <img className="ingredient-logo" src={logo} alt="로고"/>
        </Link>
      </header>
      <main>
        <div className='ingredient-container'>
          <img src={Img} alt="" className='ingredient-image' />
          <div>
            <img src={AddShoppingCart} alt="" className='add-shopping-cart-icon'/>
            <div className='ingredient-text'><span className='ingredient-name'>Apple</span><br /><span className='ingredient-pronunciation'>(sa-gwa)</span></div>
          </div>
        </div>
        <Donut />
        <Table />
        <div className='line main-line'></div>
        <div className='line-chart'>
          <LineChart />
        </div>
        <div className='line main-line'></div>
        <div className='related-food-text'>Related food</div>
      </main>
      <footer>
        <Footer />
      </footer>

    </div>
  );
}

export default Ingredient;