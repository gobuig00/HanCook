import React from 'react';
import axios from 'axios';
import './Category.css';

export default function Category({ categoryList, isChosen, setIsChosen, setPart, usedPart }) {
  const clickDishCat = async (event) => {
    const chosen = event.target.textContent;
    setIsChosen(chosen);
    const params = {
      lan: 1,
    };
    if (usedPart==="mainIngredient"){
      if (chosen === 'Cheap') {
        try {
          const getCheapAxios = await axios.get(`${process.env.REACT_APP_API_URL}/deal/${chosen}`, {params});
          const nameList = getCheapAxios.data.map(item => item.small);
          
          try {
            // 쿼리 스트링 직접 생성하기
            const queryString = nameList.map(name => `name=${name}`).join('&') + '&lan=0';
            const ingreAxios = await axios.get(`${process.env.REACT_APP_API_URL}/ingredient/getbyname?${queryString}`, {
              headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
            });
            setPart(ingreAxios.data)
          } catch(error) {
            console.error('Error cheapIngre data: ', error);
          }

        } catch (error) {
          console.error('Error cheap data: ', error);
        }
      } else if (chosen === 'Vegetable' || chosen === 'Meat' || chosen === 'Popular') {
        try {
          const ingreAxios = await axios.get(`${process.env.REACT_APP_API_URL}/ingredient/${chosen}`, {params});
          setPart(ingreAxios.data)
        } catch (error) {
          console.error('Error VegiMeatPop data: ', error);
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