// 리액트 import
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {Button} from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

// 컴포넌트 import
import CamModal from './Camera/CamModal';
import Footer from './Footer'
import Card from './Card/Card';
import Category from './Categories/Category';
import PriceChange from './changes/PriceChange';
import LineChart from './LineChart';

// 기타파일 import
import './Main.css';
import logo from '../images/logo.png';


function Main() {
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('hancook-token');
    if (!token) {
      navigate('/login')
    }
  }, [navigate]);

  // 카메라 키는데 필요한 것
  const [isVisible, setIsVisible] = useState(false);
  const [isVideoStart, setIsVideoStart] = useState(false);

  // 초기데이터 구성하는데 필요한 것
  const [data, setData] = useState(null);
  const [dishChosen, setDishChosen] = useState('Seasonal');
  const [ingredientChosen, setIngredientChosen] = useState('Seasonal')

  // 식재료 관련 음식 구성하는데 필요한 것
  const [ingredientFoodName, setIngredientFoodName] =useState('')
  const [ingredientFoodList, setIngredientFoodList] =useState(null)
  

  const toggleModal = () => {
    setIsVisible(!isVisible);
    setIsVideoStart(!isVideoStart);
  };

  const getFoodList = async () => {
    try {
      const ingredientNameFormData = new FormData();
      ingredientNameFormData.append('ingredientName', ingredientFoodName);
      const response2 = await axios.get('AXIOS-2-URL', ingredientNameFormData);
      setIngredientFoodList(response2.data)
      console.log(ingredientFoodList)
    }
    catch (error) {
      console.error('Error fetching data: ', error);
    }
  }

  useEffect( () => {
    const fetchData = async () => {
      try {
        const response1 = await axios.get('http://localhost:8080/');
        setData(response1.data);
        console.log(data)

        setIngredientFoodName(response1.data.ingredient['Seasonal'][0].ingredientName)
        try {
          const ingredientNameFormData = new FormData();
          ingredientNameFormData.append('ingredientName', ingredientFoodName);
          const response2 = await axios.get('AXIOS-2-URL', ingredientNameFormData);
          setIngredientFoodList(response2.data)
          console.log(ingredientFoodList)
        }
        catch (error) {
          console.error('Error fetching data: ', error);
        }
      }
      catch (error) {
        console.error('Error fetching data: ', error);
      }
    };
    fetchData();
  }, []);

  return (
    <div>
      <div className='camera-modal'>
        {isVisible && (
          <CamModal toggleModal={toggleModal} isVideoStart={isVideoStart} />
        )}
      </div>


      {/* {!isVisible && ()} */}
        <div className='header'>
          <img className="main-logo" src={logo} alt="로고"/><br/>
          <img className='main-image' src='' alt="메인이미지"/>
        </div>

        <div className='article'>

          <div className='main-dish'>
            <h1 className='main-title'>Dish</h1>
            <Category
              categoryList={['Seasonal', 'Popular','Vegitarian', 'Cheap']}
              isChosen={dishChosen}
              setIsChosen={setDishChosen}
              usedPart='mainDish'
            />
            <div className='dish-cards'>
                {data.dish[dishChosen].map((dishItem, index) => (
                  <Card
                    key={index}
                    cardName={dishItem.dishName}
                    cardImage={dishItem.dishImage}
                    cardIndex={index}
                    usedPart='dish'
                    cardUrl={dishItem.URL}
                    size='small'
                    // onClick={검색 후 레시피로 넘어가는 함수필요}
                  />
                ))}
            </div>
          </div>

          <div className='main-ingredient'>
            <h1 className='main-title'>Ingredient</h1>
            <Category
              categoryList={['Seasonal', 'Cheap', 'Meat']}
              isChosen={ingredientChosen}
              setIsChosen={setIngredientChosen}
              usedPart='mainIngredient'
            />
            <div className='ingredient-cards'>
              {data.ingredient[ingredientChosen].map((ingredientItem, index) => (
                <Card
                  key={index}
                  cardName={ingredientItem.ingredientName}
                  cardImage={ingredientItem.ingredientImage}
                  cardIndex={index}
                  usedPart='ingredient'
                  cardUrl={ingredientItem.URL}
                  size='small'
                  onClick={getFoodList}
                />
              ))}
            </div>
            <div className='ingreddient-food-cards'>
            {ingredientFoodList.map((ingredientItem, index) => (
                <Card
                  key={index}
                  cardImage={ingredientItem.ingredientImage}
                  cardIndex={index}
                  usedPart='ingredient'
                  cardUrl={ingredientItem.URL}
                  size='large'
                  // onClick={검색 후 레시피로 넘어가는 함수필요}
                />
              ))}
            </div>

              <Button className="more-button">more</Button>
          </div>

          <div className='main-price'>
            <h1 className='main-title'>Price Static</h1>
            <div className='main-increased-part'>
              <h4>Most Increased in Price</h4>
              {data.priceStatic.increased.map((priceItem, index) => (
                <PriceChange
                  product = {priceItem.product}
                  prevPrice = {priceItem.prevPrice}
                  curPrice = {priceItem.curPrice}
                  percentage = {priceItem.percentage}
                  isIncreased = {true}
                />
              ))}
            </div>
            <div className='main-decreased-part'>
              <h4>Most Decreased in Price</h4>
              {data.priceStatic.decreased.map((priceItem, index) => (
                <PriceChange
                  product = {priceItem.product}
                  prevPrice = {priceItem.prevPrice}
                  curPrice = {priceItem.curPrice}
                  percentage = {priceItem.percentage}
                  isIncreased = {false}
                />
              ))}
            </div>
            <div className='main-chart-part'>
              <LineChart/>
            </div>
            
          </div>
        </div>
      <div className='main-footer'>
        <Footer toggleModal={toggleModal} />
      </div>
    </div>
  );
}

export default Main;