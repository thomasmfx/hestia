import { Link } from 'react-router-dom';
import './Logo.scss';

export default function Logo() {
  return (
    <div>
      <Link className="logo" to="/">
        Hestia
      </Link>
    </div>
  );
}
