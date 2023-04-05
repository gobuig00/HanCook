import React, { useState, useEffect } from 'react';
import axios from 'axios';
import html2canvas from 'html2canvas';
import Footer from '../Footer';

import './Cart.css';
import ItemList from './ItemList';

// Utility functions
import {
  toggleExpandUtil,
  deleteIngredientUtil,
  selectItemUtil,
  deleteSelectedItemUtil,
  getTotalPriceByMartUtil,
  getTotalPriceUtil,
} from './CartUtils';



export default function Cart() {
  const [cartItems, setCartItems] = useState(null);
  const [expanded, setExpanded] = useState({});
  const [selectedItems, setSelectedItems] = useState([[], [], []]);
  const Kakao = window.Kakao;
  
  useEffect(() => {
    const headers = {
      'Authorization' : `Bearer ${localStorage.getItem('hancook-token')}`,
      'Content-Type': 'application/json',
    }
    axios.get(`${process.env.REACT_APP_API_URL}/mart?lan=1`, { headers: headers })
    .then((res) => {
      setCartItems(res.data)
    }).catch((err) => {
      console.log(err)
    })

    // fetchCartItems();
    // Initialize the Kakao SDK with your app's JavaScript key

    // Check if the SDK is initialized
    if (Kakao.isInitialized()) {
      console.log('Kakao SDK initialized.');
    } else {
      console.error('Failed to initialize Kakao SDK.');
  }
  }, []);

  

  const toggleExpand = (ingredient) => {
    setExpanded((prevExpanded) => toggleExpandUtil(prevExpanded, ingredient));
  };

  const deleteIngredient = (ingredient) => {
    setCartItems((prevCartItems) => deleteIngredientUtil(prevCartItems, ingredient));
  };

  const selectItem = (item, index) => {
    setSelectedItems((prevSelectedItems) => selectItemUtil(prevSelectedItems, item, index));
  };

  const deleteSelectedItem = (martIndex, itemIndex) => {
    setSelectedItems((prevSelectedItems) => deleteSelectedItemUtil(prevSelectedItems, martIndex, itemIndex));
  };

  const totalPrice = getTotalPriceUtil(selectedItems, getTotalPriceByMartUtil);

  function dataURItoBlob(dataURI) {
    const byteString = atob(dataURI.split(',')[1]);
    const mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
    const ab = new ArrayBuffer(byteString.length);
    const ia = new Uint8Array(ab);
    for (let i = 0; i < byteString.length; i++) {
      ia[i] = byteString.charCodeAt(i);
    }
    return new Blob([ab], { type: mimeString });
  }

  async function uploadImageToImgbb(imageBlob) {
    try {
      const formData = new FormData();
      formData.append('image', imageBlob, 'image.png');
      const response = await axios.post(`https://api.imgbb.com/1/upload?key=${process.env.REACT_APP_IMGBB_APP_KEY}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      
      return response.data.data.url;
    } catch (error) {
      console.error('Error uploading image to server:', error);
      return null;
    }
  }

  const handleShareClick = async () => {
    const receipt = document.querySelector('.cart-receipt');
    const canvas = await html2canvas(receipt);
    const base64Image = canvas.toDataURL('image/png');
  
    const imageBlob = dataURItoBlob(base64Image);
  
    // Upload the captured image to the Spring server
    const imageUrl = await uploadImageToImgbb(imageBlob);
    console.log(imageUrl)
    
    if (imageUrl) {
      Kakao.Link.sendDefault({
        objectType: 'feed',
        content: {
          title: 'Check out my shopping list!',
          imageUrl: imageUrl,
          link: {
            mobileWebUrl: `${window.location.origin}/image/${encodeURIComponent(imageUrl)}`,
            webUrl: `${window.location.origin}/image/${encodeURIComponent(imageUrl)}`,
          },
        },
        buttons: [
          {
            title: 'View Shopping List',
            link: {
              mobileWebUrl: `${window.location.origin}/image/${encodeURIComponent(imageUrl)}`,
              webUrl: `${window.location.origin}/image/${encodeURIComponent(imageUrl)}`,
            },
          },
        ],
      });
    } else {
      alert('Failed to share the shopping list. Please try again.');
    }
  };

  return (
    <div className='cart-container'>
      <div className='cart-ingredients'>
        <div className='cart-header'><b>{cartItems ? Object.keys(cartItems).length : ''}</b> ingredient(s) selected</div>
        {cartItems ? (
          Object.entries(cartItems).map(([ingredient, items]) => (
          <div key={ingredient} className='cart-ingredien-part'>
            <div className='cart-ingredien-part-header'>
              <i className="material-icons" onClick={() => toggleExpand(ingredient)}>
                {expanded[ingredient] ? "expand_less" : "expand_more"}
              </i>
              <h3 onClick={() => toggleExpand(ingredient)}>{ingredient}</h3>
              <button onClick={() => deleteIngredient(ingredient)}>del</button>
            </div>
            {expanded[ingredient] &&
              items.map((item, index) => (
                <div key={index} className="cart-item" onClick={() => selectItem(item, item.mart-1)}>
                  <ItemList title={item.itemName} price={item.itemPrice} index={item.mart} />
                </div>
              ))
            }
          </div>
        ))) : ''}
      </div>
      <div className="cart-receipt">
        <div className="receipt-header">
          <h3>receipt</h3>
          {/* <i className="material-icons" onClick={handleShareClick}>share</i> */}
          <i className="material-icons" onClick={handleShareClick}>share</i>
  <div id="kakao-link-btn" style={{ display: 'none' }}></div>
        </div>
          <hr />
        <div className="receipt-body">
          {[ 'Lotte-Mart', 'Home-Plus', 'E-Mart'].map((martName, index) => {
            const selectedMartItems = selectedItems[index];

            return (
              <div key={index} className={`receipt-body-${martName}`}>
                <div className={`receipt-body-${martName}-header`}>
                  <div className={`receipt-body-${martName}-image`} />
                  <h3>{martName}</h3>
                </div>
                <div className={`receipt-body-${martName}-body`}>
                  {selectedMartItems.map((item, itemIndex) => (
                    <div key={item.itemName} className="selected-item">
                        <div className='selected-item-title'>
                            <i className="material-icons" onClick={() => deleteSelectedItem(index, itemIndex)}>close</i>
                            <p>{item.itemName}</p>
                        </div>
                        <p className='selected-item-price'>{item.itemPrice}₩</p>
                    </div>
                  ))}
                </div>
                <div className={`receipt-body-${martName}-footer`}>
                  <p className={`receipt-body-${martName}-footer-title`}>Total:</p>
                  <p className={`receipt-body-${martName}-footer-price`}>{getTotalPriceByMartUtil(selectedItems[index])}₩</p>
                </div>
                    <hr />
              </div>
            );
          })}
        </div>
        <div className='receipt-footer'>
          <p className='receipt-footer-title'>Total Price:</p>
          <p className='receipt-footer-price'>{totalPrice}₩</p>
        </div>
      </div>
      <footer>
        <Footer></Footer>
      </footer>
    </div>
  );
}