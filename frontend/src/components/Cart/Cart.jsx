import React, { useState, useEffect } from 'react';
import axios from 'axios';
import html2canvas from 'html2canvas';

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
  const [cartItems, setCartItems] = useState({
    Radish: [
      { title: 'title1title1title1title1title1title1title1title1title1title1title1title1title1title1title1title1title1', price: 100 }, // e-mart
      { title: 'title2', price: 200 }, // lotte-mart
      { title: 'title3', price: 300 }, // home-plus
    ],
    Onion: [
      { title: 'title4', price: 100 }, // e-mart
      { title: 'title5', price: 200 }, // lotte-mart
      { title: 'title6', price: 300 }, // home-plus
    ],
  });

  const [expanded, setExpanded] = useState({});
  const [selectedItems, setSelectedItems] = useState([[], [], []]);
  const Kakao = window.Kakao;
  
  useEffect(() => {
    async function fetchCartItems() {
      try {
        const response = await axios.get('/api/cart-items');
        setCartItems(response.data);
      } catch (error) {
        console.error('Error fetching cart items:', error);
      }
    }

    fetchCartItems();
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

  async function uploadImageToServer(base64Image) {
    try {
      const response = await axios.post('/api/upload-image', {
        image: base64Image,
      });
  
      return response.data.url;
    } catch (error) {
      console.error('Error uploading image to server:', error);
      return null;
    }
  }

  const handleShareClick = async () => {
    const receipt = document.querySelector('.cart-receipt');
    const canvas = await html2canvas(receipt);
    const base64Image = canvas.toDataURL('image/png');
    
    // Upload the captured image to the Spring server
    const imageUrl = await uploadImageToServer(base64Image);

    if (imageUrl) {
      // Use Kakao.Link.sendDefault() to share the image URL
      Kakao.Link.sendDefault({
        objectType: 'feed',
        content: {
          title: 'Receipt',
          imageUrl,
          link: {
            mobileWebUrl: imageUrl,
            webUrl: imageUrl,
          },
        },
      });
    } else {
      console.error('Failed to upload image to Server');
    }
  };

  return (
    <div className='cart-container'>
      <div className='cart-ingredients'>
        <div className='cart-header'><b>{Object.keys(cartItems).length}</b> ingredient(s) selected</div>
        {Object.entries(cartItems).map(([ingredient, items]) => (
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
                <div key={index} className="cart-item" onClick={() => selectItem(item, index)}>
                  <ItemList title={item.title} price={item.price} index={index} />
                </div>
              ))
            }
          </div>
        ))}
      </div>
      <div className="cart-receipt">
        <div className="receipt-header">
          <h3>receipt</h3>
          <i className="material-icons" onClick={handleShareClick}>share</i>
        </div>
          <hr />
        <div className="receipt-body">
          {['E-Mart', 'Lotte-Mart', 'Home-Plus'].map((martName, index) => {
            const selectedMartItems = selectedItems[index];

            return (
              <div key={index} className={`receipt-body-${martName}`}>
                <div className={`receipt-body-${martName}-header`}>
                  <div className={`receipt-body-${martName}-image`} />
                  <h3>{martName}</h3>
                </div>
                <div className={`receipt-body-${martName}-body`}>
                  {selectedMartItems.map((item, itemIndex) => (
                    <div key={item.title} className="selected-item">
                        <div className='selected-item-title'>
                            <i className="material-icons" onClick={() => deleteSelectedItem(index, itemIndex)}>close</i>
                            <p>{item.title}</p>
                        </div>
                        <p className='selected-item-price'>{item.price}₩</p>
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
    </div>
  );
}