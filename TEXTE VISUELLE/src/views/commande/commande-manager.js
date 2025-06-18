// Exemple de données d'articles selon le type de service
const articlesParService = {
    "Nettoyage à sec": [
        { id: "costume", name: "Costume", price: 15, icon: "🤵" },
        { id: "robe-soiree", name: "Robe de soirée", price: 25, icon: "👗" },
        { id: "manteau", name: "Manteau", price: 20, icon: "🧥" },
    ],
    "Blanchisserie": [
        { id: "linge-lit", name: "Linge de lit", price: 10, icon: "🛏️" },
        { id: "serviettes", name: "Serviettes", price: 8, icon: "🧻" },
    ],
    "Repassage": [
        { id: "chemise", name: "Chemise", price: 5, icon: "👔" },
        { id: "pantalon", name: "Pantalon", price: 6, icon: "👖" },
        { id: "robe", name: "Robe", price: 8, icon: "👗" },
        { id: "veste", name: "Veste", price: 7, icon: "🧥" },
        { id: "t-shirt", name: "T-Shirt", price: 4, icon: "👕" },
    ],
    "Détachage": [
        { id: "tache-tapis", name: "Tache sur tapis", price: 18, icon: "🧽" },
        { id: "tache-canape", name: "Tache canapé", price: 22, icon: "🛋️" },
    ],
    "Collecte et livraison": [], // ce service est inclus dans d'autres
    "Entretien des vêtements professionnels": [
        { id: "uniforme", name: "Uniforme", price: 12, icon: "👷" },
        { id: "blouse", name: "Blouse", price: 10, icon: "🥼" },
    ]
};

export function getSelectedService() {
    return localStorage.getItem("selectedService");
}

export function getArticlesForService(serviceName) {
    return articlesParService[serviceName] || [];
}
