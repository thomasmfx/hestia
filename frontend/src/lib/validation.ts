const EMAIL_RE = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

export function isEmailValid(email: string): boolean {
  return EMAIL_RE.test(email);
}

// Formata os dígitos digitados como (XX) XXXXX-XXXX.
export function formatTelefone(value: string): string {
  const digits = value.replace(/\D/g, '').slice(0, 11);
  if (digits.length <= 2) return digits;
  if (digits.length <= 7) return `(${digits.slice(0, 2)}) ${digits.slice(2)}`;
  return `(${digits.slice(0, 2)}) ${digits.slice(2, 7)}-${digits.slice(7)}`;
}

// CPF é válido para envio quando tem 11 dígitos.
export function isCpfValid(value: string): boolean {
  return value.replace(/\D/g, '').length === 11;
}

// Máscara progressiva de CPF: 123.456.789-09.
export function maskCpf(value: string): string {
  const d = value.replace(/\D/g, '').slice(0, 11);
  if (d.length <= 3) return d;
  if (d.length <= 6) return `${d.slice(0, 3)}.${d.slice(3)}`;
  if (d.length <= 9) return `${d.slice(0, 3)}.${d.slice(3, 6)}.${d.slice(6)}`;
  return `${d.slice(0, 3)}.${d.slice(3, 6)}.${d.slice(6, 9)}-${d.slice(9)}`;
}

// Máscara progressiva de CEP: 01310-100.
export function maskCep(value: string): string {
  const d = value.replace(/\D/g, '').slice(0, 8);
  if (d.length <= 5) return d;
  return `${d.slice(0, 5)}-${d.slice(5)}`;
}

// Unidades federativas (para validar o campo Estado).
export const UFS = [
  'AC',
  'AL',
  'AP',
  'AM',
  'BA',
  'CE',
  'DF',
  'ES',
  'GO',
  'MA',
  'MT',
  'MS',
  'MG',
  'PA',
  'PB',
  'PR',
  'PE',
  'PI',
  'RJ',
  'RN',
  'RS',
  'RO',
  'RR',
  'SC',
  'SP',
  'SE',
  'TO',
];

export function isUf(value: string): boolean {
  return UFS.includes(value);
}

// ─── Senha ──────────────────────────────────────────────────────────────
// Compartilhado pelo medidor de força e pela validação do formulário.
export function getPasswordStrength(senha: string): number {
  let score = 0;
  if (senha.length >= 8) score++;
  if (/[A-Z]/.test(senha)) score++;
  if (/[0-9]/.test(senha)) score++;
  if (/[a-z]/.test(senha) || /[^A-Za-z0-9]/.test(senha)) score++;
  return score;
}

// Regra exibida no formulário: mínimo 8 caracteres, com número e maiúscula.
export function isPasswordValid(senha: string): boolean {
  return senha.length >= 8 && /[0-9]/.test(senha) && /[A-Z]/.test(senha);
}
