// src/views/dashboard/orders.js

export async function renderOrders(root) {
    root.innerHTML = `<h2>Commandes</h2><p>Chargement des commandes...</p>`;

    try {
        const token = localStorage.getItem("token");
        const res = await fetch("http://localhost:8080/commandes", {
            headers: { Authorization: `Bearer ${token}` }
        });

        if (!res.ok) throw new Error("Erreur lors du chargement des commandes");

        const commandes = await res.json();
        if (commandes.length === 0) {
            root.innerHTML = `<h2>Commandes</h2><p>Aucune commande trouvée.</p>`;
            return;
        }

        const html = commandes.map(cmd => `
  <div class="order-card">
    <h3>Commande #${cmd.numeroCommande}</h3>
    <p><strong>Date commande :</strong> ${new Date(cmd.dateCommande).toLocaleString("fr-FR")}</p>
    <p><strong>Date collecte :</strong> ${new Date(cmd.dateCollecte).toLocaleDateString("fr-FR")}</p>
    <p><strong>Date livraison :</strong> ${new Date(cmd.dateLivraison).toLocaleDateString("fr-FR")}</p>
    <p><strong>Total :</strong> ${cmd.montantTotal} €</p>
    <p><strong>Statut :</strong> ${cmd.statut}</p>
    <p><strong>Adresse :</strong> ${cmd.adresseLivraison || "Non précisée"}</p>
    ${cmd.instructions ? `<p><strong>Instructions :</strong> ${cmd.instructions}</p>` : ""}
    <p><strong>Client :</strong> ${cmd.client ? cmd.client.prenomClient + ' ' + cmd.client.nomClient : "Non précisé"}</p>
  </div>
`).join("");



        root.innerHTML = `<h2>Commandes</h2>${html}`;
    } catch (e) {
        root.innerHTML = `<h2>Commandes</h2><p class="error">${e.message}</p>`;
    }
}
