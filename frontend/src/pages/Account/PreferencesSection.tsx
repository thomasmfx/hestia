import { Plus, Sliders } from 'react-feather';
import EmptyState from './EmptyState';

// Preferências de estadia — empty state (sem campo no backend ainda).
export default function PreferencesSection() {
  return (
    <section className="account__card">
      <h2 className="account__card-title">Preferências de estadia</h2>
      <EmptyState
        icon={<Sliders size={28} />}
        title="Ainda não há preferências salvas"
        description="Conte o que deixa sua estadia perfeita — andar alto, travesseiro extra, café especial — e a gente cuida do resto."
        action={
          <button
            type="button"
            className="account__cta account__cta--ghost"
            disabled
            title="Em breve"
          >
            <Plus size={16} /> Adicionar preferência
          </button>
        }
      />
    </section>
  );
}
