import React, { useRef, useEffect } from 'react';
import axios from 'axios';
import Footer from '../Footer';

export default function Camera() {

  const videoRef = useRef(null);
  const canvasRef = useRef(null);

  useEffect(() => {
    getVideo();
  });

  const getVideo = async () => {
    try {
        const stream = await navigator.mediaDevices.getUserMedia({
          video: true,
        });
        const video = videoRef.current;
        if (video) {
          video.srcObject = stream;
          video.play();
        }
    } catch (err) {
      console.error(err);
    }
  };

  const getCapture = () => {
    const canvas = canvasRef.current;
    const video = videoRef.current;
    if (canvas && video) {
      const context = canvas.getContext('2d');
      context.drawImage(video, 0, 0, canvas.width, canvas.height);
      const dataUrl = canvas.toDataURL('image/png')
      // console.log(dataUrl)
      imageMaker(dataUrl);
    }
  };
  const imageMaker = (dataUrl) => {
    const img = new Image();
    img.onload = () => {
      const canvas = document.createElement('canvas');
      const ctx = canvas.getContext('2d');
      canvas.width = img.width;
      canvas.height = img.height;
      ctx.drawImage(img, 0, 0);
      canvas.toBlob(blob => {
        const imageFile = new File([blob], 'image.png', { type: 'image/png' });
        axiosFunc(imageFile);
      }, 'image/png', 1);
    };
    img.src = dataUrl;
  };

  const axiosFunc = (imageFile) => {
    const formData = new FormData();
    formData.append('image', imageFile);

    axios.post('http://192.168.100.172:8080/food/recognize', formData)
      .then(response => {
        // API 요청 성공 시 처리할 코드
        console.log(response.data);
      })
      .catch(error => {
        // API 요청 실패 시 처리할 코드
        console.error(error);
      });
    
    
  }
   

  return (
    <div>
      <div>
        <video ref={videoRef} />
      </div>

      <div>
        <button type="button" onClick={getCapture}> capture </button>
        <canvas ref={canvasRef} style={{display: 'none'}} />
      </div>
      <footer>
        <Footer />
      </footer>
    </div>
  );
}