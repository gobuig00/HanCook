import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {BrowserRouter} from 'react-router-dom';

const rootelement = document.getElementById('root')
if (rootelement) {
  const root = ReactDOM.createRoot(rootelement);
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </React.StrictMode>
);
}

const globalErrorHandler = window.onerror;
window.onerror = (message, source, lineno, colno, error) => {
  if (message === 'ResizeObserver loop limit exceeded') {
    return false;
  }
  if (globalErrorHandler) {
    return globalErrorHandler(message, source, lineno, colno, error);
  }
  return false;
};




// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
