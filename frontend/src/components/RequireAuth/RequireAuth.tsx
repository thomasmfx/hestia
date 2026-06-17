import type { ReactNode } from 'react';
import { Navigate } from 'react-router-dom';
import { isAuthenticated } from '../../lib/session';

interface RequireAuthProps {
  children: ReactNode;
}

// Rota protegida: sem token de sessão, redireciona para o login.
export default function RequireAuth({ children }: RequireAuthProps) {
  if (!isAuthenticated()) {
    return <Navigate to="/login" replace />;
  }
  return <>{children}</>;
}
