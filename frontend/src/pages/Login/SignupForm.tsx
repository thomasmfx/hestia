import { useState } from 'react';
import TextField from '../../components/TextField/TextField';
import PasswordField from '../../components/PasswordField/PasswordField';
import InputDate from '../../components/InputDate/InputDate';
import {
  formatTelefone,
  isCpfValid,
  isEmailValid,
  isPasswordValid,
  isUf,
  maskCep,
  maskCpf,
} from '../../lib/validation';
import { cadastrar, login } from '../../lib/api';
import type { CadastrarHospedeRequest } from '../../lib/types';
import './AuthForm.scss';

interface SignupFormProps {
  onSuccess: (token: string) => void;
  onSwitch: () => void;
}

export default function SignupForm({ onSuccess, onSwitch }: SignupFormProps) {
  const [step, setStep] = useState<1 | 2>(1);

  const [nome, setNome] = useState('');
  const [cpf, setCpf] = useState('');
  const [dtNascimento, setDtNascimento] = useState('');
  const [email, setEmail] = useState('');
  const [telefone, setTelefone] = useState('');
  const [senha, setSenha] = useState('');
  const [cep, setCep] = useState('');
  const [estado, setEstado] = useState('');
  const [logradouro, setLogradouro] = useState('');
  const [numero, setNumero] = useState('');
  const [complemento, setComplemento] = useState('');
  const [bairro, setBairro] = useState('');
  const [cidade, setCidade] = useState('');
  const [acceptedTerms, setAcceptedTerms] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState('');

  const telDigits = telefone.replace(/\D/g, '');

  // Etapa 1 — dados pessoais/conta.
  const step1Valid =
    nome.trim() !== '' &&
    isCpfValid(cpf) &&
    dtNascimento !== '' &&
    isEmailValid(email) &&
    telDigits.length >= 10 &&
    isPasswordValid(senha);

  // Etapa 2 — endereço + termos.
  const step2Valid =
    cep.replace(/\D/g, '').length === 8 &&
    isUf(estado) &&
    logradouro.trim() !== '' &&
    numero.trim() !== '' &&
    bairro.trim() !== '' &&
    cidade.trim() !== '' &&
    acceptedTerms;

  const canSubmit = !submitting && step1Valid && step2Valid;

  const preventNav = (e: React.SyntheticEvent) => e.preventDefault();

  function goBack() {
    setError('');
    setStep(1);
  }

  async function handleSubmit(e: React.SyntheticEvent) {
    e.preventDefault();

    // Na etapa 1, "Continuar" apenas avança.
    if (step === 1) {
      if (step1Valid) {
        setError('');
        setStep(2);
      }
      return;
    }

    if (!canSubmit) return;
    setSubmitting(true);
    setError('');

    const cpfDigits = cpf.replace(/\D/g, '');
    const payload: CadastrarHospedeRequest = {
      nome: nome.trim(),
      cpf: cpfDigits,
      dtNascimento,
      email: email.trim(),
      senha,
      aceitouTermos: acceptedTerms,
      telefone: { ddd: telDigits.slice(0, 2), numero: telDigits.slice(2) },
      endereco: {
        logradouro: logradouro.trim(),
        numero: numero.trim(),
        complemento: complemento.trim() || undefined,
        bairro: bairro.trim(),
        cep: cep.replace(/\D/g, ''),
        cidade: cidade.trim(),
        estado,
      },
    };

    try {
      await cadastrar(payload);
      const { token } = await login(cpfDigits, senha);
      onSuccess(token);
    } catch (err) {
      setError(
        err instanceof Error
          ? err.message
          : 'Não foi possível concluir o cadastro.',
      );
      setSubmitting(false);
    }
  }

  return (
    <form className="auth-form" onSubmit={handleSubmit} noValidate>
      <h1 className="auth-form__title">Criar sua conta</h1>

      <div className="auth-form__steps" aria-hidden="true">
        <span className="auth-form__step-bar auth-form__step-bar--active" />
        <span
          className={`auth-form__step-bar ${step >= 2 ? 'auth-form__step-bar--active' : ''}`}
        />
      </div>
      <p className="auth-form__step-label">
        Passo {step} de 2 · {step === 1 ? 'Dados pessoais' : 'Endereço'}
      </p>

      {error && <p className="auth-form__error">{error}</p>}

      <div className="auth-form__step" key={step}>
        {step === 1 ? (
          <>
            <TextField
              label="Nome completo"
              value={nome}
              onChange={setNome}
              placeholder="Marina Duarte"
              autoComplete="name"
            />

            <div className="auth-form__row">
              <TextField
                label="CPF"
                value={cpf}
                onChange={(v) => setCpf(maskCpf(v))}
                placeholder="123.456.789-09"
                autoComplete="off"
                name="cpf"
              />
              <InputDate
                label="Data de nascimento"
                value={dtNascimento}
                onChange={setDtNascimento}
                autoComplete="bday"
              />
            </div>

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

            <button
              type="submit"
              className="auth-form__submit"
              disabled={!step1Valid}
            >
              Continuar
            </button>

            <p className="auth-form__switch">
              Já tem conta?{' '}
              <button type="button" onClick={onSwitch}>
                Entrar
              </button>
            </p>
          </>
        ) : (
          <>
            <div className="auth-form__row">
              <TextField
                label="CEP"
                value={cep}
                onChange={(v) => setCep(maskCep(v))}
                placeholder="01310-100"
                autoComplete="postal-code"
              />
              <TextField
                label="Estado (UF)"
                value={estado}
                onChange={(v) =>
                  setEstado(
                    v
                      .toUpperCase()
                      .replace(/[^A-Z]/g, '')
                      .slice(0, 2),
                  )
                }
                placeholder="SP"
              />
            </div>

            <TextField
              label="Logradouro"
              value={logradouro}
              onChange={setLogradouro}
              placeholder="Av. Paulista"
              autoComplete="address-line1"
            />

            <div className="auth-form__row">
              <TextField
                label="Número"
                value={numero}
                onChange={setNumero}
                placeholder="1578"
              />
              <TextField
                label="Complemento"
                value={complemento}
                onChange={setComplemento}
                placeholder="Ap 92 (opcional)"
                autoComplete="address-line2"
              />
            </div>

            <div className="auth-form__row">
              <TextField
                label="Bairro"
                value={bairro}
                onChange={setBairro}
                placeholder="Bela Vista"
              />
              <TextField
                label="Cidade"
                value={cidade}
                onChange={setCidade}
                placeholder="São Paulo"
                autoComplete="address-level2"
              />
            </div>

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

            <div className="auth-form__actions">
              <button
                type="button"
                className="auth-form__back"
                onClick={goBack}
              >
                Voltar
              </button>
              <button
                type="submit"
                className="auth-form__submit"
                disabled={!canSubmit}
              >
                {submitting ? 'Criando…' : 'Criar minha conta'}
              </button>
            </div>
          </>
        )}
      </div>
    </form>
  );
}
