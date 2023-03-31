import React from 'react';
import axios from 'axios';
import './Category.css';

export default function Category({ categoryList, isChosen, setIsChosen, setPart, usedPart }) {
  const clickDishCat = async (event) => {
    const chosen = event.target.textContent;
    setIsChosen(chosen);

    if (usedPart==="mainDish"){
      try {
        const params = {
          lan: 0,
        };
        const dishAxios = await axios.get(`http://localhost:8080/recipe/${chosen}`, {params});
        setPart(dishAxios.data)
      } catch (error) {
        console.error('Error fetching data: ', error);
      }
    } else if (usedPart==="mainIngredient"){
      try {
        const params = {
          lan: 0,
        };
        const ingreAxios = await axios.get(`http://localhost:8080/recipe/${chosen}`, {params});
        setPart(ingreAxios.data)
      } catch (error) {
        console.error('Error fetching data: ', error);
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