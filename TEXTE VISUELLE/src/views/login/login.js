import html from "./login.html?raw";
import "./login.scss";
import { login, getUserRole, initPasswordToggle } from "./login-manager.js";
import { renderMenu } from "../../shared/header/header.js";

export function renderLogin(root) {
    root.innerHTML = html;

    // Active le bouton "œil" pour afficher/masquer le mot de passe
    initPasswordToggle();

    const form = root.querySelector("#login-form");
    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        const success = await login();

        if (success) {
            renderMenu();
            const role = getUserRole();
            console.log("Rôle récupéré :", role);

            if (role === "CLIENT") {
                window.history.pushState({}, "", "/");
            } else if (role === "ADMINISTRATEUR") {
                window.history.pushState({}, "", "/dashboard");
            } else {
                alert("Rôle inconnu : accès refusé.");
                return;
            }

            window.dispatchEvent(new PopStateEvent("popstate"));
        }
    });

    const registerLink = root.querySelector("#register-link");
    registerLink.addEventListener("click", (e) => {
        e.preventDefault();
        window.history.pushState({}, "", "/register");
        window.dispatchEvent(new PopStateEvent("popstate"));
    });
}
