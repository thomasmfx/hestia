import { useState } from 'react';
import TextField from '../../components/TextField/TextField';
import PasswordField from '../../components/PasswordField/PasswordField';
import { isPasswordValid } from '../../lib/password';
import { isEmailValid, formatTelefone } from '../../lib/validation';
import './AuthForm.scss';

interface SignupFormProps {
  onSuccess: (data: { nome: string; email: string }) => void;
  onSwitch: () => void;
}

export default function SignupForm({ onSuccess, onSwitch }: SignupFormProps) {
  const [nome, setNome] = useState('');
  const [email, setEmail] = useState('');
  const [telefone, setTelefone] = useState('');
  const [senha, setSenha] = useState('');
  const [acceptedTerms, setAcceptedTerms] = useState(false);

  const canSubmit =
    acceptedTerms &&
    isPasswordValid(senha) &&
    nome.trim() !== '' &&
    isEmailValid(email) &&
    telefone.trim() !== '';

  const preventNav = (e: React.SyntheticEvent) => e.preventDefault();

  function handleSubmit(e: React.SyntheticEvent) {
    e.preventDefault();
    if (!canSubmit) return;
    onSuccess({ nome: nome.trim(), email });
  }

  return (
    <form className="auth-form" onSubmit={handleSubmit} noValidate>
      <h1 className="auth-form__title">Criar sua conta</h1>
      <p className="auth-form__subtitle">
        Leva menos de um minuto. Os dados de endereço ficam para a primeira
        reserva.
      </p>

      <TextField
        label="Nome completo"
        value={nome}
        onChange={setNome}
        placeholder="Marina Duarte"
        autoComplete="name"
      />

      <div className="auth-form__row">
        <TextField
          label="E-mail"
          type="email"
          value={email}
          onChange={setEmail}
          placeholder="marina@exemplo.com"
          autoComplete="email"
        />
        <TextField
          label="Telefone"
          type="tel"
          value={telefone}
          onChange={(v) => setTelefone(formatTelefone(v))}
          placeholder="(11) 98765-4321"
          autoComplete="tel"
        />
      </div>

      <PasswordField
        value={senha}
        onChange={setSenha}
        placeholder="Crie uma senha"
        autoComplete="new-password"
        showStrength
      />

      <label className="auth-form__terms">
        <input
          type="checkbox"
          checked={acceptedTerms}
          onChange={(e) => setAcceptedTerms(e.target.checked)}
        />
        <span>
          Li e aceito os{' '}
          <a href="#" onClick={preventNav}>
            Termos de Serviço
          </a>{' '}
          e a{' '}
          <a href="#" onClick={preventNav}>
            Política de Privacidade
          </a>
          .
        </span>
      </label>

      <button type="submit" className="auth-form__submit" disabled={!canSubmit}>
        Criar minha conta
      </button>

      <p className="auth-form__switch">
        Já tem conta?{' '}
        <button type="button" onClick={onSwitch}>
          Entrar
        </button>
      </p>
    </form>
  );
}
