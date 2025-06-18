// src/shared/header/header.js
import { logout, isAuthenticated, getUserRole } from '../../views/login/login-manager.js';
import './header.scss';

export function renderMenu() {
    const menuContainer = document.getElementById('menu');
    if (!menuContainer) return;

    const prenom = localStorage.getItem('prenomClient') || '';
    const role = getUserRole();

    // Ne pas afficher de menu pour les administrateurs
    if (role === 'ADMINISTRATEUR') {
        menuContainer.innerHTML = '';
        return;
    }

    if (isAuthenticated()) {
        menuContainer.innerHTML = `
            <nav class="menu">
                <div class="navbar-brand">
                    <a href="/" class="logo">E - pressing</a>
                    <button class="burger" id="burger-toggle" aria-label="Menu">
                        <span></span><span></span><span></span>
                    </button>
                </div>
                <ul class="navbar-menu" id="navbar-menu">
                    <li><a href="/">Accueil</a></li>
                    <li><a href="/about">À propos</a></li>
                    <li><a href="/profile">Profil</a></li>
                    <li><span class="welcome-msg"><span class="greeting">Bonjour</span> <span class="prenom">${prenom}</span></span></li>
                    <li><button id="logout-button">Déconnexion</button></li>
                </ul>
            </nav>
        `;

        document.getElementById('logout-button').addEventListener('click', e => {
            e.preventDefault();
            logout();
            renderMenu();
        });

    } else {
        menuContainer.innerHTML = `
            <nav class="menu">
                <div class="navbar-brand">
                    <a href="/" class="logo">E - pressing</a>
                    <button class="burger" id="burger-toggle" aria-label="Menu">
                        <span></span><span></span><span></span>
                    </button>
                </div>
                <ul class="navbar-menu" id="navbar-menu">
                    <li><a href="/">Accueil</a></li>
                    <li><a href="/about">À propos</a></li>
                    <li><a href="/login">Connexion</a></li>
                    <li><a href="/register">Inscription</a></li>
                </ul>
            </nav>
        `;
    }

    // Menu responsive mobile
    const burger = document.getElementById('burger-toggle');
    const menu = document.getElementById('navbar-menu');
    if (burger && menu) {
        burger.addEventListener('click', () => {
            burger.classList.toggle('active');
            menu.classList.toggle('active');
        });
    }
}
