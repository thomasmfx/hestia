import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import type { Hospede } from '../../lib/types';
import { getMe, UnauthorizedError } from '../../lib/api';
import { clearSession, clearToken } from '../../lib/session';
import { firstName } from '../../lib/format';
import NextStaySection from './NextStaySection';
import PersonalDataCard from './PersonalDataCard';
import ReservationsSection from './ReservationsSection';
import PreferencesSection from './PreferencesSection';
import './Account.scss';

type Status = 'loading' | 'error' | 'ready';

export default function Account() {
  const navigate = useNavigate();
  const [hospede, setHospede] = useState<Hospede | null>(null);
  const [status, setStatus] = useState<Status>('loading');
  const [reloadKey, setReloadKey] = useState(0);

  useEffect(() => {
    let cancelled = false;
    getMe()
      .then((me) => {
        if (cancelled) return;
        setHospede(me);
        setStatus('ready');
      })
      .catch((err: unknown) => {
        if (cancelled) return;
        // Token inválido/expirado: encerra a sessão e volta ao login.
        if (err instanceof UnauthorizedError) {
          clearSession();
          clearToken();
          navigate('/login', { replace: true });
          return;
        }
        setStatus('error');
      });
    return () => {
      cancelled = true;
    };
  }, [navigate, reloadKey]);

  function handleRetry() {
    setStatus('loading');
    setReloadKey((key) => key + 1);
  }

  return (
    <main className="account">
      <div className="account__container">
        <header className="account__greeting">
          <h1 className="account__greeting-title">
            Olá,{' '}
            {status === 'loading' ? (
              <span className="account__skeleton account__skeleton--name" />
            ) : (
              firstName(hospede?.nome ?? 'visitante')
            )}
          </h1>
          <p className="account__greeting-subtitle">
            Que bom ter você de volta.
          </p>
        </header>

        <NextStaySection />

        <div className="account__columns">
          <PersonalDataCard
            hospede={hospede}
            status={status}
            onRetry={handleRetry}
          />
          <div className="account__column">
            <ReservationsSection />
            <PreferencesSection />
          </div>
        </div>
      </div>
    </main>
  );
}
