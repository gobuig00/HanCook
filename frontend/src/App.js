import { Route, Routes } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import Start from './Start';
import Main from './Main';
// import Login from './components/acoounts/LogIn'

function App() {
  return (
    <Routes>
      <Route path='/' element={<Start />} />
      <Route path='/main' element={<Main />} />
      {/* <Route path='/login' element={<Login />} /> */}
    </Routes>
  );
}
  
export default App;