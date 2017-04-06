//Добавление учетной записи
/** Нужны подключенные файлы "query.util.js" **/
function addUser(user) {
    document.getElementById("download").style.display = 'block';
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
        .then(function (response) {
            document.getElementById("download").style.display = 'none';
            document.getElementById("form_reg").style.display = "none";
            document.getElementById("popupForMessage").style.display = "block";
            console.log("hello");
        })
        .catch(function (error) {
            document.getElementById("download").style.display = 'none';
            let errors = document.getElementById("errors");
            console.log(error.message);
            errors.innerHTML = "Пользователь уже создан";
        });
}