export const toggleExpandUtil = (prevExpanded, ingredient) => ({
    ...prevExpanded,
    [ingredient]: !prevExpanded[ingredient],
  });
  
  export const deleteIngredientUtil = (prevCartItems, ingredient) => {
    const newCartItems = { ...prevCartItems };
    delete newCartItems[ingredient];
    return newCartItems;
  };
  
  export const selectItemUtil = (prevSelectedItems, item, index) => {

    if (prevSelectedItems[index].some((selectedItem) => selectedItem.ingredientName === item.ingredientName)) {
  
      return prevSelectedItems;
    }
    const newSelectedItems = [...prevSelectedItems];
    newSelectedItems[index].push(item);
    return newSelectedItems;
  };
  
  export const deleteSelectedItemUtil = (prevSelectedItems, martIndex, itemIndex) => {
    const newSelectedItems = [...prevSelectedItems];
    newSelectedItems[martIndex] = newSelectedItems[martIndex].filter((item, index) => index !== itemIndex);
    return newSelectedItems;
  };
  
  export const getTotalPriceByMartUtil = (items) => {
    return items.reduce((totalPrice, currentItem) => {
      return totalPrice + parseInt(currentItem.itemPrice);
    }, 0);
  };
  
  export const getTotalPriceUtil = (selectedItems, getTotalPriceByMart) => {
    return selectedItems.reduce((totalPrice, currentMartItems) => {
      return totalPrice + getTotalPriceByMart(currentMartItems);
    }, 0);
  };