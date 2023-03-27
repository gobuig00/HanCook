import React from 'react';
import './Footer.css';
import { useNavigate } from 'react-router-dom';

export default function Footer({ toggleModal }) {
    const navigate = useNavigate();
    const moveToProfile = () => {
        navigate('/profile');
    }
    const moveToCart = () => {
        navigate('/cart');
    }
    return (
        <div className='footer'>
            <span
                className="material-symbols-outlined Camera-button"
                onClick={toggleModal}
            >
                photo_camera
            </span>
            <span
                class="material-symbols-outlined"
                onClick={moveToProfile}
            >
                person
            </span>
            <span
                class="material-symbols-outlined"
                onClick={moveToCart}
            >
                shopping_cart
            </span>
        </div>
    );
}

