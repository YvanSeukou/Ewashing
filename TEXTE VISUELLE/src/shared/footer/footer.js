import html from './footer.html?raw';
import './footer.scss';

export function renderFooter() {
    if (document.querySelector('.site-footer')) return; // Prevent duplicates

    const wrapper = document.createElement('div');
    wrapper.innerHTML = html;
    const footerElement = wrapper.firstElementChild;

    // Inject footer at the bottom of <body>
    document.body.appendChild(footerElement);

    // Cookie preference reset link
    footerElement.querySelector('#edit-cookie-preferences')?.addEventListener('click', (e) => {
        e.preventDefault();
        if (window.cookieconsent) {
            window.cookieconsent.reset();
        } else {
            alert("La bannière de consentement n'est pas chargée.");
        }
    });

    // Load cookieconsent.js if not already loaded
    if (!window.cookieconsent) {
        const script = document.createElement('script');
        script.src = "https://cdn.jsdelivr.net/npm/cookieconsent@3/build/cookieconsent.min.js";
        script.onload = initCookieConsent;
        document.head.appendChild(script);

        const style = document.createElement('link');
        style.rel = 'stylesheet';
        style.href = "https://cdn.jsdelivr.net/npm/cookieconsent@3/build/cookieconsent.min.css";
        document.head.appendChild(style);
    } else {
        initCookieConsent();
    }

    function initCookieConsent() {
        window.cookieconsent.initialise({
            palette: {
                popup: { background: "#000" },
                button: { background: "#f1d600", text: "#000" }
            },
            theme: "classic",
            position: "bottom",
            content: {
                message: "Ce site utilise des cookies pour améliorer votre expérience.",
                dismiss: "Tout accepter",
                deny: "Tout refuser",
                link: "En savoir plus",
                href: "/cookies"
            },
            type: "opt-in",
            revokable: true,
            onInitialise: function (status) {
                if (status === 'allow') enableAnalytics();
            },
            onStatusChange: function (status) {
                if (status === 'allow') enableAnalytics();
            }
        });
    }

    function enableAnalytics() {
        console.log("Consent donné - Google Analytics activé.");
        // You can dynamically load analytics scripts here
    }
}