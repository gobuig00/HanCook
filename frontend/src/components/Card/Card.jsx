import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Card.css';


export default function Card({ cardName, cardImage, usedPart, size, onClick }) {
    let itemClassName = ''

    if (size==='small') {
        itemClassName = `custom-card ${usedPart} small`
    } else if (size==='large') {
        itemClassName = `custom-card ${usedPart} large`
    } else {
        itemClassName = `custom-card ${usedPart}`
    }   

    const cardImageStyle = {
        width: '100%',
        aspectRatio: '1/1',
        backgroundPosition: 'center center',
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover',
        overflow: 'hidden',
        backgroundImage: `url(${cardImage})`,
        border: 'none',
        borderRadius: '5%',
    };

    return (
        <div className={itemClassName} onClick={onClick}>
            <div className='card-image' style={cardImageStyle}>
            </div>
            <div className='card-name'>
                {cardName}
            </div>
        </div>
    );
}

