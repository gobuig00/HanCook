import React from 'react';
import { useNavigate } from 'react-router-dom';

import './Card.css';

export default function Card({ cardName, cardImage, cardIndex, usedPart, cardUrl, size }) {
    const itemClassName = ''

    if (size==='small') {
        const itemClassName = `${usedPart}-${cardIndex} small`
    } else if (size==='large') {
        const itemClassName = `${usedPart}-${cardIndex} large`
    } else {
        const itemClassName = `${usedPart}-${cardIndex}`
    }   

    const cardImageStyle = {
        width: '100%',
        aspectRatio: '1/1',
        backgroundPosition: 'center center',
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover',
        overflow: 'hidden',
        backgroundImage: `url(${cardImage})`,
    };

    const navigate = useNavigate();
    const clickCard = () => {
        navigate(cardUrl);
    }
    return (
        <div className={itemClassName} onClick={clickCard}>
            <div className='card-image' style={cardImageStyle}>
            </div>

            <div className='card-name'>
                {cardName}
            </div>
        </div>
    );
}

