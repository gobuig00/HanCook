import './Start.css';
import { Link, useNavigate } from 'react-router-dom';
import {Button} from 'react-bootstrap';
import logo from '../images/logo.png';
import seasonal1 from '../images/seasonal1.jpg';
import seasonal2 from '../images/seasonal2.jpg';
import seasonal3 from '../images/seasonal3.jpg';
import seasonal4 from '../images/seasonal4.jpg';
import takepicture from '../images/takepicture.jpg';
import buyimage from '../images/buyimage.jpg';
import { useEffect } from 'react';

function Start() {
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('hancook-token');
    if (token) {
      navigate('/main')
    }
  }, [navigate]);
  return (
    <div className="start">
      <header className="start-header">
        <img className="start-logo" src={logo} alt="로고"/>
      </header>
      <main>
        <div className='main-first'>
          <img className="take-picture-image" src={takepicture} alt="" />
          <div className='main-text-container'>
            <p className='main-text-lineheight'><span className='green-text'>Searching&nbsp;</span><span className='black-text'>for recipes</span></p>
            <p className='main-text-lineheight'><span className='black-text'>through&nbsp;</span><span className='orange-text'>pictures</span></p>
          </div>
          <div className='main-small-text-container'>
            <p className='main-smaill-text-lineheight'>You can get information about food</p>
            <p className='main-smaill-text-lineheight'>just by taking pictures.</p>
          </div>
        </div>
        <div className='main-line'></div>
        <div className='main-second'>
          <div className='seasonal-image-container'>
            <img className='seasonal-image' src={seasonal1} alt="" />
            <img className='seasonal-image' src={seasonal2} alt="" />
            <img className='seasonal-image' src={seasonal3} alt="" />
            <img className='seasonal-image' src={seasonal4} alt="" />
          </div>
          <div className='main-text-container'>
            <p className='main-text-lineheight'><span className='black-text'>Get&nbsp;</span><span className='green-text'>seasonal&nbsp;</span><span className='black-text'>food</span></p>
            <p className='main-text-lineheight'><span className='orange-text'>recommendations</span></p>
          </div>
          <div className='main-small-text-container'>
            <p className='main-smaill-text-lineheight'>You can get information about food</p>
            <p className='main-smaill-text-lineheight'>just by taking pictures.</p>
          </div>
        </div>
        <div className='main-line'></div>
        <div className='main-third'>
          <img className='buyimage' src={buyimage} alt="" />
          <div className='main-text-container'>
            <p className='main-text-lineheight'><span className='green-text'>Price&nbsp;</span><span className='black-text'>comparison</span></p>
            <p className='main-text-lineheight'><span className='black-text'>by&nbsp;</span><span className='orange-text'>mart</span></p>
          </div>
          <div className='main-small-text-container'>
            <p className='main-smaill-text-lineheight'>You can get information about food</p>
            <p className='main-smaill-text-lineheight'>just by taking pictures.</p>
          </div>
        </div>
      </main>
      <footer>
        <Link to="/login">
          <Button className="get-started-button">Get Started</Button>
        </Link>
      </footer>
    </div>
  );
}

export default Start;
