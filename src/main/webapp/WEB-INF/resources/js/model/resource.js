'use strict';
//Класс Узер
class Resource{

    constructor(login, password){
        this.id = null;
        this.login = login;
        this.password = password;
    }
    setId(id){
        this.id = id;
    }
    toJson(){
        return {
            login: this.login,
            password: this.password
        }
    }
    toStringJson(){
        var json = {
            id: this.id,
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
