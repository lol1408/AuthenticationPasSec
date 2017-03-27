
//Проверяем если аутентификация произошла то ридеректим
//на /res
if(getCookie("token")!=undefined) location.href = "/res";

$(function () {
   $("#form_reg").validate({
       ignore: ":hidden",
       rules: {
           in_login: {
               required: true,
               minlength: 6
           },
           in_password: {
               required: true,
               minlength: 6
           },
           in_password_two: {
               required: true,
               equalTo: "#in_password"
           }
       },
       messages:{
         in_login:{
             required: "Поле не заполнено",
             minlength: "Введите как миниму 6 символов"
         },
           in_password:{
                required: "Поле не заполнено",
                minlength: "Введите как миниму 6 символов"
           },
           in_password_two:{
                required: "Поле не заполнено",
                equalTo: "Пароль не совпадает с введенным ранее"
           }

       },
       submitHandler: function () {
           sendUser();
       }
   });
});

//Отправка пользователя
function sendUser() {
    var login = document.getElementById("in_login").value;
    var password = sha1(document.getElementById("in_password").value);
    let user = new User(login, password);
    addUser(user);
}
