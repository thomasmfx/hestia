// Sessão simulada (somente frontend, sem backend).
// Representa o "usuário logado" após cadastro/login.

const SESSION_KEY = 'hestia.session';

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
