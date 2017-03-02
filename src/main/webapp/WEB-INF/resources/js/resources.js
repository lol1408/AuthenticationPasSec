//JS отвечающий за resources.js
//Заполняем таблицу
function fillTable() {
    //Получаем все ресурсы пользователя
    var allResources = getAllResources();
    console.log(allResources);
    var table = document.getElementById("res_table");
    if(table.rows.length>1){
        //Збрасываем таблицу по умолчанию
        for (var i=table.rows.length-1; i>0; i--){
            table.deleteRow(i);
        }
    }
    allResources.forEach(function (item, i, data) {
        var row = table.insertRow(i+1);
        var login = row.insertCell(0);
        var password = row.insertCell(1);
        console.log(item);
        login.innerHTML = item.login;
        password.innerHTML = item.password;
    });
}

function addRes(){
    var login = document.getElementById("res_login_field").value;
    var password = document.getElementById("res_pass_field").value;
    let resource = new Resource(login, password);
    addResource(resource);
}
