import React from 'react';

export default function CameraModule({ isVisible, toggleModal }) {
    const handleCapture = (event) => {
        const file = event.target.files[0];
        const url = URL.createObjectURL(file);
        const video = document.createElement('video');
        video.src = url;
        document.body.appendChild(video);
    };
    const closeModal = () => {
        toggleModal();
    }

    return (
        <div className='cameramodule'>
            <span onClick={closeModal}>X</span><br>
            </br>
            <input type="file" accept="image/*, video/*" capture onChange={handleCapture}/>
        </div>
    );
}

