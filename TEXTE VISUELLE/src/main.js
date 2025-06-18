import { initRouter } from "./router.js";
import { renderFooter } from "./shared/footer/footer.js";
import { renderMenu } from "./shared/header/header.js";

document.addEventListener('DOMContentLoaded', () => {
    renderMenu(); // Met à jour le menu selon token
    renderFooter(); // Pied de page
    initRouter();  // SPA routing
});

export function logout() {
    localStorage.removeItem('token');
    renderMenu(); // Mise à jour menu
    window.location.href = '/';
}
