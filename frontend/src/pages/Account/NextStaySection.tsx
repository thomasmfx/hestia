import { Link } from 'react-router-dom';
import { Calendar } from 'react-feather';
import EmptyState from './EmptyState';

// Próxima estadia — empty state (ainda não há reservas).
export default function NextStaySection() {
  return (
    <section className="account__card account__next-stay">
      <span className="account__card-eyebrow">Sua próxima estadia</span>
      <EmptyState
        icon={<Calendar size={28} />}
        title="Nenhuma estadia à vista — por enquanto"
        description="Quando você reservar, sua próxima estadia aparece aqui, com check-in, número da reserva e atalhos para gerenciar tudo."
        action={
          <Link className="account__cta" to="/">
            Encontrar um quarto <span aria-hidden="true">→</span>
          </Link>
        }
      />
    </section>
  );
}
