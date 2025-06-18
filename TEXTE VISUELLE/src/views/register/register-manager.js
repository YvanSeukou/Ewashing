export async function register(userData) {
    try {
        const response = await fetch("http://localhost:8080/Utilisateurs/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(userData)
        });

        if (!response.ok) {
            let errorMessage = "Erreur inconnue";

            try {
                const errorData = await response.json(); // attente d'un objet { message: "..." }
                errorMessage = errorData.message || errorMessage;
            } catch (e) {
                console.error("Erreur lors du parsing de l'erreur JSON", e);
            }

            // Affichage dans l'élément HTML d'erreur
            const errorElement = document.getElementById("register-error");
            if (errorElement) {
                errorElement.textContent = "Échec de l'inscription : " + errorMessage;
            } else {
                alert("Échec de l'inscription : " + errorMessage);
            }

            return false;
        }

        // Efface l'erreur si tout se passe bien
        const errorElement = document.getElementById("register-error");
        if (errorElement) errorElement.textContent = "";

        alert("Inscription réussie ! Connectez-vous.");
        window.location.href = "/login";
        return true;

    } catch (error) {
        console.error("Erreur lors de l'inscription :", error);
        const errorElement = document.getElementById("register-error");
        if (errorElement) {
            errorElement.textContent = "Erreur réseau ou serveur";
        } else {
            alert("Erreur réseau ou serveur");
        }
        return false;
    }
}
