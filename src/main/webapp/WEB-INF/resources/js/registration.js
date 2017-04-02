
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
           },
           in_mail:{
               required: true,
               email: true
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
           },
           in_mail:{
             required: "Поле не заполнено",
               email: "Email указан не верно"
           }

       },
       submitHandler: function () {
           sendUser();
       }
   });
});

//Отправка пользователя
function sendUser() {
    let login = document.getElementById("in_login").value;
    let mail = document.getElementById("in_mail").value;
    let password = sha1(document.getElementById("in_password").value);
    let user = new User(login, password, mail);
    addUser(user);
}
function toHome() {
    location.href = "/";
}