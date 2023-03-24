import SearchBar from './SearchBar';
import axios from 'axios'

export default function Search() {
  const handleSearch = (searchTerm) => {
    const params = {
      ingredient: searchTerm,
    };
    axios.get('http://192.168.100.172:8080/recipe/search', { params })
    .then(response => {
      console.log(response)
    }).catch(err => {
      console.log(err)
    })
  }
  return (
    <div>
      <SearchBar onSearch={handleSearch} />
    </div>
  );
}