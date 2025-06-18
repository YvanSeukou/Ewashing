import html from "./register.html?raw";
import "./register.scss";
import { register } from "./register-manager.js";

export function renderRegister(root) {
    root.innerHTML = html;

    const form = document.getElementById("register-form");
    const errorElement = document.getElementById("register-error");

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const password1 = document.getElementById("user-password1").value.trim();
        const password2 = document.getElementById("user-password2").value.trim();

        // Réinitialise le message d'erreur
        errorElement.textContent = "";

        // Vérifie que les mots de passe correspondent
        if (password1 !== password2) {
            errorElement.textContent = "Les mots de passe ne correspondent pas.";
            return;
        }

        const userData = {
            nomClient: document.getElementById("user-nom").value.trim(),
            prenomClient: document.getElementById("user-prenom").value.trim(),
            email: document.getElementById("user-email").value.trim(),
            telephone: document.getElementById("user-telephone").value.trim(),
            motDePasse: password1,
            typeUtilisateur: "CLIENT",
        };

        const success = await register(userData);
        if (success) {
            window.history.pushState({}, "", "/login");
            window.dispatchEvent(new PopStateEvent("popstate"));
        }
    });
}
