import React from 'react';
import { useParams } from 'react-router-dom';
import './ImagePage.css';

const ImagePage = () => {
  const { imageUrl } = useParams();
  const decodedImageUrl = decodeURIComponent(imageUrl);

  return (
    <div className='cart-image-container' style={{backgroundImage: `url(${decodedImageUrl})`,}}>
    </div>
  );
};

export default ImagePage;