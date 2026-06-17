import type { CadastrarHospedeRequest, Hospede, LoginResponse } from './types';
import { getToken } from './session';

// As chamadas usam caminhos relativos; em dev o proxy do Vite encaminha
// /hospedes e /auth para o backend Spring em :8080 (sem CORS).

// Erro de autenticação (401): a tela limpa a sessão e manda para o login.
export class UnauthorizedError extends Error {
  constructor() {
    super('Sessão expirada. Entre novamente.');
    this.name = 'UnauthorizedError';
  }
}

// Extrai a mensagem de erro do backend (campo "message"), com fallback.
async function readError(res: Response, fallback: string): Promise<string> {
  try {
    const body = (await res.json()) as { message?: string };
    if (typeof body.message === 'string' && body.message.trim()) {
      return body.message;
    }
  } catch {
    // corpo vazio ou não-JSON
  }
  return fallback;
}

// Perfil do hóspede logado. GET /hospedes/me (protegido por JWT).
export async function getMe(): Promise<Hospede> {
  const res = await fetch('/hospedes/me', {
    headers: { Authorization: `Bearer ${getToken() ?? ''}` },
  });
  // 403 também: como /hospedes/me só é protegido por JWT, "proibido" aqui
  // significa token ausente/inválido → tratamos como sessão expirada.
  if (res.status === 401 || res.status === 403) throw new UnauthorizedError();
  if (!res.ok) {
    throw new Error(await readError(res, 'Não foi possível carregar seu perfil.'));
  }
  return (await res.json()) as Hospede;
}

// Login por CPF + senha. POST /auth/login -> { token, tipo }.
export async function login(cpf: string, senha: string): Promise<LoginResponse> {
  const res = await fetch('/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ cpf, senha }),
  });
  if (!res.ok) {
    throw new Error(await readError(res, 'CPF ou senha inválidos.'));
  }
  return (await res.json()) as LoginResponse;
}

// Cadastro de hóspede. POST /hospedes -> HospedeResponse (201).
export async function cadastrar(
  payload: CadastrarHospedeRequest,
): Promise<Hospede> {
  const res = await fetch('/hospedes', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
  });
  if (!res.ok) {
    // O backend não inclui "message" por padrão; damos fallbacks por status.
    let fallback = 'Não foi possível concluir o cadastro. Verifique os dados.';
    if (res.status === 409) fallback = 'Este CPF já está cadastrado.';
    else if (res.status === 422) fallback = 'É necessário aceitar os termos de uso.';
    throw new Error(await readError(res, fallback));
  }
  return (await res.json()) as Hospede;
}
