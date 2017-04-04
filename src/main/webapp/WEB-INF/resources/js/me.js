function onload() {
    fetch("/users/", {
        method: "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json; charset=utf-8',
            'token': getCookie('token')
        }
    })
        .then(status)
        .then(json)
        .then(function (data) {
            let loginHtml = document.getElementById('lab_login');
            let emailHtml = document.getElementById('lab_email');
            let passwordHidden = document.getElementById('hid_pass');
            loginHtml.innerHTML = data.login;
            emailHtml.innerHTML = data.mail;
            passwordHidden.value = data.password;
        })
        .catch(function (error) {
            let errors = document.getElementById("errors");
            errors.innerHTML = "Пользователь уже создан";
        });
}
function open_popup() {
    document.getElementById('popupForMessage').style.display = 'block';
}
function closePopup() {
    var popup = document.getElementById("popupForMessage");
    document.getElementById('old_div').style.display = 'block';
    document.getElementById('new_div').style.display = 'none';
    popup.style.display = "none";
    document.getElementById("in_oldpass").value = "";
    document.getElementById("in_newpass_first").value = "";
    document.getElementById("in_newpass_second").value = "";
    document.getElementById('popup_content').style.height = '150px'
}

function checkPassword(){
    let passwordHidden = document.getElementById('hid_pass').value;
    let old_password = document.getElementById('in_oldpass').value;
    console.log(passwordHidden);
    console.log(sha1(old_password));
    console.log(sha1(old_password).localeCompare(passwordHidden));
    if(sha1(old_password).localeCompare(passwordHidden)==0){
        document.getElementById('old_div').style.display = 'none';
        document.getElementById('new_div').style.display = 'block';
        document.getElementById('error').innerHTML = '';
        document.getElementById('popup_content').style.height = '220px'
    }else {
        document.getElementById('error').innerHTML = 'Пароль не верен';
    }
}
$(function () {
    $("#form").validate({
        ignore: ":hidden",
        rules: {
            in_newpass_first: {
                required: true,
                minlength: 6
            },
            in_newpass_second: {
                required: true,
                equalTo: "#in_newpass_first"
            }
        },
        messages:{
            in_newpass_first:{
                required: "Поле не заполнено",
                minlength: "Введите как миниму 6 символов"
            },
            in_newpass_second:{
                required: "Поле не заполнено",
                equalTo: "Вы повторили не верный пароль"
            }
        },
        submitHandler: function () {
            updatePassword();
        }
    });
});

function updatePassword(){
    fetch("/users/password", {
        method: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'token': getCookie('token'),
            'oldpassword' : document.getElementById('hid_pass').value
        },
        body: sha1(document.getElementById("in_newpass_first").value)
    })
        .then(status)
        .then(json)
        .then(function (data) {
            console.log(data);
            document.getElementById('hid_pass').value = data.password;
            console.log('password');
            closePopup();
        })
        .catch(function (error) {
            console.log('error ' + error)
        });
}