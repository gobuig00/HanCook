import React from 'react';
import './PriceChange.css';

export default function PriceChange({ product, prevPrice, curPrice, percentage, isIncreased, onClick }) {
    const strPercentage = `${percentage}%`
    
    return (
        <div className='price-change-row' onClick={onClick}>
            <span className='left'>{product}</span>

            <span className='middle'>
                <span className='prev-price'>{prevPrice}</span>
                <i className="material-icons">arrow_forward</i>
                <span className='cur-price'>{curPrice}</span>
            </span>

            <span className='right'>
                {isIncreased && ( 
                    <>
                        <span className="red-triangle"></span>
                        <span className='red-percentage'>{strPercentage}</span>
                    </>
                )}
                {!isIncreased && (
                    <>
                        <span className="blue-triangle"></span>
                        <span className='blue-percentage'>{strPercentage}</span>
                    </>
                )}
            </span>
        </div>
    );
}

