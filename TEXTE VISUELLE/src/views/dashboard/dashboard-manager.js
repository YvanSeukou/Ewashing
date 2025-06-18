// src/views/dashboard/dashboard-manager.js
export function getUserInfoFromToken() {
    const token = localStorage.getItem('token');
    if (!token) return null;

    try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        return {
            email: payload.sub,
            role: payload.role,
            prenomClient: localStorage.getItem('prenomClient') || ''
        };
    } catch (error) {
        console.error("Erreur décodage token:", error);
        return null;
    }
}
