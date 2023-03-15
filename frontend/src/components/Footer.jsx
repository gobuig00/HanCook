import React, { useState } from 'react';
import CamModal from './CamModal';

export default function Footer() {
    let [isVisible, setIsVisible] = useState(false);
    let [isVideoStart, setIsVideoStart] = useState(false);

    const toggleModal = () => {
        setIsVisible(!isVisible);
        setIsVideoStart(!isVideoStart);
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
                <CamModal toggleModal={toggleModal} isVideoStart={isVideoStart} />
            )}
        </div>
    );
}

