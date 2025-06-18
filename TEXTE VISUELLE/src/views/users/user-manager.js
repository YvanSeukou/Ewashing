import axios from 'axios';

export class UserDto {
    constructor(idUtilisateur, nomClient, prenomClient, email, telephone, typeUtilisateur) {
        this.idUtilisateur = idUtilisateur;
        this.nomClient = nomClient;
        this.prenomClient = prenomClient;
        this.email = email;
        this.telephone = telephone;
        this.typeUtilisateur = typeUtilisateur;
    }
}

export class UserManager {
    constructor() {
        this.users = [];
    }

    async getAllUsers() {
        try {
            const response = await axios.get('http://localhost:8080/Utilisateurs/all');
            this.users = response.data.map(
                user => new UserDto(
                    user.idUtilisateur,
                    user.nomClient,
                    user.prenomClient,
                    user.email,
                    user.telephone,
                    user.typeUtilisateur
                )
            );
            return this.users;
        } catch (error) {
            console.error('Erreur lors de la récupération des utilisateurs:', error);
            return [];
        }
    }
}