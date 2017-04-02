//Добавление учетной записи
/** Нужны подключенные файлы "query.util.js" **/
function addUser(user) {
    var userJson = user.toStringJson();
    fetch("/users/", {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: userJson
    })
        .then(status)
        .then(function () {
            document.getElementById("form_reg").style.display = "none";
            document.getElementById("popupForMessage").style.display = "block";
            console.log("hello");
        })
        .catch(function (error) {
            let errors = document.getElementById("errors");
            errors.innerHTML = "Пользователь уже создан";
        });
}