// Lógica de senha compartilhada pelo medidor de força e pela validação do form.

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
