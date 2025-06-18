// example: router.route('/privacy-policy', renderPrivacyPage)

function renderPrivacyPage() {
    document.getElementById('app').innerHTML = `
    <h1>Politique de confidentialité</h1>
    <p>Nous stockons uniquement les données nécessaires au fonctionnement du site.</p>
  `;
}
