import { Route, Routes } from 'react-router-dom';
import Start from './Start';
import Main from './Main';

function App() {
  return (
    <Routes>
      <Route path='/' element={<Start />} />
      <Route path='/main' element={<Main />} />
    </Routes>
  );
}
  
export default App;