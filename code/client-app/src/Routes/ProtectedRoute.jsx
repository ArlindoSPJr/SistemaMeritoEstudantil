import React from 'react';
import { Navigate } from 'react-router-dom';
import LoginService from '../Services/LoginService';

/**
 * Componente que protege rotas verificando se existe token.
 * Se não autenticado, redireciona para /login.
 */
export default function ProtectedRoute({ children }) {
  const isAuth = LoginService.isAuthenticated ? LoginService.isAuthenticated() : false;
  if (!isAuth) {
    return <Navigate to="/login" replace />;
  }
  return children;
}
