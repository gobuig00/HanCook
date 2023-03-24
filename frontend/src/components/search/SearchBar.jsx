import './SearchBar.css'

import React, { useState } from 'react';

const SearchBar = ({ onSearch }) => {
  const [searchTerm, setSearchTerm] = useState('');

  const handleChange = (event) => {
    setSearchTerm(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    onSearch(searchTerm);
  };

  return (
    <form onSubmit={handleSubmit} className='login-inner-form'>
      <input
        className='login-custom-placeholder'
        type="text"
        placeholder="검색어 입력"
        value={searchTerm}
        onChange={handleChange}
      />
      <button type="submit">검색</button>
    </form>
  );
};

export default SearchBar;



