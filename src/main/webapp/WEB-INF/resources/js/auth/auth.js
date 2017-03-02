include(path+"util/cookie.js");

//GET запрос для попытки аутентификации
function login() {
    var login = document.getElementById("in_login");
    var password = document.getElementById("in_password");
    let user = new User(login.value, sha1(password.value));

    fetch("/login", {
        method: "GET",
        headers: {
            'Accept':'application/json',
            'Content-Type':'application/json',
            'login':user.getLogin(),
            'password':user.getPassword()
        }
    })
        .then(status)
        .then(json)
        .then(function (data) {
            console.log("Request success");
            console.log(data);
            setCookie("token", data, {
                expires: 7200
            });
            location.href = "/res"
        })
        .catch(function (error) {
            console.log("Request failed", error);
        });
}

function logout() {
    fetch("/logout", {
        method: "GET",
        headers: {
            'Accept':'application/json',
            'Content-Type':'application/json',
            'token':getCookie("token")
        }
    })
        .then(status)
        .then(json)
        .then(function (data) {
            deleteCookie("token");
            location.href = "/"
        })
        .catch(function (error) {
            console.log("Request failed", error);
        });
}