import React from 'react';
import Camera from './Camera';

export default function CamModal({ toggleModal, isVideoStart }) {
    const closeModal = () => {
        toggleModal();
    }

    return (
        <div className='cameramodal'>
            <span onClick={closeModal}>X</span><br>
            </br>
            <Camera isVideoStart={isVideoStart} />
        </div>
    );
}

