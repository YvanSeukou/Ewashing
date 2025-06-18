import html from "./commande.html?raw";
import "./commande.scss";
import { getSelectedService, getArticlesForService } from "./commande-manager.js";

export function renderCommande(root) {
    root.innerHTML = html;

    const selectedService = getSelectedService();
    const articlesData = getArticlesForService(selectedService);
    let selectedArticles = {};
    let clientInfo = {};

    const articleList = root.querySelector("#article-list");
    const recap = root.querySelector("#recapitulatif");
    const steps = root.querySelectorAll(".step");
    const stepContents = root.querySelectorAll(".step-content");

    function goToStep(n) {
        steps.forEach((s, i) => s.classList.toggle("active", i === n - 1));
        stepContents.forEach((s, i) => s.classList.toggle("active", i === n - 1));
    }

    function renderArticles() {
        articleList.innerHTML = "";
        articlesData.forEach(a => {
            const el = document.createElement("div");
            el.className = "article-item";
            el.innerHTML = `
                <span>${a.icon} ${a.name} — ${a.price}€</span>
                <div class="quantity">
                  <button class="btn minus" data-id="${a.id}">-</button>
                  <input type="number" id="${a.id}" value="0" min="0"/>
                  <button class="btn plus" data-id="${a.id}">+</button>
                </div>
            `;
            articleList.appendChild(el);
        });
        root.querySelectorAll(".plus").forEach(btn => btn.onclick = () => {
            const input = root.querySelector(`#${btn.dataset.id}`); input.value++; input.dispatchEvent(new Event("change"));
        });
        root.querySelectorAll(".minus").forEach(btn => btn.onclick = () => {
            const input = root.querySelector(`#${btn.dataset.id}`); input.value = Math.max(0, input.value - 1); input.dispatchEvent(new Event("change"));
        });
    }

    function renderSummary() {
        let html = `<h3>Résumé</h3>`, total = 0;
        Object.values(selectedArticles).forEach(a => {
            const sub = a.quantity * a.price; total += sub;
            html += `<p>${a.icon} ${a.name} × ${a.quantity} — ${sub}€</p>`;
        });
        html += `
          <hr/>
          <p><strong>Total :</strong> ${total.toFixed(2)}€</p>
          <div class="client-info">
            <p><strong>Client :</strong> ${clientInfo.nom}</p>
            <p><strong>Adresse :</strong> ${clientInfo.adresse}</p>
            <p><strong>Téléphone :</strong> ${clientInfo.telephone}</p>
            <p><strong>Date collecte :</strong> ${clientInfo.dateCollecte}</p>
            ${clientInfo.instructions ? `<p><strong>Instructions :</strong> ${clientInfo.instructions}</p>` : ""}
          </div>
        `;
        recap.innerHTML = html;
    }

    function calculateTotal() {
        return Object.values(selectedArticles)
            .reduce((sum, a) => sum + a.price * a.quantity, 0)
            .toFixed(2);
    }

    // Étape 1 : sélection articles
    root.querySelector("#step-1 .next-step").onclick = () => {
        selectedArticles = {}; let ok = false;
        articlesData.forEach(a => {
            const q = +root.querySelector(`#${a.id}`).value;
            if (q > 0) { selectedArticles[a.id] = { ...a, quantity: q }; ok = true; }
        });
        if (!ok) return alert("Sélectionnez au moins un article");
        goToStep(2);
    };

    // Étape 2 : infos client
    root.querySelector("#client-form").onsubmit = e => {
        e.preventDefault();
        clientInfo = {
            nom: root.querySelector("#nom").value.trim(),
            adresse: root.querySelector("#adresse").value.trim(),
            telephone: root.querySelector("#telephone").value.trim(),
            email: root.querySelector("#email").value.trim(),
            dateCollecte: root.querySelector("#date-collecte").value,
            instructions: root.querySelector("#instructions").value.trim()
        };
        if (!clientInfo.nom || !clientInfo.adresse || !clientInfo.telephone || !clientInfo.dateCollecte) {
            return alert("Remplissez tous les champs obligatoires");
        }
        renderSummary();
        goToStep(3);
    };

    // Étape 3 : envoi
    root.querySelector("#confirm-btn").onclick = async () => {
        const dateCommande = new Date(),
            dateCollecte = new Date(clientInfo.dateCollecte),
            dateLivraison = new Date(dateCollecte);
        dateLivraison.setDate(dateCollecte.getDate() + 3);

        const commandeServices = Object.values(selectedArticles).map(a => ({
            nomArticle: a.name,
            quantite: a.quantity,
            prixUnitaire: a.price
        }));

        const payload = {
            numeroCommande: "CMD-" + Math.floor(Math.random() * 100000),
            dateCommande: dateCommande.toISOString(),
            dateCollecte: dateCollecte.toISOString(),
            dateLivraison: dateLivraison.toISOString(),
            statut: "EN_ATTENTE",
            montantTotal: calculateTotal(),
            adresseLivraison: clientInfo.adresse,
            instructions: clientInfo.instructions,
            commandeServices
        };

        try {
            const token = localStorage.getItem("token");
            const res = await fetch("http://localhost:8080/commandes/add", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: "Bearer " + token
                },
                body: JSON.stringify(payload)
            });
            if (!res.ok) throw new Error("Échec envoi");
            alert("Commande enregistrée 🎉");
            goToStep(1);
            root.querySelector("#client-form").reset();
            articleList.querySelectorAll("input[type=number]").forEach(i => i.value = 0);
            selectedArticles = {}; clientInfo = {};
        } catch (err) {
            console.error(err);
            alert("Erreur lors de l'envoi");
        }
    };

    // Retour
    root.querySelectorAll(".prev-step").forEach((b, i) => b.onclick = () => goToStep(i + 1));

    renderArticles();
    goToStep(1);
}
