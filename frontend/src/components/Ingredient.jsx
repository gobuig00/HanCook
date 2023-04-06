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
import Toast from 'react-bootstrap/Toast';
import ToastContainer from 'react-bootstrap/ToastContainer';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';

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

function useNutrition() {
  const params = {
    ingredientId: useParams().id,
  };
  const [data, setData] = useState(null);
  useEffect(() => {
    axios.get(`${process.env.REACT_APP_API_URL}/nutrient/ingredient`, { params })
    .then(function (response) {
      const size = response.data.servingSize / 100;
      const nutrientData = {
        nutrient: {
          carbs: response.data.carb,
          fat: response.data.fat,
          protein: response.data.protein,
        },
        other: {
          carbs: [`${response.data.carb}(${(response.data.carb/3.25).toFixed(2)}%)`, `${(response.data.carb / size).toFixed(2)}(${(response.data.carb / size/3.25).toFixed(2)})%`],
          fat: [`${response.data.fat}(${(response.data.fat/5).toFixed(2)}%)`, `${(response.data.fat / size).toFixed(2)}(${(response.data.fat / size/5).toFixed(2)})%`],
          protein: [`${response.data.protein}(${(response.data.protein*2).toFixed(2)}%)`, `${(response.data.protein / size).toFixed(2)}(${(response.data.protein*2 / size).toFixed(2)})%`],
          cholesterol: [`${response.data.cholesterol}(${(response.data.cholesterol/2).toFixed(2)}%)`, `${(response.data.cholesterol / size).toFixed(2)}(${(response.data.cholesterol / size/2).toFixed(2)})%`],
          kcal: [`${response.data.kcal}(${(response.data.kcal/22).toFixed(2)}%)`, `${(response.data.kcal / size).toFixed(2)}(${(response.data.kcal / size/22).toFixed(2)})%`],
          sugar: [`${response.data.sugar}(${(response.data.sugar).toFixed(2)}%)`, `${(response.data.sugar / size).toFixed(2)}(${(response.data.sugar / size).toFixed(2)})%`],
          salt: [`${response.data.salt}(${(response.data.salt/20).toFixed(2)}%)`, `${(response.data.salt / size).toFixed(2)}(${(response.data.salt / size/20).toFixed(2)})%`],
        },
      };
      setData(nutrientData);
    })
    .catch(() => {
      console.log('There is no nutrient data');
    });
  }, []);
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
  const ingredientId = useParams().id

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
  const nutrientData = useNutrition();
  const [show, setShow] = useState(false);
  const [toastdata, setToastData] = useState('')
  const [shoppingCartModal, setShoppingCartModal] = useState(false);

  const addShoppingCart = () => {
    const headers = {
      'Authorization': `Bearer ${localStorage.getItem('hancook-token')}`,
      'Content-Type': 'application/json',
    }
    axios.post(`${process.env.REACT_APP_API_URL}/cart/addComponent?ingredientId=${ingredientId}`, {}, { headers: headers })
    .then(() => {
      setShoppingCartModal(false);
      setToastData('Add ingredients to your shopping cart');
      setShow(true);
    }).catch(() => {
      setShoppingCartModal(false);
      setToastData('Fail');
      setShow(true);
    })
  }

  const openShoppingCartModal = () => {
    setShoppingCartModal(true);
  }

  const closeShoppingCartModal = () => {
    setShoppingCartModal(false);
  }

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
              <img src={AddShoppingCart} alt="" className='add-shopping-cart-icon' onClick={openShoppingCartModal}/>
              <div className='ingredient-text'><span className='ingredient-name'>{cardData.engName}</span><br /><span className='ingredient-pronunciation'>{cardData.pronunciation}</span></div>
            </div>
          </>
        ) : ('Loading...')
        }
        </div>
        <div className='profile-nutrition dish-donut'>
          <Donut
            keyList={['carbs', 'fat', 'protein']}
            valueList={nutrientData ? Object.values(nutrientData.nutrient) : [0, 0, 0]}
            title={nutrientData ? "Nutrition" : 'There is No Nutrition Data'}
            centerText={nutrientData ? ` kcal` : ''}
          />
        </div>
        <Table body={nutrientData ? nutrientData.other : []} head={['nutrients', 'amount@(% daily value)', 'per 100g']}/>
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
      <Modal
        show={shoppingCartModal}
        onHide={closeShoppingCartModal}
        backdrop="static"
        keyboard={false}
        centered
      >
        <Modal.Body>
          Would you like to add ingredients to your shopping cart?
        </Modal.Body>
        <Modal.Footer>
          <Button className='dish-no-button' onClick={closeShoppingCartModal}>
            No
          </Button>
          <Button className='dish-yes-button' onClick={addShoppingCart}>Yes</Button>
        </Modal.Footer>
      </Modal>
      <ToastContainer position='bottom-center' className='camera-toast'>
        <Toast onClose={() => setShow(false)} show={show} delay={3000} autohide bg='dark'>
          <Toast.Body>{toastdata}</Toast.Body>
        </Toast>
      </ToastContainer>
      <footer>
        <Footer />
      </footer>

    </div>
  );
}

export default Ingredient;