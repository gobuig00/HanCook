import React, { useState, useRef, useEffect} from 'react';
import axios from 'axios';
import Footer from '../Footer';
import { useNavigate } from 'react-router-dom';
import './Camera.css';
import Menu from './Menu';
import camera from '../../icons/camera.png';
import Modal from 'react-bootstrap/Modal';
import { ModalHeader } from 'react-bootstrap';
import Toast from 'react-bootstrap/Toast';


export default function Camera() {
  const navigate = useNavigate();
  const [status, setStatus] = useState(0);
  const [data, setData ] = useState('');
  const [show, setShow] = useState(false);
  const [toastdata, setToastData] = useState('');

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
      setToastData('다시 한 번 사진을 찍어주세요.');
      setShow(0);
    } else {
      setStatus(status+1);
    }
  }

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

    axios.post(`${process.env.REACT_APP_API_URL}/food/recognize`, formData)
      .then(response => {
        // API 요청 성공 시 처리할 코드
        if (response.data.result.length === 0) {
          setToastData('일치하는 음식이 없습니다.')
          setShow(true);
        } else {
          // 결과 배열이 존재할 경우, ConfirmModal을 띄웁니다.
          console.log(response.data.result[0].class_info)
          setData(response.data.result[0].class_info)
          setStatus(1);
        }
      })
      .catch(error => {
        // API 요청 실패 시 처리할 코드
        setToastData('오류가 발생했습니다.');
        setShow(true);
      });
  }

  return (
    <div className='background-black'>
      <div className='total-container'>
        <video ref={videoRef} className='camera-container'/>
        <div className='camera-menu-container'>
          <Menu />
          <span onClick={getCapture} className='capture-button'><img src={camera} alt="" className='capture-button-image'/></span>
        </div>
      </div>
      <canvas ref={canvasRef} style={{display: 'none'}} />
      <Modal show={status !== 0} onHide={handleClose} backdrop='static' keyboard={false} centered>
        <ModalHeader closeButton>
          {data[status-1] ? (
            <Modal.Title>{data[status-1].food_name}, right?</Modal.Title>
          ) : ('Nothing')}
        </ModalHeader>
        <Modal.Body>
          {/* <img src={data} alt="" /> */}
          맞음?
        </Modal.Body>
        <Modal.Footer className='confirm-button-container'>
          <button onClick={handleNext} className='confirm-no-button'>
            No
          </button>
          <button onClick={handleClose} className='confirm-yes-button'>
            Yes
          </button>
        </Modal.Footer>
      </Modal>
      <Toast onClose={() => setShow(false)} show={show} delay={3000} autohide>
        <Toast.Body>{toastdata}</Toast.Body>
      </Toast>
      <footer>
        <Footer />
      </footer>
    </div>
  );
}