import Footer from './Footer';
import Donut from './Donut';
import './Dish.css';
import Table from './Table';
import AddShoppingCart from '../icons/AddShoppingCart.svg';
import logo from '../images/logo.png'
import { Link, useParams } from 'react-router-dom';
import axios from 'axios';
import {useState, useEffect } from 'react'

export function Youtube(props) {
  const target = props.target;
  // const [videoId, setVideoId] = useState('');
  useEffect(() => { 
  }, [target]);
  return(
    <div className='video-container'>
      <iframe width="560" height="315" src={`https://www.youtube.com/embed/${props.target}`} title="YouTube video player" frameBorder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowFullScreen></iframe>
    </div>
  );
}

function useRecipeAPI() {
  const params = {
    recipeId: useParams().id,
  };
  const [data, setData] = useState(null);
  useEffect(() => {
    axios.get('http://192.168.100.172:8080/recipe/id', { params })
    .then(function (response) {
      setData(response.data);
    })
    .catch(function (err) {
      console.log(err);
    });
  }, []);
  return data;
}

function RecipeIngredient(props) {
  let result = ''
  for (let i=0; i<props.data.length; i++) {
    if (i !== (props.data.length-1)) {
      result += props.data[i].name + props.data[i].capacity + ', '
    } else {
      result += props.data[i].name + props.data[i].capacity
    }
  }
  return (
    <div>
      {result}
    </div>
  )
}


function Dish() {
  const data = useRecipeAPI();
  return (
    <div className='background-green'>
      <header>
        <Link to='/main'>
          <img className="ingredient-logo" src={logo} alt="로고"/>
        </Link>
      </header>
      <main>
        <div className='ingredient-container'>
          {data ? (
              <img src={data.recipe.img} alt="" className='ingredient-image' />
          ) : (
            'Loading...'
          )}
          <div className='text-center'>
            <img src={AddShoppingCart} alt="" className='add-shopping-cart-icon'/>
            {data ? (
                <>
                  <span className='ingredient-name'>{data.recipe.name}</span>
                  <br />
                  <span className='ingredient-pronunciation'>(dwejigogi kimchijjige)</span>
                </>
              ) : (
                'Loading...'
              )}
          </div>
        </div>
        <Donut />
        <Table />
        <div className='green-line'></div>
        <div className='related-food-text'>Recipe</div>
        <br />
        {data ? (
          <>
            <div className='semi-title-text'>Ingredient</div>
            <RecipeIngredient data={data.ingredient}/>
            <br />
            <div className='semi-title-text'>Process</div>
            <ol>
              {data.process.map((item, index) => (
                <li key={index}>
                  {item.description}
                </li>
              ))}
            </ol>
            <div className='green-line'></div>
            <div className='related-food-text'>Video</div>
            <Youtube target={data.recipe.youtubeId}/>
          </>
        ) : (
          'Loading...'
        )}
      </main>
      <footer>
        <Footer />
      </footer>
    </div>
  );
}

export default Dish;