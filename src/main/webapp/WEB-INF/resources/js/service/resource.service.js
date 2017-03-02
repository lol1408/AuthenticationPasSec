

function getAllResources() {
    var arr = [];
    fetch("/resources/",{
        method: 'GET',
        headers:{
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'token':getCookie("token")
        }
    }).
    then(status).
    then(json).
    then(function (data) {
        console.log('Request success');
        data.forEach(function (item, i, data) {
            arr[i] = new Resource(item.login, item.password);
            console.log(item.login + " " + item.password)
        });
    }).catch(function (error) {
        console.log('Request failed', error);
    });
    return arr;
}

function addResource(resource) {
    var resourceJson = resource.toStringJson();
    fetch("/resources/", {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'token':getCookie("token")
        },
        body: resourceJson
    })
        .then(status)
        .then(function () {
            console.log("Request success");
            location.href = "/";
        })
        .catch(function (error) {
            alert("user already exist");
        });
}