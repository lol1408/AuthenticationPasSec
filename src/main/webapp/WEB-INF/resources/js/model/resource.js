'use strict';
//Класс Узер
class Resource{

    constructor(url, password){
        this.id = null;
        this.url = url;
        this.password = password;
    }
    setId(id){
        this.id = id;
    }
    toJson(){
        return {
            url: this.url,
            password: this.password
        }
    }
    toStringJson(){
        var json = {
            id: this.id,
            url: this.url,
            password: this.password
        };
        return JSON.stringify(json);
    }
    getLogin(){
        return this.url;
    }
    getPassword(){
        return this.password;
    }
}
