import React from 'react';
import './Footer.css';
import { useNavigate } from 'react-router-dom';
import shoppingcart from '../icons/ShoppingCart.svg';
import camera from '../icons/Camera.svg';
import home from '../icons/Home.svg';
import search from '../icons/Search.svg';
import person from '../icons/Person.svg';

export default function Footer({ toggleModal }) {
    const navigate = useNavigate();
    const moveToSearch = () => {
        navigate('/search');
    }
    const moveToHome = () => {
        navigate('/main');
    }
    const moveToCamera = () => {
        navigate('/camera');
    }
    const moveToShoppingCart = () => {
        navigate('/cart');
    }
    const moveToProfile = () => {
        navigate('/profile');
    }
    const moveToCart = () => {
        navigate('/cart');
    }
    return (
        <div className='footer'>
            <span onClick={moveToSearch}>
                <img src={search} alt="search button" />
            </span>
            <span onClick={moveToHome}>
                <img src={home} alt="home button" />
            </span>
            <span onClick={moveToCamera}>
                <img src={camera} alt="camera button" />
            </span>
            <span onClick={moveToShoppingCart}>
                <img src={shoppingcart} alt="shoppingcart button" />
            </span>
            <span onClick={moveToProfile}>
                <img src={person} alt="" />
            </span>
        </div>
    );
}

