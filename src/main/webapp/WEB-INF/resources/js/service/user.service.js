//Добавление учетной записи
/** Нужны подключенные файлы "query.util.js" **/
function addUser(user) {
    var userJson = user.toStringJson();
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
            console.log("Request success");
            location.href = "/";
        })
        .catch(function (error) {
            alert("user already exist");
        });
}