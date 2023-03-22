import { Route, Routes } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import Start from './components/Start';
import Main from './components/Main';
import Login from './components/acoounts/LogIn';
import Signup from './components/acoounts/SignUp';
import Ingredient from './components/Ingredient';
import Dish from './components/Dish'
import Profile from './components/profile/Profile';

function App() {
  return (
    <Routes>
      <Route path='/' element={<Start />} />
      <Route path='/main' element={<Main />} />
      <Route path='/login' element={<Login />} />
      <Route path='/signup' element={<Signup />} />
      <Route path='/ingredient/:id' element={<Ingredient />} />
      <Route path='/dish/:id' element={<Dish />} />
      <Route path='/profile/' element={<Profile />} />
    </Routes>
  );
}
  
export default App;