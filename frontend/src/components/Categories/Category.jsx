import React from 'react';
import axios from 'axios';
import './Category.css';

export default function Category({ categoryList, isChosen, setIsChosen, setPart, usedPart }) {
  const clickDishCat = async (event) => {
    const chosen = event.target.textContent;
    setIsChosen(chosen);
    const params = {
      lan: 0,
    };
    if (usedPart==="mainIngredient"){
      if (chosen === 'Cheap') {
        try {
          const ingreAxios = await axios.get(`${process.env.REACT_APP_API_URL}/deal/${chosen}`, {params});
          setPart(ingreAxios.data)
          console.log(ingreAxios.data)
        } catch (error) {
          console.error('Error fetching data: ', error);
        }
      } else if (chosen === 'Vegetable' || chosen === 'Meat') {
        try {
          const ingreAxios = await axios.get(`${process.env.REACT_APP_API_URL}/ingredient/${chosen}`, {params});
          setPart(ingreAxios.data)
        } catch (error) {
          console.error('Error fetching data: ', error);
        }
      } else {
        try {
          const ingreAxios = await axios.get(`${process.env.REACT_APP_API_URL}/component/${chosen}`, {params});
          setPart(ingreAxios.data)
        } catch (error) {
          console.error('Error fetching data: ', error);
        }
      }
    }
  };

  return (
    <div className="category">
      {categoryList.map((item, index) => {
        const itemClassName = `${usedPart} ${
          isChosen === item ? 'isChosen' : 'isUnchosen'
        }`;

        return (
          <p key={index} className={itemClassName} onClick={clickDishCat}>
            {item}
          </p>
        );
      })}
    </div>
  );
}