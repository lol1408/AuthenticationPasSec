//JS отвечающий за index.html
//Проверяем если аутентификация произошла то ридеректим
//на /res
if(getCookie("token")!=undefined) location.href = "/res";

