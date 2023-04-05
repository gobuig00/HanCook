import { Link, useParams, useNavigate } from 'react-router-dom';
import AddShoppingCart from '../icons/AddShoppingCart.svg';
import AddEatingFood from '../icons/AddEatingFood.png';
import MinusButtom from '../icons/MinusButton.png';
import PlusButton from '../icons/PlusButton.png';
import Button from 'react-bootstrap/Button';
import {useState, useEffect } from 'react';
import Modal from 'react-bootstrap/Modal';
import logo from '../images/logo.png';
import Footer from './Footer';
import Donut from './Donut';
import Table from './Table';
import axios from 'axios';
import './Dish.css';
import Toast from 'react-bootstrap/Toast';
import ToastContainer from 'react-bootstrap/ToastContainer';

export function Youtube(props) {
  const target = props.target;
  // const [videoId, setVideoId] = useState('');
  useEffect(() => { 
  }, [target]);
  return(
    <div className='video-container'>
      <iframe width="560" height="315" src={`https://www.youtube.com/embed/${props.target}`} title="YouTube video player" frameBorder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowFullScreen></iframe>
    </div>
  );
}

function useRecipeAPI() {
  const params = {
    recipeId: useParams().id,
    lan: 1,
  };
  const [data, setData] = useState(null);
  const [nutrient, setNutrient] = useState(null);
  const [originData, setOriginData] = useState(null);
  useEffect(() => {
    axios.get(`${process.env.REACT_APP_API_URL}/recipe/card`, { params })
    .then(function (response) {
      setData(response.data);
    })
    .catch(function () {
      console.log('There is no food data');
    });
  }, []);

  useEffect(() => {
    if (data) {
      const foodname = data.recipe.name;
      axios
        .get(`${process.env.REACT_APP_API_URL}/nutrient/food/${foodname}`)
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
          setNutrient(nutrientData);
          setOriginData(response.data);
        })
        .catch(() => {
          console.log('There are no nutrient data.');
        });
    }
  }, [data]);

  let result = [data, nutrient, originData];
  return result;
}



function RecipeIngredient(props) {
  let result = ''
  if (props.data) {
    for (let i=0; i<props.data.length; i++) {
      if (i !== (props.data.length-1)) {
        result += props.data[i].name + props.data[i].capacity + ', '
      } else {
        result += props.data[i].name + props.data[i].capacity
      }
    }
  }
  return (
    <div>
      {result}
    </div>
  )
}


function Dish() {
  const navigate = useNavigate();
  const recipeId = useParams().id;


  useEffect(() => {
    const token = localStorage.getItem('hancook-token');
    if (!token) {
      navigate('/login')
    }
  }, [navigate]);


  const result = useRecipeAPI();
  const data = result[0];
  const [addNumber, setAddNumber] = useState(1);
  const [modal, setModal] = useState(false);
  const [show, setShow] = useState(false);
  const [toastdata, setToastData] = useState('')
  const [shoppingCartModal, setShoppingCartModal] = useState(false);

  
  const increaseAddNumber = () => {
    if (addNumber < 9) {
      setAddNumber(addNumber + 1);
    }
  }

  const decreaseAddNumber = () => {
    if (addNumber > 1) {
      setAddNumber(addNumber - 1);
    }
  }

  const openEatConfirmModal = () => {
    setModal(true);
  }

  const closeEatConfirmModal = () => {
    setModal(false);
  }

  const openShoppingCartModal = () => {
    setShoppingCartModal(true);
  }

  const closeShoppingCartModal = () => {
    setShoppingCartModal(false);
  }

  const addShoppingCart = () => {
    const data = {
      recipeId: recipeId,
    }
    const headers = {
      'Authorization' : `Bearer ${localStorage.getItem('hancook-token')}`,
      'Content-Type': 'application/json',
    }
    axios.post(`${process.env.REACT_APP_API_URL}/cart/add/?recipeId=${recipeId}`, data,  { headers: headers })
    .then(() => {
      setShoppingCartModal(false);
      setToastData('Add ingredients to your shopping cart');
      setShow(true);
    })
    .catch(() => {
      setShoppingCartModal(false);
      setToastData('Fail');
      setShow(true);
    })
  }

  const useAddEatFood = () => {
    const params = {
      cnt : addNumber,
    }
    const headers = {
      'Authorization' : `Bearer ${localStorage.getItem('hancook-token')}`,
      'Content-Type': 'application/json',
    }
    const foodRecordRequestDto = JSON.stringify(result[2])
    axios.post(`${process.env.REACT_APP_API_URL}/record/register`, foodRecordRequestDto , { params: params, headers: headers })
    .then(() => {
      setModal(false);
      setToastData('Add this menu to the list you ate today');
      setShow(true);
    })
    .catch(() => {
      setModal(false);
      setToastData('Unable to add due to lack of nutrient data.');
      setShow(true);
    })
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
          {data ? (
              <img src={data.recipe.img} alt="" className='ingredient-image' />
          ) : (
            'Loading...'
          )}
          <div className='text-center'>
            <img src={AddShoppingCart} alt="" className='add-shopping-cart-icon' onClick={openShoppingCartModal}/>
            {data ? (
              <>
                <span className='ingredient-name'>{data.recipe.engName}</span>
                <br />
                <span className='ingredient-pronunciation'>{data.recipe.pronunciation}</span>
              </>
              ) : (
                'Loading...'
              )
            }
            <div className='add-eat-food-container'>
              <img src={PlusButton} alt="" className='plus-minus-button' onClick={increaseAddNumber} />
              <span className='add-number'>{addNumber}</span>
              <img src={MinusButtom} alt="" className='plus-minus-button' onClick={decreaseAddNumber}/>
              <img src={AddEatingFood} alt=""className='add-eat-food' onClick={openEatConfirmModal}/>
            </div>
          </div>
        </div>
        {result[1] ? (
          <>
            <div className='profile-nutrition dish-donut'>
              <Donut
                keyList={result[1] ? Object.keys(result[1].nutrient) : []}
                valueList={result[1] ? Object.values(result[1].nutrient).map((value) => parseInt(value, 10)) : []}
                title="Nutrition"
                centerText={result[1] ? `${result[1].other.kcal[0]} kcal` : ''}
              />
            </div>
            <Table body={result[1] ? result[1].other : []} head={['nutrients', 'amount@(% daily value)', 'per 100g']}/>
          </>
        ) : ('No nutritional information data.')}
        <div className='green-line'></div>
        <div className='related-food-text'>Recipe</div>
        <br />
        {data ? (
          <>
            <div className='semi-title-text'>Ingredient</div>
            <RecipeIngredient data={data.ingredient}/>
            <br />
            <div className='semi-title-text'>Process</div>
            <ol>
              {data.process.map((item, index) => (
                <li key={index}>
                  {item.description}
                </li>
              ))}
            </ol>
            <div className='green-line'></div>
            <div className='related-food-text'>Video</div>
            <Youtube target={data.recipe.youtubeId}/>
          </>
        ) : (
          'Loading...'
        )}
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
      <Modal
        show={modal}
        onHide={closeShoppingCartModal}
        backdrop="static"
        keyboard={false}
        centered
      >
        <Modal.Body>
          Would you like to add this menu to the list you ate today? (Add {addNumber})
        </Modal.Body>
        <Modal.Footer>
          <Button className='dish-no-button' onClick={closeEatConfirmModal}>
            No
          </Button>
          <Button className='dish-yes-button' onClick={useAddEatFood}>Yes</Button>
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

export default Dish;