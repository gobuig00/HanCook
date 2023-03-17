import './Start.css';
import logo from './image/logo.png';
import { Link } from 'react-router-dom';

function Start() {
  return (
    <div className="start">
      <header className="start-header">
        <img className="start-logo" src={logo} alt="로고"/>
      </header>
      <footer>
        <Link to="/main">
          <button className="get-start-button">Get Started</button>
        </Link>
      </footer>
    </div>
  );
}

export default Start;
