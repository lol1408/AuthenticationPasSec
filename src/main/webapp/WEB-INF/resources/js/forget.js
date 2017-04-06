
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
    document.getElementById("download").style.display = 'block';
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
            //Close download div
            document.getElementById("download").style.display = 'none';
            //Close form send
            document.getElementById("form_send").style.display = "none";
            //Open popup message
            document.getElementById("popupForMessage").style.display = "block";
            console.log("hello");
        })
        .catch(function (error) {
            document.getElementById("download").style.display = 'none';
            let errors = document.getElementById("errors");
            errors.style.display = 'block';
            errors.innerHTML = "Пользователь не найден";
        });
}
function toHome() {
    location.href = "/";
}
