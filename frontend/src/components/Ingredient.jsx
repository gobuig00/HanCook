import Footer from './Footer';
import Donut from './Donut';
import './Ingredient.css';
import Table from './Table';
import LineChart from './LineChart';
import Img from '../images/takepicture.jpg';
import AddShoppingCart from '../icons/AddShoppingCart.svg';
import logo from '../images/logo.png'
import { Link, useNavigate } from 'react-router-dom';
import Card from './Card/Card';
import {useState, useEffect } from 'react';
import axios from 'axios';

function useRelatedFoodAPI() {
  const params = {
    ingredient: '양파',
  };
  const [data, setData] = useState(null);
  useEffect(() => {
    axios.get('http://192.168.100.172:8080/recipe/ingredient', { params })
    .then(function (response) {
      setData(response.data);
    })
    .catch(function (err) {
      console.log(err);
    });
  }, []);
  return data;
}
// 나중에 백 되면 한번에
// function useIngredient() {
//   const params = {
//     recipeId: useParams().id,
//   };
//   const [data, setData] = useState(null);
//   useEffect(() => {
//     axios.get('http://192.168.100.172:8080/ingredient', { params })
//     .then(function (response) {
//       setData(response.data);
//     })
//     .catch(function (err) {
//       console.log(err);
//     });
//   }, []);
//   return data;
// }


function Ingredient() {
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('hancook-token');
    if (!token) {
      navigate('/login')
    }
  }, [navigate]);

  const data = useRelatedFoodAPI()
  console.log(data)
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
        <div className='green-line'></div>
        <div className='line-chart'>
          <LineChart />
        </div>
        <div className='green-line'></div>
        <div className='related-food-text'>Related food</div>
        {data ? (data.map((item, index) => (
          <Card key={index} cardName={item.name} cardImage={item.img} cardIndex={index} usedPart='related-food' cardUrl={'/dish/' + item.recipeId} size='small'/>
        ))) : ('Loading...')}
        
      </main>
      <footer>
        <Footer />
      </footer>

    </div>
  );
}

export default Ingredient;