import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Check } from 'react-feather';
import { setSession, setToken } from '../../lib/session';
import { getMe } from '../../lib/api';
import SignupForm from './SignupForm';
import LoginForm from './LoginForm';
import './Login.scss';

type Mode = 'cadastro' | 'login';

export default function Login() {
  const navigate = useNavigate();

  const [mode, setMode] = useState<Mode>('login');
  const [leaving, setLeaving] = useState(false);

  // Recebe o token (login ou cadastro), carrega o perfil para o header
  // e faz o cross-fade de 300ms antes de ir para a conta.
  async function completeAuth(token: string) {
    setToken(token);
    try {
      const me = await getMe();
      setSession({ nome: me.nome, email: me.email });
    } catch {
      // O perfil recarrega na própria página da conta, se necessário.
    }
    setLeaving(true);
    setTimeout(() => navigate('/account'), 300);
  }

  return (
    <main className={`login login--${mode} ${leaving ? 'login--leaving' : ''}`}>
      {/* ─── Painel da imagem ───────────────────────────────────────────── */}
      <section className="login__panel login__panel--image">
        <div className="login__overlay login__overlay--cadastro">
          <h2 className="login__overlay-title">
            Sua chave para
            <br />
            voltar sempre.
          </h2>
          <ul className="login__benefits login__benefits--cadastro">
            <li>
              <Check size={18} /> Reserve mais rápido
            </li>
            <li>
              <Check size={18} /> Acompanhe e gerencie suas reservas
            </li>
            <li>
              <Check size={18} /> Ofertas e tarifas exclusivas de membro
            </li>
          </ul>
        </div>
        <div className="login__overlay login__overlay--login">
          <h2 className="login__overlay-title">
            O bom filho à
            <br />
            casa torna.
          </h2>
          <ul className="login__benefits login__benefits--login">
            <li>
              <Check size={18} /> Suas reservas e preferências, sempre à mão
            </li>
            <li>
              <Check size={18} /> Continue de onde parou em segundos
            </li>
            <li>
              <Check size={18} /> Vantagens exclusivas esperando por você
            </li>
          </ul>
        </div>
      </section>

      {/* ─── Painel do formulário ───────────────────────────────────────── */}
      <section className="login__panel login__panel--form">
        <div className="login__form login__form--cadastro">
          <SignupForm
            onSuccess={completeAuth}
            onSwitch={() => setMode('login')}
          />
        </div>
        <div className="login__form login__form--login">
          <LoginForm
            onSuccess={completeAuth}
            onSwitch={() => setMode('cadastro')}
          />
        </div>
      </section>
    </main>
  );
}
