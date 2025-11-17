import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Login from '../Pages/login';
import Home from '../Pages/Home';
import Empresas from '../Pages/Empresas';
import Professor from '../Pages/Professor';
import Instituicao from '../Pages/Instituicao';
import Vantagens from '../Pages/Vantagens';
import Transacoes from '../Pages/Transacoes';
import Alunos from '../Pages/Alunos';
import ProtectedRoute from './ProtectedRoute';

export default function AppRoutes() {
	return (
		<BrowserRouter>
			<Routes>
				<Route path="/login" element={<Login />} />
				<Route
					path="/"
					element={
						<ProtectedRoute>
							<Home />
						</ProtectedRoute>
					}
				/>
				<Route path="/alunos" element={<Alunos />} />

				<Route
					path="/professores"
					element={
						<ProtectedRoute>
							<Professor />
						</ProtectedRoute>
					}
				/>

				<Route
					path="/instituicoes"
					element={
						<ProtectedRoute>
							<Instituicao />
						</ProtectedRoute>
					}
				/>

				{/* fallback to home or login depending on auth handled by ProtectedRoute */}
				<Route
					path="/empresas"
					element={
						<ProtectedRoute>
							<Empresas />
						</ProtectedRoute>
					}
				/>
				<Route
					path="/vantagens"
					element={
						<ProtectedRoute>
							<Vantagens />
						</ProtectedRoute>
					}
				/>
				<Route
					path="/transacoes"
					element={
						<ProtectedRoute>
							<Transacoes />
						</ProtectedRoute>
					}
				/>
				<Route path="*" element={<Navigate to="/" replace />} />
			</Routes>
		</BrowserRouter>
	);
}
