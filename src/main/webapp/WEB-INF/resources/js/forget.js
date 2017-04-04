
if(getCookie("token")!=undefined) location.href = "/res";

$(function () {
    $("#form_send").validate({
        ignore: ":hidden",
        rules: {
            in_mail:{
                required: true,
                email: true
            }
        },
        messages:{
            in_mail:{
                required: "Поле не заполнено",
                email: "Email указан не верно"
            }
        },
        submitHandler: function () {
            sendMail();
        }
    });
});
function sendMail() {

    fetch("/getnewpass", {
        method: "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'email':document.getElementById('in_mail').value
        },
    })
        .then(status)
        .then(function () {
            document.getElementById("form_send").style.display = "none";
            document.getElementById("popupForMessage").style.display = "block";
            document.getElementById("popupForMessage").style.display = "block";
            console.log("hello");
        })
        .catch(function (error) {
            let errors = document.getElementById("errors");
            errors.style.display = 'block';
            errors.innerHTML = "Пользователь не найден";
        });
}
function toHome() {
    location.href = "/";
}
