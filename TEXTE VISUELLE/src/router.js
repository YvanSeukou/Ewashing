import { renderNotFound } from "./shared/not-found/not-found.js";
import { renderHome } from "./views/home/home.js";
import { renderLogin } from "./views/login/login.js";
import { renderRegister } from "./views/register/register.js";
import { renderAbout } from "./views/about/about.js";
import { renderCookiesPage } from "./shared/footer/cookie.js";
import { renderDashboard } from './views/dashboard/dashboard.js';
import { renderUsers } from './views/users/user.js';
import { renderServices } from './views/services/service.js';
import { renderCommande } from './views/commande/commande.js';
import { renderProfile } from './views/profile/profile.js';

import { isAuthenticated, getUserRole } from "./views/login/login-manager.js";

const routes = {
    '/': renderHome,
    '/login': renderLogin,
    '/register': renderRegister,
    '/about': renderAbout,
    '/cookies': renderCookiesPage,
    '/dashboard': renderDashboard,
    '/users': renderUsers,
    '/services': renderServices,
    '/commande': renderCommande,
    '/profile': renderProfile,
};

const protectedRoutes = {
    '/dashboard': ['ADMINISTRATEUR'],
    '/users': ['ADMINISTRATEUR'],
    '/commande': ['CLIENT'],
    '/profile': ['CLIENT'],
};

let root;

export function handleRoute(path = window.location.pathname) {
    const publicRoutes = ['/', '/login', '/register', '/about', '/cookies', '/services'];
    const userIsAuth = isAuthenticated();
    const userRole = getUserRole();

    // Redirection si utilisateur déjà connecté
    if ((path === '/login' || path === '/register') && userIsAuth) {
        const redirectPath = userRole === 'CLIENT' ? '/' : '/dashboard';
        window.history.replaceState({}, '', redirectPath);
        return handleRoute(redirectPath);
    }

    // Vérification d’accès aux routes protégées
    if (protectedRoutes[path]) {
        if (!userIsAuth) {
            window.history.replaceState({}, '', '/login');
            return handleRoute('/login');
        } else if (!protectedRoutes[path].includes(userRole)) {
            window.history.replaceState({}, '', '/');
            return handleRoute('/');
        }
    }

    const render = routes[path] || renderNotFound;
    render(root, handleRoute);
}

export function initRouter() {
    root = document.getElementById('app');
    handleRoute();

    document.body.addEventListener('click', (e) => {
        if (e.target.matches('a[href^="/"]')) {
            e.preventDefault();
            const path = e.target.getAttribute('href');
            window.history.pushState({}, '', path);
            handleRoute(path);
        }
    });

    window.addEventListener('popstate', () => handleRoute());
}
