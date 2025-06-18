import './about.scss';

export async function renderAbout(root, handleRoute) {
    try {
        const res = await fetch('/src/views/about/about.html');
        if (!res.ok) throw new Error('Erreur de chargement de la page À propos');

        const html = await res.text();
        root.innerHTML = html;
    } catch (error) {
        root.innerHTML = `<p>Impossible de charger la page À propos.</p>`;
        console.error(error);
    }
}