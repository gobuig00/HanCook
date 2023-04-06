import './Menu.css';
import React from 'react';
import { Carousel } from 'react-responsive-carousel';
import 'react-responsive-carousel/lib/styles/carousel.min.css';
import { useState } from 'react';

const styles = {
  slide: {
    minHeight: 70,
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    fontFamily: 'semibold',
  },
  slide1: {
  },
  slide2: {
  },
};

const Menu = () => {
  const [selectedSlide, setSelectedSlide] = useState(0);

  const handleClick = (index) => {
    setSelectedSlide(index);
  };

  return (
    <div className='menu-carousel-position'>
      <div className='selected-background'></div> 
      <Carousel
        showStatus={false}
        showThumbs={false}
        showIndicators={false}
        showArrows={false}
        swipeable={true}
        dynamicHeight={false}
        emulateTouch={false}
        selectedItem={selectedSlide}
        onChange={(index) => setSelectedSlide(index)}
      >
        <div style={Object.assign({}, styles.slide, styles.slide1)} onClick={() => handleClick(0)}>
          <span className='carousel-selected-text'>Food</span> 
        </div>
      </Carousel>
    </div>
  );
};

export default Menu;