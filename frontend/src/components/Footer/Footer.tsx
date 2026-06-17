import Logo from '../Logo/Logo';
import { Link } from 'react-router-dom';
import './Footer.scss';

export default function Footer() {
  return (
    <footer className="footer">
      <div className="footer__content footer__content--main">
        <Logo />
        <p className="footer__content-text">
          Uma experiência de hotel boutique que une serviço de alto padrão e
          calor humano.
        </p>
        <p className="footer__content-opaque">
          © 2026 HESTIA Hotel Boutique. Todos os direitos reservados.
        </p>
      </div>

      <div className="footer__content">
        <h3 className="footer__content-title">EXPLORAR</h3>
        <Link className="footer__content-link" to="/">
          Quartos
        </Link>
        <Link className="footer__content-link" to="/">
          Comodidades
        </Link>
        <Link className="footer__content-link" to="/">
          Sobre
        </Link>
      </div>

      <div className="footer__content">
        <h3 className="footer__content-title">LEGAL</h3>
        <Link className="footer__content-link" to="#">
          Política de Privacidade
        </Link>
        <Link className="footer__content-link" to="#">
          Termos de Serviço
        </Link>
      </div>

      <div className="footer__content">
        <h3 className="footer__content-title">EXPLORAR</h3>
        <Link className="footer__content-link" to="#">
          Fale Conosco
        </Link>
        <Link className="footer__content-link" to="#">
          Instagram
        </Link>
      </div>
    </footer>
  );
}
