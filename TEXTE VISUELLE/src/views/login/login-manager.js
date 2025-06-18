export async function login() {
    const email = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value;

    if (!email || !password) {
        alert("Veuillez remplir tous les champs.");
        return false;
    }

    try {
        const response = await fetch("http://localhost:8080/Utilisateurs/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });

        if (response.ok) {
            const data = await response.json();
            const token = data.accessToken;

            // Décodage du token
            const payload = JSON.parse(atob(token.split('.')[1]));
            console.log("Payload JWT :", payload);

            const role = payload.role;

            // Stockage
            localStorage.setItem("token", token);
            localStorage.setItem("prenomClient", data.user.prenomClient || "");
            localStorage.setItem("userRole", role);

            return true;
        } else {
            alert("Identifiants incorrects ou utilisateur non trouvé.");
            return false;
        }
    } catch (error) {
        console.error("Erreur lors de la connexion :", error);
        alert("Erreur réseau.");
        return false;
    }
}

export function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("prenomClient");
    localStorage.removeItem("userRole");
    window.location.href = "/login";
}

export function isAuthenticated() {
    return !!localStorage.getItem("token");
}

export function getUserRole() {
    return localStorage.getItem("userRole");
}

// 👁️ Gestion visibilité du mot de passe
export function initPasswordToggle() {
    const toggleBtn = document.querySelector('.toggle-password');
    const passwordInput = document.getElementById('password');

    if (toggleBtn && passwordInput) {
        toggleBtn.addEventListener('click', function () {
            const icon = this.querySelector('i');
            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                icon.textContent = '🙈';
            } else {
                passwordInput.type = 'password';
                icon.textContent = '👁️';
            }
        });
    }
}
