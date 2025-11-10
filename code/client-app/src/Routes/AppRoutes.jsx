import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Login from '../Pages/login';
import Home from '../Pages/Home';
import Empresas from '../Pages/Empresas';
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

				{/* fallback to home or login depending on auth handled by ProtectedRoute */}
				<Route
					path="/empresas"
					element={
						<ProtectedRoute>
							<Empresas />
						</ProtectedRoute>
					}
				/>
				<Route path="*" element={<Navigate to="/" replace />} />
			</Routes>
		</BrowserRouter>
	);
}
