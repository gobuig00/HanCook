import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Cart.css';
import ItemList from './ItemList';

export default function Cart() {
  const [cartItems, setCartItems] = useState({
    Radish: [
      { title: 'title1', price: 100 }, // e-mart
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
    setExpanded((prevExpanded) => ({
      ...prevExpanded,
      [ingredient]: !prevExpanded[ingredient],
    }));
  };

  const deleteIngredient = (ingredient) => {
    setCartItems((prevCartItems) => {
      const newCartItems = { ...prevCartItems };
      delete newCartItems[ingredient];
      return newCartItems;
    });
  };

  const selectItem = (item, index) => {
    setSelectedItems((prevSelectedItems) => {
      // 항목이 이미 추가된 경우, 추가하지 않고 이전 상태를 그대로 반환합니다.
      if (prevSelectedItems[index].some((selectedItem) => selectedItem.title === item.title)) {
        console.log('Item already added.');
        return prevSelectedItems;
      }
      const newSelectedItems = [...prevSelectedItems];
      newSelectedItems[index].push(item);
      return newSelectedItems;
    });
  };

  const deleteSelectedItem = (martIndex, itemIndex) => {
    setSelectedItems((prevSelectedItems) => {
      const newSelectedItems = [...prevSelectedItems];
      newSelectedItems[martIndex].splice(itemIndex, 1);
      return newSelectedItems;
    });
  };

  const getTotalPriceByMart = (items) => {
    return items.reduce((totalPrice, currentItem) => {
      return totalPrice + parseInt(currentItem.price);
    }, 0);
  };

  const totalPrice = selectedItems.reduce((totalPrice, currentMartItems) => {
    return totalPrice + getTotalPriceByMart(currentMartItems);
  }, 0);

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
              <h3>{ingredient}</h3>
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
          <i className="material-icons">share</i>
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
                  <p className={`receipt-body-${martName}-footer-price`}>{getTotalPriceByMart(selectedItems[index])}₩</p>
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