if(getCookie("token")==undefined) {
    alert("Сессия учетной записи не активна");
    location.href = "/";
}

//JS отвечающий за resources.js
//Заполняем таблицу
function fillTable() {
    //Получаем все ресурсы пользователя
    var allResources = [];
    getAllResources(allResources).
        then(function () {
            var table = document.getElementById("res_table");
            if(table.rows.length>1){
                //Збрасываем таблицу по умолчанию
                for (var i=table.rows.length-1; i>0; i--){
                    table.deleteRow(i);
                }
            }
            allResources.forEach(function (item, i, data) {
                var row = table.insertRow(i + 1);
                var login = row.insertCell(0);
                var password = row.insertCell(1);
                var change = row.insertCell(2);
                var deleteMess = row.insertCell(3);
                console.log(item);
                login.innerHTML = item.login;
                password.innerHTML = item.password;
                change.innerHTML = "<input type='button' id='chen"+item.id+"' value='Изменить' onclick='changeById("+item.id+");'>";
                deleteMess.innerHTML = "<input type='button' id='del"+item.id+ "' value='Удалить' onclick='deleteById("+item.id+");'>";
            });
    });
}

function addRes(){
    var login = document.getElementById("res_login_field").value;
    var password = document.getElementById("res_pass_field").value;
    let resource = new Resource(login, password);
    //Отправляем на сервер и добавляем запись в таблицу
    addResource(resource).then(function () {
        var table = document.getElementById("res_table");
        var row = table.insertRow(table.rows.length);
        var login = row.insertCell(0);
        var password = row.insertCell(1);
        var change = row.insertCell(2);
        var deleteMess = row.insertCell(3);
        console.log(resource);
        login.innerHTML = resource.login;
        password.innerHTML = resource.password;
        change.innerHTML = "<input type='button' id='chen"+resource.id+"' value='Изменить' onclick='changeById("+resource.id+");'>";
        deleteMess.innerHTML = "<input type='button' id='del"+resource.id+ "' value='Удалить' onclick='deleteById("+resource.id+");'>";
    });
}