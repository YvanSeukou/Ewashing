import html from './user.html?raw';
import './user.scss';

export function renderUsers(root, handleRoute) {
    root.innerHTML = '<h2>Chargement des utilisateurs...</h2>';

    const token = localStorage.getItem('token');

    fetch('http://localhost:8080/Utilisateurs/all', {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Accès interdit ou problème de récupération.");
            }
            return response.json();
        })
        .then(users => {
            root.innerHTML = html;

            const tbody = root.querySelector('#user-table-body');
            users.forEach(user => {
                const row = document.createElement('tr');
                row.dataset.id = user.idUtilisateur;
                row.innerHTML = `
                    <td data-label="Nom">${user.nomClient}</td>
                    <td data-label="Prénom">${user.prenomClient}</td>
                    <td data-label="Email">${user.email}</td>
                    <td data-label="Téléphone">${user.telephone}</td>
                    <td data-label="Rôle">${user.typeUtilisateur}</td>
                    <td data-label="Actions">
                        <button class="edit-btn">Modifier</button>
                        <button class="delete-btn">Supprimer</button>
                    </td>
                `;
                tbody.appendChild(row);
            });

            // 🎯 Suppression
            root.querySelectorAll('.delete-btn').forEach(button => {
                button.addEventListener('click', async (e) => {
                    const row = e.target.closest('tr');
                    const id = row.dataset.id;

                    if (confirm('Confirmer la suppression ?')) {
                        const response = await fetch(`http://localhost:8080/Utilisateurs/supp/${id}`, {
                            method: 'DELETE',
                            headers: {
                                'Authorization': `Bearer ${token}`
                            }
                        });

                        if (response.ok) {
                            row.remove();
                        } else {
                            alert("Erreur lors de la suppression.");
                        }
                    }
                });
            });

            // ✏️ Modification
            root.querySelectorAll('.edit-btn').forEach(button => {
                button.addEventListener('click', (e) => {
                    const row = e.target.closest('tr');
                    const id = row.dataset.id;

                    const nomClient = prompt('Nom :', row.children[0].textContent);
                    const prenomClient = prompt('Prénom :', row.children[1].textContent);
                    const email = prompt('Email :', row.children[2].textContent);
                    const telephone = prompt('Téléphone :', row.children[3].textContent);
                    const typeUtilisateur = prompt('Type (CLIENT ou ADMINISTRATEUR) :', row.children[4].textContent);

                    const updatedUser = { nomClient, prenomClient, email, telephone, typeUtilisateur };

                    fetch(`http://localhost:8080/Utilisateurs/update/${id}`, {
                        method: 'PUT',
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${token}`
                        },
                        body: JSON.stringify(updatedUser)
                    }).then(res => {
                        if (res.ok) {
                            row.children[0].textContent = nomClient;
                            row.children[1].textContent = prenomClient;
                            row.children[2].textContent = email;
                            row.children[3].textContent = telephone;
                            row.children[4].textContent = typeUtilisateur;
                        } else {
                            alert("Erreur lors de la modification.");
                        }
                    });
                });
            });
        })
        .catch(error => {
            root.innerHTML = `<p class="error">Erreur : ${error.message}</p>`;
            console.error(error);
        });
}
