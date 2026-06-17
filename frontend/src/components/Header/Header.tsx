import { useEffect, useRef, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Calendar, ChevronDown, LogOut, User } from 'react-feather';
import Button from '../Button/Button';
import Logo from '../Logo/Logo';
import { clearSession, clearToken, getSession } from '../../lib/session';
import { firstName, initials } from '../../lib/format';
import './Header.scss';

export default function Header() {
  const location = useLocation();
  const navigate = useNavigate();
  const activeClass = (link: string) =>
    location.pathname === link ? 'header__nav-link--active' : '';

  // Relido a cada navegação (o Header re-renderiza quando a rota muda).
  const session = getSession();
  const [menuOpen, setMenuOpen] = useState(false);
  const menuRef = useRef<HTMLDivElement>(null);

  // Fecha o dropdown com Esc ou clique fora.
  useEffect(() => {
    if (!menuOpen) return;
    const onKey = (e: KeyboardEvent) => {
      if (e.key === 'Escape') setMenuOpen(false);
    };
    const onClick = (e: MouseEvent) => {
      if (menuRef.current && !menuRef.current.contains(e.target as Node)) {
        setMenuOpen(false);
      }
    };
    document.addEventListener('keydown', onKey);
    document.addEventListener('mousedown', onClick);
    return () => {
      document.removeEventListener('keydown', onKey);
      document.removeEventListener('mousedown', onClick);
    };
  }, [menuOpen]);

  function handleLogout() {
    clearSession();
    clearToken();
    setMenuOpen(false);
    navigate('/');
  }

  const displayName = session?.nome ?? session?.email ?? '';

  return (
    <header className="header">
      <Logo />
      <nav className="header__nav">
        <Link
          className={`header__nav-link ${activeClass('/quartos')}`}
          to="/"
        >
          Quartos
        </Link>
        <Link
          className={`header__nav-link ${activeClass('/comodidades')}`}
          to="/"
        >
          Comodidades
        </Link>
        <Link
          className={`header__nav-link ${activeClass('/sobre')}`}
          to="/"
        >
          Sobre
        </Link>
      </nav>
      <nav className="header__nav">
        {session ? (
          <>
            <Button>Reservar</Button>
            <div className="header__account" ref={menuRef}>
              <button
                type="button"
                className="header__account-trigger"
                onClick={() => setMenuOpen((open) => !open)}
                aria-haspopup="menu"
                aria-expanded={menuOpen}
              >
                <span className="header__avatar">{initials(displayName)}</span>
                <span className="header__account-name">
                  {firstName(displayName)}
                </span>
                <ChevronDown size={16} className="header__chevron" />
              </button>

              {menuOpen && (
                <div className="header__menu" role="menu">
                  <div className="header__menu-header">
                    <span className="header__menu-name">
                      {session.nome ?? 'Hóspede'}
                    </span>
                    <span className="header__menu-email">{session.email}</span>
                  </div>
                  <Link
                    className="header__menu-item"
                    to="/account"
                    role="menuitem"
                    onClick={() => setMenuOpen(false)}
                  >
                    <User size={16} /> Minha Conta
                  </Link>
                  <Link
                    className="header__menu-item"
                    to="/account"
                    role="menuitem"
                    onClick={() => setMenuOpen(false)}
                  >
                    <Calendar size={16} /> Minhas Reservas
                  </Link>
                  <button
                    type="button"
                    className="header__menu-item header__menu-item--danger"
                    role="menuitem"
                    onClick={handleLogout}
                  >
                    <LogOut size={16} /> Sair
                  </button>
                </div>
              )}
            </div>
          </>
        ) : (
          <>
            <Link
              className={`header__nav-link ${activeClass('/login')}`}
              to="/login"
            >
              Entrar
            </Link>
            <Button>Reservar</Button>
          </>
        )}
      </nav>
    </header>
  );
}
