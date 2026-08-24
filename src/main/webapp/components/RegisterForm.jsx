import React, { useState } from 'react';
import './RegisterForm.css';

/**
 * Componente: Formulario de Registro
 * Descripción: Permite a los usuarios ingresar sus datos para registrarse en BonuApp.
 * Basado en la Historia de Usuario: HU-01 (Registro de Usuario)
 */
export const RegisterForm = () => {
    // Estado local para gestionar los campos del formulario
    const [formData, setFormData] = useState({
        nombre: '',
        email: '',
        password: ''
    });

    // Estado para capturar y mostrar mensajes de confirmación o error
    const [mensaje, setMensaje] = useState('');
    const [cargando, setCargando] = useState(false);

    // Manejador de cambios en los inputs
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    // Manejador del envío del formulario hacia el Servlet
    const handleSubmit = async (e) => {
        e.preventDefault();
        setMensaje('');
        setCargando(true);

        // Validación básica
        if (!formData.nombre || !formData.email || !formData.password) {
            setMensaje('Todos los campos son obligatorios.');
            setCargando(false);
            return;
        }

        try {
            // Petición HTTP POST enviando el objeto JSON a UsuarioServlet
            const response = await fetch('http://localhost:8080/BonuApp/UsuarioServlet', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });

            const data = await response.json();

            if (response.ok) {
                setMensaje('¡Usuario registrado con éxito!');
                setFormData({ nombre: '', email: '', password: '' });
            } else {
                setMensaje(data.mensaje || 'Error al registrar usuario.');
            }
        } catch (error) {
            setMensaje('Error de conexión con el servidor backend.');
        } finally {
            setCargando(false);
        }
    };

    return (
        <div className="register-container">
            <h2>Registro de Usuario</h2>

            {mensaje && <p className="mensaje-alerta">{mensaje}</p>}

            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="nombre">Nombre Completo:</label>
                    <input
                        type="text"
                        id="nombre"
                        name="nombre"
                        value={formData.nombre}
                        onChange={handleChange}
                        placeholder="Ingresa tu nombre"
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="email">Correo Electrónico:</label>
                    <input
                        type="email"
                        id="email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                        placeholder="ejemplo@correo.com"
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="password">Contraseña:</label>
                    <input
                        type="password"
                        id="password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                        placeholder="Mínimo 6 caracteres"
                    />
                </div>

                <button type="submit" disabled={cargando}>
                    {cargando ? 'Guardando...' : 'Registrarse'}
                </button>
            </form>
        </div>
    );
};