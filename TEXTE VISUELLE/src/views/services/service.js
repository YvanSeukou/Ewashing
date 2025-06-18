import html from "./service.html?raw";
import { services } from "./service-manager.js";
import "./service.scss";

export function renderServices(root) {
    root.innerHTML = html;

    const container = root.querySelector("#servicesContainer");

    services.forEach(service => {
        const card = document.createElement("div");
        card.className = "service-card";
        card.innerHTML = `
            <div class="service-image" style="background-image: url('${service.image}')"></div>
            <div class="service-content">
                <h3 class="service-title">${service.title}</h3>
                <p class="service-description">${service.description}</p>
                <button class="service-button" data-service="${service.title}">Commander</button>
            </div>
        `;
        container.appendChild(card);
    });

    // Écoute des clics sur les boutons "Commander"
    container.querySelectorAll(".service-button").forEach(button => {
        button.addEventListener("click", () => {
            const service = button.getAttribute("data-service");

            // Enregistre temporairement dans localStorage ou utilise une query string
            localStorage.setItem("selectedService", service);  // ← Option simple

            // Redirection vers la page commande
            window.history.pushState({}, '', '/commande');
            window.dispatchEvent(new PopStateEvent('popstate')); // ← active le router
        });
    });
}
