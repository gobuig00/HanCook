import SearchBar from './SearchBar';
import axios from 'axios'
import { useState, useEffect } from 'react'
import './Search.css'
import { Link, useNavigate } from 'react-router-dom';


export default function Search() {
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('hancook-token');
    if (!token) {
      navigate('/login')
    }
  }, [navigate]);

  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleSearch = (searchTerm) => {
    setLoading(true);
    const params = {
      name: searchTerm,
    };
    axios.get('http://192.168.100.172:8080/recipe/search', { params })
    .then(response => {
      setResult(response.data)
      setLoading(false)
    }).catch(err => {
      console.log(err)
      setLoading(false)
    })
  }
  return (
    <div className='background-green screen-full'>
      <SearchBar onSearch={handleSearch} />
      <div className='search-result-container'>
        {loading ? 'Loading...' : result ? (result.map((item, index) => (
          <Link to={'/dish/'+item.recipeId} key={index} className={`search-result-link ${index % 3 !== 0 ? 'link-margin' : ''}`}>
            <div className='search-result-image-container'>
              <img src={item.img} alt="" className='search-result-image'/>
            </div>
          </Link>
        ))) : ('')}
      </div>
    </div>
  );
}