import { useLocation } from 'react-router-dom';
import { Link } from 'react-router-dom';
import Button from '../Button/Button';
import Logo from '../Logo/Logo';
import './Header.scss';

export default function Header() {
  const currentRoute = useLocation().pathname;
  const activeClass = (link: string) =>
    currentRoute === link ? 'header__nav-link--active' : '';

  return (
    <header className="header">
      <Logo />
      <nav className="header__nav">
        <Link
          className={`header__nav-link ${activeClass('/quartos')}`}
          to="/quartos"
        >
          Quartos
        </Link>
        <Link
          className={`header__nav-link ${activeClass('/comodidades')}`}
          to="/comodidades"
        >
          Comodidades
        </Link>
        <Link
          className={`header__nav-link ${activeClass('/sobre')}`}
          to="/sobre"
        >
          Sobre
        </Link>
      </nav>
      <nav className="header__nav">
        <Link
          className={`header__nav-link ${activeClass('/login')}`}
          to="/login"
        >
          Entrar
        </Link>
        <Button>Reservar</Button>
      </nav>
    </header>
  );
}
