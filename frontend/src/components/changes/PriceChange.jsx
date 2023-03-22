import React from 'react';
import './PriceChange.css';

export default function PriceChange({ product, prevPrice, curPrice, percentage, isIncreased }) {
    const strPercentage = `${percentage}%`
    
    return (
        <div className='price-change-row'>
            <span className='left'>{product}</span>

            <span className='middle'>
                <span className='prev-price'>{prevPrice}</span>
                <i class="material-icons">arrow_forward</i>
                <span className='cur-price'>{curPrice}</span>
            </span>

            <span className='right'>
                {isIncreased && ( 
                    <span class="material-symbols-outlined increased">
                        change_history
                    </span>
                )}
                {!isIncreased && (
                    <span class="material-symbols-outlined decreased">
                        change_history
                    </span>
                )}
                <span className='percentage'>{strPercentage}</span>
            </span>
        </div>
    );
}

