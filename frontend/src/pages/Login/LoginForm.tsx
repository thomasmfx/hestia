import { useState } from 'react';
import TextField from '../../components/TextField/TextField';
import PasswordField from '../../components/PasswordField/PasswordField';
import { isCpfValid, maskCpf } from '../../lib/validation';
import { login } from '../../lib/api';
import './AuthForm.scss';

interface LoginFormProps {
  onSuccess: (token: string) => void;
  onSwitch: () => void;
}

export default function LoginForm({ onSuccess, onSwitch }: LoginFormProps) {
  const [cpf, setCpf] = useState('');
  const [senha, setSenha] = useState('');
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState('');

  const canSubmit = isCpfValid(cpf) && senha.trim() !== '' && !submitting;

  const preventNav = (e: React.SyntheticEvent) => e.preventDefault();

  async function handleSubmit(e: React.SyntheticEvent) {
    e.preventDefault();
    if (!canSubmit) return;
    setSubmitting(true);
    setError('');
    try {
      const { token } = await login(cpf.replace(/\D/g, ''), senha);
      onSuccess(token);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Não foi possível entrar.');
      setSubmitting(false);
    }
  }

  return (
    <form className="auth-form" onSubmit={handleSubmit} noValidate>
      <h1 className="auth-form__title">Bem-vindo de volta</h1>
      <p className="auth-form__subtitle">
        Entre para acessar suas reservas e preferências.
      </p>

      <TextField
        label="CPF"
        value={cpf}
        onChange={(v) => setCpf(maskCpf(v))}
        placeholder="123.456.789-09"
        autoComplete="username"
        name="cpf"
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

      {error && <p className="auth-form__error">{error}</p>}

      <button type="submit" className="auth-form__submit" disabled={!canSubmit}>
        {submitting ? 'Entrando…' : 'Entrar'}
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
