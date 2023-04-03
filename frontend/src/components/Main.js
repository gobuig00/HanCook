// 리액트 import
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {Button, Carousel} from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

// 컴포넌트 import
import Footer from './Footer'
import Card from './Card/Card';
import Category from './Categories/Category';
import PriceChange from './changes/PriceChange';
import LineChart from './LineChart';

// 기타파일 import
import './Main.css';
import logo from '../images/logo.png';
import mainImage1 from '../images/mainImage1.png';
import mainImage2 from '../images/mainImage2.png';
import mainImage3 from '../images/mainImage3.png';
import ingreDefaultImage from '../images/ingreDefaultImage.png';

function Main() {
  const navigate = useNavigate();

  // 초기데이터 구성하는데 필요한 것
  const [dish, setDish] = useState([]); // 카테고리 변경시 필요한 것
  const [dishChosen, setDishChosen] = useState('Popular'); // 카테고리 변경시 필요한 것
  const [ingredients, setIngredients] = useState([]); // 카테고리 변경시 필요한 것
  const [ingredientChosen, setIngredientChosen] = useState('Popular'); // 카테고리 변경시 필요한 것
  const [ingreDish, setIngreDish] = useState([]);
  const [priceChange, setPriceChange] = useState([]);
  const defaultImage = ingreDefaultImage;

  useEffect( () => {
    // 로그인 확인
    const token = localStorage.getItem('hancook-token');
    if (!token) {
      navigate('/login');
    }
    // 통신
    fetchData();
  }, [navigate]);

  const fetchData = async () => {
    try {
      const params = {
        lan: 0,
      };
      const dishAxios = await axios.get(`${process.env.REACT_APP_API_URL}/recipe/random`, {params});
      setDish(dishAxios.data)
      const ingreAxios = await axios.get(`${process.env.REACT_APP_API_URL}/component/random`, {params});
      setIngredients(ingreAxios.data)
      const priceChangeAxios = await axios.get(`${process.env.REACT_APP_API_URL}/deal/change`);
      setPriceChange(priceChangeAxios.data)

      //console
      console.log(dishAxios.data)
      console.log(ingreAxios.data)
      // console.log(priceChangeAxios.data)

      try {
        const params = {
          ingredient: ingreAxios.data[0].name,
          lan: 0,
        }
        const ingreDishAxios = await axios.get(`${process.env.REACT_APP_API_URL}/recipe/ingredient`,{params});
        setIngreDish(ingreDishAxios.data.slice(0, 4))
        console.log(ingreDishAxios.data)
      } catch (error) {
        console.error('Error fetching data: ', error);
      }
    }
    catch (error) {
      console.error('Error fetching data: ', error);
    }
  };
  const moveToRecipe = (recipeId) => {
    navigate(`/dish/${recipeId}`)
  }
  const fetchIngreDish = async (ingredientName) => {
    try {
      const params = {
        ingredient: ingredientName,
        lan: 0,
      };
      const ingreDishAxios = await axios.get(`${process.env.REACT_APP_API_URL}/recipe/ingredient`, { params });
      setIngreDish(ingreDishAxios.data.slice(0, 4));
      console.log(ingreDishAxios.data);
    } catch (error) {
      console.error('Error fetching data: ', error);
    }
  };

  return (
    <div className="main-container">
        <div className='main-header'>
          <img className="main-logo" src={logo} alt="로고"/><br/>
          <div className="main-image">
            <Carousel
              prevIcon={<span aria-hidden="true" className="hide-icon" />}
              nextIcon={<span aria-hidden="true" className="hide-icon" />}
            >
              
              <Carousel.Item interval={5000}>
                <img
                  className="d-block w-100"
                  src={mainImage1}
                  alt="First slide"
                  style={{ width: "100%", height: "400px", objectFit: "cover" }}
                />
              </Carousel.Item>
              <Carousel.Item interval={5000}>
                <img
                  className="d-block w-100"
                  src={mainImage2}
                  alt="Second slide"
                  style={{ width: "90%", height: "400px", objectFit: "cover" }}
                />
              </Carousel.Item>
              <Carousel.Item interval={5000}>
                <img
                  className="d-block w-100"
                  src={mainImage3}
                  alt="Third slide"
                  style={{ width: "190%", height: "400px", objectFit: "cover" }}
                />
              </Carousel.Item>
            </Carousel>
          </div>
        </div>
        <div className='main-article'>
          <div className='main-dish'>
            <h1 className='main-title'>Dish</h1>

            <Category
              categoryList={['Popular','Vegitable', 'Cheap']}
              isChosen={dishChosen}
              setIsChosen={setDishChosen}
              setPart={setDish}
              usedPart='mainDish'
            />

            <div className='dish-cards'>
                {dish.map((dishItem, index) => (
                  <Card
                    key={index}
                    cardName={dishItem.name}
                    cardImage={dishItem.img}
                    usedPart='dish'
                    size='small'
                    onClick={() => moveToRecipe(dishItem.recipeId)}
                  />
                ))}
            </div>
          </div>
          <hr/>
          <div className='main-ingredient'>
            <h1 className='main-title'>Ingredient</h1>
            <Category
              categoryList={['Popular','Vegitable', 'Cheap']}
              isChosen={ingredientChosen}
              setIsChosen={setIngredientChosen}
              setPart={setIngredients}
              usedPart='mainIngredient'
            />
            <div className='ingredient-cards'>
              {ingredients.map((ingredientItem, index) => (
                <Card
                  key={index}
                  cardName={ingredientItem.name}
                  cardImage={ingredientItem.ingredientImage || defaultImage}
                  usedPart='ingredient'
                  size='small'
                  onClick={() => fetchIngreDish(ingredientItem.name)}
                />
                
              ))}
            </div>

            <div className='ingredient-dish-cards'>
            {ingreDish.map((ingreDishItem, index) => (
                <Card
                  key={index}
                  cardImage={ingreDishItem.img}
                  usedPart='ingredient'
                  size='large'
                  onClick={() => moveToRecipe(ingreDishItem.recipeId)}
                />
              ))}
            </div>
            <Button className="more-button">more</Button>
          </div>
          <hr/>
          <div className='main-price'>
            <h1 className='main-title'>Price Static</h1>
            <div className='main-increased-part'>
              <p>Most Increased in Price</p>
              {/* {priceChange.increased.map((priceItem, index) => (
                <PriceChange
                  product = {priceItem.product}
                  prevPrice = {priceItem.prevPrice}
                  curPrice = {priceItem.curPrice}
                  percentage = {priceItem.percentage}
                  isIncreased = {true}
                />
              ))} */}
            </div>
            <div className='main-decreased-part'>
              <p>Most Decreased in Price</p>
              {/* {priceChange.decreased.map((priceItem, index) => (-
                <PriceChange
                  product = {priceItem.product}
                  prevPrice = {priceItem.prevPrice}
                  curPrice = {priceItem.curPrice}
                  percentage = {priceItem.percentage}
                  isIncreased = {false}
                />
              ))} */}
            </div>
            <div className='main-chart-part'>
              <LineChart/>
            </div>
            
          </div>

        </div>
    </div>
          


          
    //     </div>
    //   <div className='main-footer'>
    //     <Footer />
    //   </div>
    // </div>
  );
}

export default Main;