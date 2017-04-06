include(path+"util/cookie.js");
$(function () {
    $("#form_auth").validate({
        submitHandler: function () {
            login();
        }
    });
});
//GET request, try authentication
function login() {
    document.getElementById("download").style.display = 'block';
    var login = document.getElementById("in_login").value;
    var password = document.getElementById("in_password").value;
    var error_message = document.getElementById("error_message");
    fetch("/login", {
        method: "GET",
        headers: {
            'Accept':'application/json',
            'Content-Type':'application/json',
            'login': login,
            'password': sha1(password)
        }
    })
        .then(status)
        .then(json)
        .then(function (data) {
            console.log(data);
            setCookie("token", data, {
                expires: 7200
            });
            location.href = "/res";
        }).then(function () {
            return false;
    })
        .catch(function (error) {
            error_message.innerHTML = "Пользователь не найден";
            console.log("Request failed", error);
            document.getElementById("download").style.display = 'none';
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
        .then(function(data) {
            deleteCookie("token");
            location.href = "/";
        })
        .catch(function (error) {
            console.log("Request failed", error);
        });
}