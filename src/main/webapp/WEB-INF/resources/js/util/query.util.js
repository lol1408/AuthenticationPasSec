//Проверяем статус ответа от сервера
function status(response) {
    console.log(response.status);
    if (response.status >= 200 && response.status < 300) {
        return Promise.resolve(response)
    } else {
        return Promise.reject(new Error(response.statusText))
    }
}
//Возвращаем json из response объекта
function json(response) {
    return response.json()
}
