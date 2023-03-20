import React, { useEffect } from 'react';
import './Category.css';

export default function Category({ categoryList, isChosen, setIsChosen, usedPart }) {
  const clickDishCat = (event) => {
    const chosen = event.target.textContent;
    setIsChosen(chosen);
  };

  return (
    <div className="category">
      {categoryList.map((item, index) => {
        const itemClassName = `${usedPart}-${index} ${
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