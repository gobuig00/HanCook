import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {Button} from 'react-bootstrap';

import './Main.css';
import CamModal from './Camera/CamModal';
import Footer from './Footer'
import Card from './Card/Card';
import Category from './Categories/Category';

import logo from '../images/logo.png';


function Main() {
  const [isVisible, setIsVisible] = useState(false);
  const [isVideoStart, setIsVideoStart] = useState(false);
  const [data, setData] = useState([]);
  const [dishChosen, setDishChosen] = useState('Seasonal');
  const [ingredientChosen, setIngredientChosen] = useState('Seasonal')

  const toggleModal = () => {
    setIsVisible(!isVisible);
    setIsVideoStart(!isVideoStart);
  };

  // useEffect( () => {
  //   const fetchData = async () => {
  //     try {
  //       const response = await axios.get('여기 주소가 뭐임????');
  //       setData(response.data);
  //     }
  //     catch (error) {
  //       console.error('Error fetching data: ', error);
  //     }
  //   };
  //   fetchData();
  // }, []);

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
            {/* <div className='dish-cards'>
                {data.dish[dishChosen].map((dishItem, index) => (
                  <Card
                    key={index}
                    cardName={dishItem.dishName}
                    cardImage={dishItem.dishImage}
                    cardIndex={index}
                    usedPart='dish'
                    cardUrl={dishItem.URL} />
                ))}
            </div> */}
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
              {/* {data.ingredient[ingredientChosen].map((ingredientItem, index) => (
                <Card
                  key={index}
                  cardName={ingredientItem.ingredientName}
                  cardImage={ingredientItem.ingredientImage}
                  cardIndex={index}
                  usedPart='ingredient'
                  cardUrl={ingredientItem.URL}
                />
              ))} */}
                
              <Button className="more-button">more</Button>
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