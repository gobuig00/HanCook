import React from 'react';
import { useNavigate } from 'react-router-dom';

import './Card.css';

export default function Card({ cardName, cardImage, cardIndex, usedPart, cardUrl }) {
    const itemClassName = `${usedPart}-${cardIndex}`
    const navigate = useNavigate();
    const clickCard = () => {
        navigate(cardUrl);
    }
    return (
        <div className={itemClassName} onClick={clickCard}>
            <img src={cardImage} alt="카드이미지" />
            <p>{cardName}</p>
        </div>
    );
}

