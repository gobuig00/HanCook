import Footer from './Footer';
import Donut from './Donut';
import './Ingredient.css';
import Table from './Table';
import LineChart from './LineChart';
import AddShoppingCart from '../icons/AddShoppingCart.svg';
import logo from '../images/logo.png'
import { Link, useNavigate, useParams } from 'react-router-dom';
import Card from './Card/Card';
import {useState, useEffect } from 'react';
import axios from 'axios';

function useIngredientPrice() {
  const params = {
    id: useParams().id,
  };
  const [data, setData] = useState(null);
  useEffect(() => {
    axios.get(`${process.env.REACT_APP_API_URL}/deal/detail`, { params })
    .then(function (response) {
      setData(response.data);
    })
    .catch(function (err) {
      console.log(err);
    });
  }, []);
  return data;
}

function useRelatedFoodAPI(cardData) {
  const [data, setData] = useState(null);
  
  useEffect(() => {
    if (cardData) {
      const params = {
        ingredient: cardData.name,
        lan: 1,
    };
    axios.get(`${process.env.REACT_APP_API_URL}/recipe/ingredient`, { params })
    .then(function (response) {
      setData(response.data.slice(0, 3));
    })
    .catch(function (err) {
      console.log(err);
    });
    }
  }, [cardData]);
  return data;
}

const useMoveDish = () => {
  const navigate = useNavigate();
  const handleMoveDish = (id) => {
    navigate(`/dish/${id}`);
  };
  return handleMoveDish;
};

const useCardData = () => {
  const params = {
    ingredientId: useParams().id,
    lan: 0,
  };
  const [data, setData] = useState(null);
  useEffect(() => {
    axios.get(`${process.env.REACT_APP_API_URL}/ingredient/card`, { params })
    .then(function (response) {
      setData(response.data);
    })
    .catch(function (err) {
      console.log(err);
    });
  }, []);
  return data;
}

function Ingredient() {
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('hancook-token');
    if (!token) {
      navigate('/login')
    }
  }, [navigate]);

  const cardData = useCardData();
  const handleMoveDish = useMoveDish();
  const priceData = useIngredientPrice();
  const data = useRelatedFoodAPI(cardData);

  return (
    <div className='background-green'>
      <header>
        <Link to='/main'>
          <img className="ingredient-logo" src={logo} alt="로고"/>
        </Link>
      </header>
      <main>
        <div className='ingredient-container'>
        {cardData ? (
          <>
            <img src={cardData.imageUrl} alt="" className='ingredient-image' />
            <div>
              <img src={AddShoppingCart} alt="" className='add-shopping-cart-icon'/>
              <div className='ingredient-text'><span className='ingredient-name'>{cardData.engName}</span><br /><span className='ingredient-pronunciation'>{cardData.pronunciation}</span></div>
            </div>
          </>
        ) : ('Loading...')
        }
        </div>
        {/* <Donut /> */}
        {/* <Table /> */}
        <div className='green-line'></div>
        <div className='line-chart'>
        {priceData && priceData.length > 0 ? (
          <LineChart priceData={priceData}/>
        ) : ('Loading...')
        }
        </div>
        <div className='green-line'></div>
        <div className='related-food-text'>Related food</div>
        <div className='dish-cards'>
          {data ? (data.map((item, index) => (
            <Card key={index} cardName={item.name} cardImage={item.img} cardIndex={index} usedPart='related-food' cardUrl={'/dish/' + item.recipeId} size='small' onClick={() => handleMoveDish(item.recipeId)}/>
          ))) : ('Loading...')}
        </div>
      </main>
      <footer>
        <Footer />
      </footer>

    </div>
  );
}

export default Ingredient;