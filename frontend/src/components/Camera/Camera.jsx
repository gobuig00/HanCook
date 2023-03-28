import React, { useRef, useEffect, useState } from 'react';
import axios from 'axios';
import Footer from '../Footer';
import { useNavigate } from 'react-router-dom';
import './Camera.css';

// function SwipeMenu() {
//   const [selected, setSelected] = useState('Dish');
//   const menuRef = useRef(null);

//   const handleTouchStart = (e) => {
//     e.currentTarget.dataset.startX = e.touches[0].clientX;
//   };

//   const handleTouchEnd = (e) => {
//     const startX = parseFloat(e.currentTarget.dataset.startX);
//     const endX = e.changedTouches[0].clientX;
//     const diffX = startX - endX;

//     if (Math.abs(diffX) > 50) {
//       if (diffX > 0) {
//         setSelected('Text');
//         menuRef.current.style.transform = 'translateX(-50%)';
//       } else {
//         setSelected('Dish');
//         menuRef.current.style.transform = 'translateX(0%)';
//       }
//     }
//   };

//   const handleTouchMove = (e) => {
//     const startX = parseFloat(e.currentTarget.dataset.startX);
//     const currentX = e.touches[0].clientX;
//     const diffX = startX - currentX;
  
//     const percentage = (diffX / window.innerWidth) * 100;
  
//     // 선택된 메뉴에 따라 드래그 방향 조정
//     const adjustedPercentage = selected === 'Text' ? -percentage : 50 - percentage;

//   if (selected === 'Text' && percentage > 0) return;
//   if (selected === 'Dish' && percentage < 0) return;
  
//     if (Math.abs(percentage) <= 50) {
//       menuRef.current.style.transform = `translateX(${adjustedPercentage}%)`;
//     }
//   };

//   return (
//     <div
//       className="swipe-menu"
//       ref={menuRef}
//       onTouchStart={handleTouchStart}
//       onTouchMove={handleTouchMove}
//       onTouchEnd={handleTouchEnd}
//     >
//       <div className='menu-selected-background'></div>
//       <div className={`menu-item ${selected === 'Dish' ? 'selected' : ''}`}>
//         Dish
//       </div>
//       <div className={`menu-item ${selected === 'Text' ? 'selected' : ''}`}>
//         Text
//       </div>
//     </div>
//   );
// }

const Menu = () => {
  const [dragging, setDragging] = useState(false);
  const [translateX, setTranslateX] = useState(0);
  const [startX, setStartX] = useState(0);

  const menuRef = useRef(null);

  const handleMouseDown = (e) => {
    setDragging(true);
    setStartX(e.clientX);
  };

  const handleMouseMove = (e) => {
    if (dragging) {
      const diffX = e.clientX - startX;
      setTranslateX(translateX + diffX);
      setStartX(e.clientX);
    }
  };

  const handleMouseUp = () => {
    setDragging(false);
  };

  const getStyle = () => {
    return {
      transform: `translateX(${translateX}px)`,
      transition: dragging ? 'none' : 'transform 0.3s',
    };
  };

  return (
    <div
      className="menu"
      ref={menuRef}
      onMouseDown={handleMouseDown}
      onMouseMove={handleMouseMove}
      onMouseUp={handleMouseUp}
      onMouseLeave={handleMouseUp}
      style={getStyle()}
    >
      <div className="menuItem">Menu Item 1</div>
      <div className="menuItem">Menu Item 2</div>
      <div className="menuItem">Menu Item 3</div>
      <div className="menuItem">Menu Item 4</div>
      <div className="menuItem">Menu Item 5</div>
    </div>
  );
};


export default function Camera() {
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('hancook-token');
    if (!token) {
      navigate('/login')
    }
  }, [navigate]);


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
          video.onloadedmetadata = () => {
            video.play();
          };
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
    <div className='background-black'>
      <div className='total-container'>
        <video ref={videoRef} className='camera-container'/>
        <div className='camera-menu-container'>
          <Menu />
          <button type="button" onClick={getCapture}> capture </button>
        </div>
      </div>
      <canvas ref={canvasRef} style={{display: 'none'}} />
      <footer>
        <Footer />
      </footer>
    </div>
  );
   

  // return (
  //   <div className='background-black'>
  //     <div>
  //       <video ref={videoRef} />
  //     </div>
  //     <div>
  //       <div className='camera-menu-container'>
  //         <button type="button" onClick={getCapture}> capture </button>
  //       </div>
  //       <canvas ref={canvasRef} style={{display: 'none'}} />
  //     </div>
  //     <footer>
  //       <Footer />
  //     </footer>
  //   </div>
  // );
}