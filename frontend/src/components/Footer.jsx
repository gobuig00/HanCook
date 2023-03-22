import React from 'react';
import './Footer.css';

export default function Footer({ toggleModal }) {
    
    return (
        <div className='footer'>
            <button
                className='Camera-button'
                onClick={toggleModal}
            >
                Camera
            </button>
        </div>
    );
}

