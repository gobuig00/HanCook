import React, { useState } from 'react';
import CameraModule from './CameraModule';

export default function Footer() {
    let [isVisible, setIsVisible] = useState(false);
    const toggleModal = () => {
        setIsVisible(!isVisible);
    };

    return (
        <div className='footer'>
            <button
                className='buttonToCamera'
                onClick={toggleModal}
            >
                Camera
            </button>

            {isVisible && (
                <CameraModule isVisible = {isVisible} toggleModal={toggleModal}/>
            )}
        </div>
    );
}

