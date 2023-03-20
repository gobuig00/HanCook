import './Start.css';
import { Link } from 'react-router-dom';
import {Button} from 'react-bootstrap';
import logo from '../images/logo.png';
import seasonal1 from '../images/seasonal1.jpg';
import seasonal2 from '../images/seasonal2.jpg';
import seasonal3 from '../images/seasonal3.jpg';
import seasonal4 from '../images/seasonal4.jpg';
import takepicture from '../images/takepicture.jpg';

function Start() {
  return (
    <div className="start">
      <header className="start-header">
        <img className="start-logo" src={logo} alt="로고"/>
      </header>
      <main>
        <div className='main-first'>
          <img className="take-picture-image" src={takepicture} alt="" />
          <div className='main-text-container'>
            <p className='main-text-lineheight'><span className='green-text'>Searching </span><span className='black-text'>for recipes</span></p>
            <p className='main-text-lineheight'><span className='black-text'>through </span><span className='orange-text'>pictures</span></p>
          </div>
          <div className='main-small-text-container'>
            <p className='main-smaill-text-lineheight'>you can get information about food</p>
            <p className='main-smaill-text-lineheight'>just by taking pictures.</p>
          </div>
          
        </div>
        <div className='main-second'>

        </div>
        <div className='third'>

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
