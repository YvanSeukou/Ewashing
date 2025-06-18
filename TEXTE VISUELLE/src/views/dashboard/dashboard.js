// src/views/dashboard/dashboard.js

import html from './dashboard.html?raw';
import './dashboard.scss';
import { getUserInfoFromToken } from './dashboard-manager.js';
import { logout } from '../login/login-manager.js';
import { renderUsers } from '../users/user.js';
import { renderOrders } from './orders.js'; // ← ajout de l'import

export function renderDashboard(root, handleRoute) {
    const userInfo = getUserInfoFromToken();

    if (!userInfo) {
        root.innerHTML = '<p>Non autorisé. Veuillez vous connecter.</p>';
        setTimeout(() => window.location.href = '/login', 2000);
        return;
    }

    root.innerHTML = html;

    // 🎯 Header dynamique
    const header = document.createElement('header');
    header.className = 'dashboard-header';
    header.innerHTML = `
        <div class="header-content">
            <span>Connecté en tant que <strong>${userInfo.prenomClient}</strong> (${userInfo.role})</span>
            <button id="logout-btn">Déconnexion</button>
        </div>
        <hr>
    `;
    root.prepend(header);

    document.getElementById('logout-btn').addEventListener('click', () => logout());

    const dashboardMessage = root.querySelector('#dashboard-message');
    const subview = root.querySelector('#dashboard-subview');
    dashboardMessage.textContent = `Bienvenue ${userInfo.prenomClient} (${userInfo.role})`;

    // 🎯 Boutons sidebar
    const userBtn = root.querySelector('#load-users-btn');
    const ordersBtn = root.querySelector('#load-orders-btn');
    const servicesBtn = root.querySelector('#load-services-btn');
    const reviewsBtn = root.querySelector('#load-reviews-btn');

    if (userInfo.role === 'ADMINISTRATEUR') {
        userBtn.addEventListener('click', () => {
            setActive(userBtn);
            renderUsers(subview, handleRoute);
        });

        ordersBtn.addEventListener('click', () => {
            setActive(ordersBtn);
            renderOrders(subview); // ← affichage complet des commandes
        });

        servicesBtn.addEventListener('click', () => {
            setActive(servicesBtn);
            subview.innerHTML = '<h2>Services</h2><p>À venir…</p>';
        });

        reviewsBtn.addEventListener('click', () => {
            setActive(reviewsBtn);
            subview.innerHTML = '<h2>Avis</h2><p>À venir…</p>';
        });
    } else {
        [userBtn, ordersBtn, servicesBtn, reviewsBtn].forEach(btn => btn.style.display = 'none');
    }

    function setActive(activeBtn) {
        [userBtn, ordersBtn, servicesBtn, reviewsBtn].forEach(btn => btn.classList.remove('active'));
        activeBtn.classList.add('active');
    }
}
