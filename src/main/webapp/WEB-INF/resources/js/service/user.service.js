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
        .then(function (data) {
            document.getElementById("download").style.display = 'none';
            document.getElementById("form_reg").style.display = "none";
            document.getElementById("popupForMessage").style.display = "block";
            console.log("hello");
        })
        .catch(function (error) {
            const message = error.message;
            let errors = document.getElementById("errors");
            document.getElementById("download").style.display = 'none';
            if (message.localeCompare('Login already use')==0)
                errors.innerHTML = 'Логин занят';
            else if (message.localeCompare('Email already use')==0)
                errors.innerHTML = "Email занят";
            else
                errors.innerHTML = "Неизвестная ошибка";

            console.log(message);
        });
}