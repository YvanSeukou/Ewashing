export function renderCookiesPage() {
    document.getElementById('app').innerHTML = `
    <h1>Politique de cookies</h1>
    <p>Cette politique explique ce que sont les cookies, comment nous les utilisons...</p>
    <button onclick="window.cookieconsent.reset()">Modifier mes préférences cookies</button>
  `;
}