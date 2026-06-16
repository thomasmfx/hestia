import { useState } from 'react';
import TextField from '../../components/TextField/TextField';
import PasswordField from '../../components/PasswordField/PasswordField';
import { isEmailValid } from '../../lib/validation';
import './AuthForm.scss';

interface LoginFormProps {
  onSuccess: (data: { email: string }) => void;
  onSwitch: () => void;
}

export default function LoginForm({ onSuccess, onSwitch }: LoginFormProps) {
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');

  const canSubmit = isEmailValid(email) && senha.trim() !== '';

  const preventNav = (e: React.SyntheticEvent) => e.preventDefault();

  function handleSubmit(e: React.SyntheticEvent) {
    e.preventDefault();
    if (!canSubmit) return;
    onSuccess({ email });
  }

  return (
    <form className="auth-form" onSubmit={handleSubmit} noValidate>
      <h1 className="auth-form__title">Bem-vindo de volta</h1>
      <p className="auth-form__subtitle">
        Entre para acessar suas reservas e preferências.
      </p>

      <TextField
        label="E-mail"
        type="email"
        value={email}
        onChange={setEmail}
        placeholder="marina@exemplo.com"
        autoComplete="email"
      />

      <PasswordField
        value={senha}
        onChange={setSenha}
        placeholder="Sua senha"
        autoComplete="current-password"
      />

      <a className="auth-form__forgot" href="#" onClick={preventNav}>
        Esqueci minha senha
      </a>

      <button type="submit" className="auth-form__submit" disabled={!canSubmit}>
        Entrar
      </button>

      <p className="auth-form__switch">
        Não tem conta?{' '}
        <button type="button" onClick={onSwitch}>
          Cadastre-se
        </button>
      </p>
    </form>
  );
}
