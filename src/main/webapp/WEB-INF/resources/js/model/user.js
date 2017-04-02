'use strict';
//Класс Узер
class User{
    constructor(login, password, mail){
        this.login = login;
        this.password = password;
        this.mail = mail;
    }

    toJson(){
        return {
            login: this.login,
            password: this.password,
            mail: this.mail
        }
    }
    toStringJson(){
        let json = this.toJson();
        return JSON.stringify(json);
    }
    getLogin(){
        return this.login;
    }
    getPassword(){
        return this.password;
    }
    getMail(){
        return this.mail;
    }
}
