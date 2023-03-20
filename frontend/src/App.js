import { Route, Routes } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import Start from './components/Start';
import Main from './components/Main';
import Login from './components/acoounts/LogIn';
import Signup from './components/acoounts/SignUp';

function App() {
  return (
    <Routes>
      <Route path='/' element={<Start />} />
      <Route path='/main' element={<Main />} />
      <Route path='/login' element={<Login />} />
      <Route path='/signup' element={<Signup />} />
    </Routes>
  );
}
  
export default App;