import html from './home.html?raw';
import './home.scss';

export function renderHome(root, handleRoute) {
    root.innerHTML = html;

    // Ajouter écoute sur le bouton "Commander maintenant"
    const btnCommander = root.querySelector('#btnCommander');
    if (btnCommander) {
        btnCommander.addEventListener('click', () => {
            // On utilise la fonction handleRoute pour changer la route sans recharger
            handleRoute('/services');
            window.history.pushState({}, '', '/services');
        });
    }
}
