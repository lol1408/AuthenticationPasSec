//Добавление учетной записи
/** Нужны подключенные файлы "query.util.js" **/
function addUser(user) {
    var userJson = user.toStringJson();
    var errors = document.getElementById("errors");
    fetch("/users/", {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: userJson
    })
        .then(status)
        .then(function () {
            location.href = "/";
        })
        .catch(function (error) {
            errors.innerHTML = "user already exist";
        });
}