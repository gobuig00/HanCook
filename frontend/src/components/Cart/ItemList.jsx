import React from 'react';
import './ItemList.css';
import emart from '../../images/emart.png';
import lottemart from '../../images/lottemart.png';
import homeplus from '../../images/homeplus.png';

export default function ItemList({ title, price, index }) {
    const backgroundImage = index === 0 ? emart : (index === 1 ? lottemart : (index === 2 ? homeplus : ''));

    return (
        <div>
            <div className="item-mart-image" style={{ backgroundImage: `url(${backgroundImage})` }}>
            </div>
            {title}
            {price}
        </div>
    );
}
