import html from "./profile.html?raw";
import "./profile.scss";

export function renderProfile(root) {
    root.innerHTML = html;
    const token = localStorage.getItem("token");

    // 1️⃣ Récupérer les infos utilisateur
    fetch("http://localhost:8080/Utilisateurs/me", {
        headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
        }
    })
        .then(res => {
            if (!res.ok) throw new Error("Échec de la récupération du profil utilisateur.");
            return res.json();
        })
        .then(user => {
            document.getElementById("user-nom").textContent = user.nomClient;
            document.getElementById("user-email").textContent = user.email;
            document.getElementById("user-telephone").textContent = user.telephone;
            // 2️⃣ Charger les commandes juste après
            fetchCommandes(token);
        })
        .catch(err => {
            console.error("Erreur profil:", err);
            document.getElementById("profile-error").textContent = err.message;
        });
}

function fetchCommandes(token) {
    fetch("http://localhost:8080/commandes/mes-commandes", {
        headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
        }
    })
        .then(res => {
            if (!res.ok) throw new Error("Impossible de récupérer les commandes.");
            return res.json();
        })
        .then(commandes => {
            const orderList = document.getElementById("order-list");
            orderList.innerHTML = "";

            if (!Array.isArray(commandes) || commandes.length === 0) {
                orderList.innerHTML = "<p>Aucune commande trouvée.</p>";
                return;
            }

            commandes.forEach(cmd => {
                const card = document.createElement("div");
                card.className = "order-card";

                // Articles
                let articlesHtml = "";
                if (Array.isArray(cmd.commandeServices) && cmd.commandeServices.length > 0) {
                    articlesHtml = `
                    <div class="order-articles">
                        <ul>
                            ${cmd.commandeServices.map(s => `
                                <li>${s.nomArticle} × ${s.quantite} — ${s.prixUnitaire}€</li>
                            `).join("")}
                        </ul>
                    </div>
                `;
                } else {
                    articlesHtml = `<p class="no-articles"><em>Aucun article spécifié</em></p>`;
                }

                // Adresse et instructions directement depuis cmd
                const adresse = cmd.adresseLivraison || "Non précisée";
                const instructions = cmd.instructions
                    ? `<p class="instructions"><strong>Instructions :</strong> ${cmd.instructions}</p>`
                    : "";

                card.innerHTML = `
                <div class="order-header">
                    <div class="order-summary">
                        <h3>Commande #${cmd.numeroCommande}</h3>
                        <p class="order-date"><strong>Date :</strong> ${formatDate(cmd.dateCommande)}</p>
                        <p class="order-total"><strong>Total :</strong> ${cmd.montantTotal} €</p>
                        <p class="order-status ${cmd.statut.toLowerCase()}"><strong>Statut :</strong> ${cmd.statut}</p>
                    </div>
                    <button class="details-btn">Voir détails <span class="arrow">↓</span></button>
                </div>
                <div class="order-details hidden">
                    ${articlesHtml}
                    <div class="delivery-info">
                        <p><strong>Adresse de livraison :</strong> ${adresse}</p>
                        ${instructions}
                    </div>
                </div>
            `;

                orderList.appendChild(card);
            });

            // Ajout des gestionnaires d'événements pour les boutons
            document.querySelectorAll('.details-btn').forEach(btn => {
                btn.addEventListener('click', function () {
                    const details = this.closest('.order-card').querySelector('.order-details');
                    const arrow = this.querySelector('.arrow');

                    details.classList.toggle('hidden');
                    arrow.textContent = details.classList.contains('hidden') ? '↓' : '↑';
                    this.textContent = details.classList.contains('hidden')
                        ? 'Voir détails '
                        : 'Masquer détails ';
                    this.appendChild(arrow);
                });
            });
        })
        .catch(err => {
            console.error("Erreur commandes:", err);
            document.getElementById("order-list").innerHTML = `<p class="error">${err.message}</p>`;
        });
}

function formatDate(timestamp) {
    if (!timestamp) return "Non précisée";
    const d = new Date(timestamp);
    return d.toLocaleDateString("fr-FR", {
        day: "2-digit",
        month: "long",
        year: "numeric",
        hour: "2-digit",
        minute: "2-digit"
    });
}