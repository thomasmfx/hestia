import { Link } from 'react-router-dom';
import { BookOpen } from 'react-feather';
import EmptyState from './EmptyState';

// Minhas reservas — empty state (ainda não há reservas).
export default function ReservationsSection() {
  return (
    <section className="account__card">
      <div className="account__card-head">
        <h2 className="account__card-title">Minhas reservas</h2>
        <button
          type="button"
          className="account__link-action"
          disabled
          title="Em breve"
        >
          Ver todas
        </button>
      </div>
      <EmptyState
        icon={<BookOpen size={28} />}
        title="Seu histórico começa na primeira reserva"
        description="Escolha um quarto que combine com você e comece a colecionar boas estadias."
        action={
          <Link className="account__cta" to="/">
            Explorar quartos <span aria-hidden="true">→</span>
          </Link>
        }
      />
    </section>
  );
}
