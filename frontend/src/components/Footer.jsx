import React from 'react';
import './Footer.css';
import { useNavigate } from 'react-router-dom';

export default function Footer({ toggleModal }) {
    const navigate = useNavigate();
    const moveToProfile = () => {
        navigate('/profile');
    }
    return (
        <div className='footer'>
            <span
                className="material-symbols-outlined Camera-button"
                onClick={toggleModal}
            >
                photo_camera
            </span>
            <div className='profile-button'>
                onClick={moveToProfile}

            </div>
            <span class="material-symbols-outlined">
                person
            </span>
        </div>
    );
}

