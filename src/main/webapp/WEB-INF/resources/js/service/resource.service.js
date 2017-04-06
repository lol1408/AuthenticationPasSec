

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
            arr[i] = new Resource(item.url, item.password);
            arr[i].setId(item.id);
            console.log(item.url + " " + item.password)
        });
        document.getElementById("download").style.display = "none";
    }).catch(function (error) {
        console.log('Request failed', error);
        deleteCookie('token');
        document.getElementById("download").style.display = "none";
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
            alert("error, resource cannot be added");
        });
}
function deleteResource(id) {
    return fetch("/resources/"+id,{
        method: "DELETE",
        headers: {
            'token':getCookie("token")
        }
    }).then(status)
        .then(function () {
            console.log("delete is success");
        }).catch(function (error) {
            alert("delete is failed");
        });
}
function updateResource(resource) {
    var resourceJson = resource.toStringJson();
    return fetch("/resources/", {
        method: "PUT",
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
        })
        .catch(function (error) {
            alert("error, resource cannot be updated");
        });
}