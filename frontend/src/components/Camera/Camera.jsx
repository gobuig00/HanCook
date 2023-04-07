import React, { useState, useRef, useEffect, useCallback } from 'react';
import axios from 'axios';
import Footer from '../Footer';
import { useNavigate } from 'react-router-dom';
import './Camera.css';
import Menu from './Menu';
import camera from '../../icons/camera.png';
import Modal from 'react-bootstrap/Modal';
import { ModalHeader } from 'react-bootstrap';
import Toast from 'react-bootstrap/Toast';
import ToastContainer from 'react-bootstrap/ToastContainer';


export default function Camera() {
  const navigate = useNavigate();
  const [status, setStatus] = useState(0);
  const [data, setData ] = useState('');
  const [show, setShow] = useState(false);
  const [toastdata, setToastData] = useState('');
  const [engFoodName, setEngFoodName] = useState('');

  useEffect(() => {
    const token = localStorage.getItem('hancook-token');
    if (!token) {
      navigate('/login')
    }
  }, [navigate]);

  const handleClose = () => setStatus(0);
  const handleNext = () => {
    if (status > 4) {
      setStatus(0);
      setToastData('Please take a picture again.');
      setShow(true);
    } else {
      axios.get(`${process.env.REACT_APP_API_URL}/translate/koreantoenglish?text=${data[status-1].food_name}`)
          .then((res) => {
            setEngFoodName(res.data)
          }).catch((err) => {
          
          })
      setStatus(status+1);
    }
  }
  const handleYes = useCallback ((name) => {
    
    axios.get(`${process.env.REACT_APP_API_URL}/food/check?name=${name}`)
    .then((res) => {
      if (res.data.checkFlag === -1) {
        setToastData('No matching food found.');
        setShow(true);
      } else if (res.data.checkFlag === 1) {
        setStatus(0);
        navigate(`/dish/${res.data.id}`);
      } else {
        setStatus(0);
        navigate(`/ingredient/${res.data.id}`);
      }
  
    }).catch((err) => {
   
    })
  }, [navigate]);

  const videoRef = useRef(null);
  const canvasRef = useRef(null);

  useEffect(() => {
    getVideo();
  });

  const getVideo = async () => {
    try {
        const stream = await navigator.mediaDevices.getUserMedia({
          video: {
            facingMode: { exact: 'environment' },
          },
        });
        // const video = videoRef.current;
        // if (video) {
        //   video.srcObject = stream;
        //   video.onloadedmetadata = () => {
        //     video.play();
        //   };
        // }
        handleStream(stream);
    } catch {
      try {
        const stream = await navigator.mediaDevices.getUserMedia({
          video: {
            facingMode: 'user',
          },
        });
        handleStream(stream);
      } catch (err) {
    
      }
    };
  }

  const handleStream = (stream) => {
    const video = videoRef.current;
    if (video) {
      video.srcObject = stream;
      video.onloadedmetadata = () => {
        video.play();
      };
    }
  };

  const getCapture = useCallback((event) => {
    event.stopPropagation();
    const canvas = canvasRef.current;
    const video = videoRef.current;
    if (canvas && video) {
      const context = canvas.getContext('2d');
      context.drawImage(video, 0, 0, canvas.width, canvas.height);
      const dataUrl = canvas.toDataURL('image/png')
  
      imageMaker(dataUrl);
    }
  }, []);
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

    axios.post(`${process.env.REACT_APP_API_URL}/food/recognize`, formData)
      .then(response => {
        // API 요청 성공 시 처리할 코드
        if (response.data.result.length === 0) {
          setToastData('No matching food found.')
          setShow(true);
        } else {
          // 결과 배열이 존재할 경우, ConfirmModal을 띄웁니다.
          setData(response.data.result[0].class_info)
          axios.get(`${process.env.REACT_APP_API_URL}/translate/koreantoenglish?text=${response.data.result[0].class_info[0].food_name}`)
          .then((res) => {
            setEngFoodName(res.data)
          }).catch((err) => {
         
          })
          setStatus(1);
        }
      })
      .catch(error => {
        // API 요청 실패 시 처리할 코드
        setToastData('An error has occurred.');
        setShow(true);
      });
  }

  return (
    <div className='background-black'>
      <div className='total-container'>
        <video ref={videoRef} className='camera-container'/>
        <div className='camera-menu-container'>
          <Menu />
          <span onClick={(event) => getCapture(event)} className='capture-button'><img src={camera} alt="" className='capture-button-image'/></span>
        </div>
      </div>
      <canvas ref={canvasRef} style={{display: 'none'}} />
      <Modal show={status !== 0} onHide={handleClose} backdrop='static' keyboard={false} centered>
        <ModalHeader closeButton>
          {engFoodName ? (
            <Modal.Title>{engFoodName}</Modal.Title>
          ) : ('Nothing')}
        </ModalHeader>
        <Modal.Footer className='confirm-button-container'>
          <button onClick={handleNext} className='confirm-no-button'>
            No
          </button>
          {data[status-1] ? (
            <button onClick={() => handleYes(data[status-1].food_name)} className='confirm-yes-button'>Yes</button>
            ) : ('')
          }
        </Modal.Footer>
      </Modal>
      <ToastContainer position='bottom-center' className='camera-toast'>
        <Toast onClose={() => setShow(false)} show={show} delay={3000} autohide bg='dark'>
          <Toast.Body>{toastdata}</Toast.Body>
        </Toast>
      </ToastContainer>
      <footer>
        <Footer />
      </footer>
    </div>
  );
}