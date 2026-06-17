import type { Hospede } from '../../lib/types';
import { formatCpf, formatEndereco } from '../../lib/format';
import { formatTelefone } from '../../lib/validation';

type Status = 'loading' | 'error' | 'ready';

interface PersonalDataCardProps {
  hospede: Hospede | null;
  status: Status;
  onRetry: () => void;
}

// Dados pessoais — dados reais de GET /hospedes/me, com loading/erro.
export default function PersonalDataCard({
  hospede,
  status,
  onRetry,
}: PersonalDataCardProps) {
  return (
    <section className="account__card account__personal">
      <div className="account__card-head">
        <h2 className="account__card-title">Dados pessoais</h2>
        {status === 'ready' && (
          <button
            type="button"
            className="account__link-action"
            disabled
            title="Em breve"
          >
            Editar
          </button>
        )}
      </div>

      {status === 'loading' && <PersonalDataSkeleton />}

      {status === 'error' && (
        <div className="account__error">
          <p>Não foi possível carregar seus dados agora.</p>
          <button type="button" className="account__cta" onClick={onRetry}>
            Tentar de novo
          </button>
        </div>
      )}

      {status === 'ready' && hospede && (
        <>
          <dl className="account__fields">
            <Field label="Nome" value={hospede.nome} />
            <Field label="CPF" value={formatCpf(hospede.cpf)} />
            <Field label="E-mail" value={hospede.email} />
            <Field
              label="Telefone"
              value={formatTelefone(
                hospede.telefone.ddd + hospede.telefone.numero,
              )}
            />
            <Field
              label="Endereço"
              value={formatEndereco(hospede.endereco)}
              full
            />
          </dl>
          <div className="account__password-row">
            <span className="account__password-label">Senha</span>
            <button
              type="button"
              className="account__link-action"
              disabled
              title="Em breve"
            >
              Alterar senha
            </button>
          </div>
        </>
      )}
    </section>
  );
}

function Field({
  label,
  value,
  full,
}: {
  label: string;
  value: string;
  full?: boolean;
}) {
  return (
    <div className={`account__field ${full ? 'account__field--full' : ''}`}>
      <dt className="account__field-label">{label}</dt>
      <dd className="account__field-value">{value}</dd>
    </div>
  );
}

function PersonalDataSkeleton() {
  return (
    <div className="account__fields" aria-hidden="true">
      {[0, 1, 2, 3, 4].map((n) => (
        <div className="account__field" key={n}>
          <span className="account__skeleton account__skeleton--label" />
          <span className="account__skeleton account__skeleton--value" />
        </div>
      ))}
    </div>
  );
}
