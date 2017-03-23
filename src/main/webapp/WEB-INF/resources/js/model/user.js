'use strict';
//Класс Узер
class User{

    constructor(login, password){
        this.login = login;
        this.password = password;
    }

    toJson(){
        return {
            login: this.login,
            password: this.password
        }
    }
    toStringJson(){
        var json = {
            login: this.login,
            password: this.password
        };
        return JSON.stringify(json);
    }
    getLogin(){
        return this.login;
    }
    getPassword(){
        return this.password;
    }
}
