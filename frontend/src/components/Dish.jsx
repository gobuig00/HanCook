import Footer from './Footer';
import Donut from './Donut';
import './Dish.css';
import Table from './Table';
import Img from '../images/takepicture.jpg';
import AddShoppingCart from '../icons/AddShoppingCart.svg';
import logo from '../images/logo.png'
import { Link } from 'react-router-dom';
import axios from 'axios';
import {useState, useEffect } from 'react'

export function Youtube() {
  const target = 'Jeyuk bokkeum';
  const [videoId, setVideoId] = useState('');
  useEffect(() => { 
    const params = {
      key: process.env.REACT_APP_YOUTUBE_API_KEY,
      part: 'snippet',
      q: `${target} recipe`,
      type: 'video'
    };
    axios.get('https://www.googleapis.com/youtube/v3/search', { params }
    ).then(function (response) {
      setVideoId(response.data.items[0].id.videoId);
    }).catch(function (error) {
      console.log(error)
    })
  }, [target]);
  return(
    <div className='video-container'>
      <iframe width="560" height="315" src={`https://www.youtube.com/embed/${videoId}`} title="YouTube video player" frameBorder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowFullScreen></iframe>
    </div>
  );
}


function Ingredient() {
  return (
    <div className='background-green'>
      <header>
        <Link to='/main'>
          <img className="ingredient-logo" src={logo} alt="로고"/>
        </Link>
      </header>
      <main>
        <div className='ingredient-container'>
          <img src={Img} alt="" className='ingredient-image' />
          <div>
            <img src={AddShoppingCart} alt="" className='add-shopping-cart-icon'/>
            <div className='ingredient-text'><span className='ingredient-name'>Pork and kimchi stew</span><br /><span className='ingredient-pronunciation'>(dwejigogi kimchijjige)</span></div>
          </div>
        </div>
        <Donut />
        <Table />
        <div className='green-line'></div>
        <div className='related-food-text'>Recipe</div>
        <Youtube />
      </main>
      <footer>
        <Footer />
      </footer>

    </div>
  );
}

export default Ingredient;