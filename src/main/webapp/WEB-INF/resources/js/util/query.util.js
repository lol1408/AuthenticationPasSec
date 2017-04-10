//Проверяем статус ответа от сервера
function status(response) {
    console.log(response.status);
    if (response.status >= 200 && response.status < 300) {
        return Promise.resolve(response)
    } else {
        let message;
        return json(response).then(function (data) {
           message = data.message;
           return Promise.reject(new Error(message));
        });
    }
}
//Возвращаем json из response объекта
function json(response) {
    return response.json();
}
