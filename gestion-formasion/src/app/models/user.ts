import { Personne } from "./personne";

export class User {
    username: string;
    password: string;

    roles: string[];

    personne:Personne;

    constructor(username: string, password: string,roles:string[]) {
        this.username = username;
        this.password = password;
        this.roles=roles;
    }
}