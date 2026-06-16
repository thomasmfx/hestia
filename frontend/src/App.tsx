import { Outlet, useLocation } from 'react-router-dom';
import Header from './components/Header/Header';
import Footer from './components/Footer/Footer';

export default function App() {
  // `key` muda a cada rota → o wrapper remonta e o fade-in (pageFadeIn) reexecuta.
  const location = useLocation();
  return (
    <>
      <Header />
      <div className="page-transition" key={location.pathname}>
        <Outlet />
      </div>
      <Footer />
    </>
  );
}
