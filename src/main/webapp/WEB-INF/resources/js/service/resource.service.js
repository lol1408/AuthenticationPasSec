

function getAllResources(arr) {
    return fetch("/resources/",{
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
            arr[i].setId(item.id);
            console.log(item.login + " " + item.password)
        });
    }).catch(function (error) {
        console.log('Request failed', error);
    });
}

function addResource(resource) {
    var resourceJson = resource.toStringJson();
    return fetch("/resources/", {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'token':getCookie("token")
        },
        body: resourceJson
    })
        .then(status)
        .then(json)
        .then(function (data) {
            console.log("Request success");
            resource.setId(data.id);
            // location.href = "/";
        })
        .catch(function (error) {
            alert("user already exist");
        });
}