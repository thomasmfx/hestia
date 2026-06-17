// Sessão do usuário logado: dados leves para exibição (usados pelo header)
// e o token JWT que autentica as chamadas à API.
// No modo mock o token é fictício; no modo real vem de POST /auth/login.

const SESSION_KEY = 'hestia.session';
const TOKEN_KEY = 'hestia.token';

export interface Session {
  nome?: string;
  email: string;
}

export function setSession(session: Session): void {
  localStorage.setItem(SESSION_KEY, JSON.stringify(session));
}

export function getSession(): Session | null {
  const raw = localStorage.getItem(SESSION_KEY);
  if (!raw) return null;
  try {
    return JSON.parse(raw) as Session;
  } catch {
    return null;
  }
}

export function clearSession(): void {
  localStorage.removeItem(SESSION_KEY);
}

export function setToken(token: string): void {
  localStorage.setItem(TOKEN_KEY, token);
}

export function getToken(): string | null {
  return localStorage.getItem(TOKEN_KEY);
}

export function clearToken(): void {
  localStorage.removeItem(TOKEN_KEY);
}

export function isAuthenticated(): boolean {
  return getToken() !== null;
}
