import './Start.css';
import logo from './image/logo.png'

function Start() {
  return (
    <div className="start">
      <header className="start-header">
        <img className="start-logo" src={logo} alt="" />
      </header>
    </div>
  );
}

export default Start;
