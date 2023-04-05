import React from 'react';
import './ItemList.css';
import emart from '../../images/emart.png';
import lottemart from '../../images/lottemart.png';
import homeplus from '../../images/homeplus.png';

export default function ItemList({ title, price, index }) {
    const backgroundImage = index === 3 ? emart : (index === 1 ? lottemart : (index === 2 ? homeplus : ''));

    return (
        <div className='item-container'>
            <div className='item-title'>
                <p>{title}</p>
            </div>
            <div className='item-price'>
                <div className="item-mart-image" style={{ backgroundImage: `url(${backgroundImage})` }}>
                </div>
                <p>{price}₩</p>
            </div>
        </div>
    );
}
