const DELETE_METHOD = 3;
const UPDATE_METHOD = 2;
const ADD_METHOD = 1;
var mapIdToIndex = [];
var cacheResources = [];
if(getCookie("token")==undefined) {
    alert("Сессия учетной записи не активна");
    location.href = "/";
}
//JS отвечающий за resources.js
//Заполняем таблицу

function fillTable() {
    //Получаем все ресурсы пользователя
    document.getElementById("download").style.display = "block";

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
                login.innerHTML = item.url;
                password.innerHTML = item.password;
                change.className = "change";
                deleteMess.className = "delete";
                change.innerHTML = "<input class='button' type='button' id='chen"+item.id+"' value='Изменить' onclick='openPopup("+item.id+");'>";
                deleteMess.innerHTML = "<input class='button' type='button' id='del"+item.id+ "' value='Удалить' onclick='deleteById("+item.id+");'>";
                mapIdToIndex[item.id] = i+1;
                cacheResources[item.id] = item;
            });
        console.log(mapIdToIndex);
        console.log(cacheResources);
    });
}
function addRes(){
    var login = document.getElementById("res_login_field").value;
    var password = document.getElementById("res_pass_field").value;
    let resource = new Resource(login, password);
    //Отправляем на сервер и добавляем запись в таблицу
    addResource(resource).then(function () {
        refresh(ADD_METHOD, resource)
    });
}

function deleteById(id){
    deleteResource(id);
    let res = new Resource(null, null);
    res.setId(id);
    refresh(DELETE_METHOD, res);
}
//Обновление данных в таблице
function refresh(method, resource) {
    var table = document.getElementById("res_table");
    if(method==ADD_METHOD){
        var row = table.insertRow(table.rows.length);
        var login = row.insertCell(0);
        var password = row.insertCell(1);
        var change = row.insertCell(2);
        var deleteMess = row.insertCell(3);
        login.innerHTML = resource.url;
        password.innerHTML = resource.password;
        change.className = "change";
        deleteMess.className = "delete";
        change.innerHTML = "<input class='button' type='button' id='"+resource.id+"' value='Изменить' onclick='openPopup("+resource.id+");'>";
        deleteMess.innerHTML = "<input class='button' type='button' id='"+resource.id+"' value='Удалить' onclick='deleteById("+resource.id+");'>";
        mapIdToIndex[resource.id] = table.rows.length-1;
        cacheResources[resource.id] = resource;
    }else if(method==DELETE_METHOD){
        table.deleteRow(mapIdToIndex[resource.id]);
        mapIdToIndex.forEach(function (item, i, data) {
            if(data[i]>data[resource.id]) data[i]--;
        });
        console.log(mapIdToIndex);
        console.log(resource);
    }else if(method==UPDATE_METHOD){
        var indexRow = mapIdToIndex[resource.id];
        table.rows[indexRow].cells[0].innerHTML = resource.url;
        table.rows[indexRow].cells[1].innerHTML = resource.password;
    }
    console.log(mapIdToIndex);
    console.log(cacheResources);
}
function changeResources() {
    var login = document.getElementById("popup-url").value;
    var password = document.getElementById("popup-password").value;
    var id = document.getElementById("idPopupHidden").value;
    let resource = new Resource(login, password);
    resource.setId(id);
    updateResource(resource).then(function () {
        var popup = document.getElementById("popupForMessage");
        popup.style.display = "none";
        cacheResources[id] = resource;
        refresh(UPDATE_METHOD, resource);
    }).catch(function (error) {
        alert("Error, resource cannot be updated")
    });

}

/** РАБОТА С ПОПАП ФОРМОЧКОЙ, НАСЛАЖДАЙТЕСЬ **/
//Открываем попап форму
function openPopup(id) {
    var popup = document.getElementById("popupForMessage");
    popup.style.display = "block";
    fillPopup(cacheResources[id]);
}
//Заполняем поля формы текущим элементом
function fillPopup(resource){
    var textBoxForLogin = document.getElementById("popup-url");
    var textBoxForPassword = document.getElementById("popup-password");
    var idHidden = document.getElementById("idPopupHidden");
    textBoxForLogin.value = resource.url;
    textBoxForPassword.value = resource.password;
    idHidden.value = resource.id;
}
//Закрываем попап форму
function closePopup() {
    var popup = document.getElementById("popupForMessage");
    popup.style.display = "none";
    document.getElementById("popup-text").value = "";
    document.getElementById("popup-title").value = "";
}