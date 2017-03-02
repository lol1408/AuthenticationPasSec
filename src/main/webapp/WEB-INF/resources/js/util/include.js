var path = "/resources/js/";

//Функция для динамического подключения js файлов
function include(url) {
    var script = document.createElement('script');
    script.src = url;
    document.getElementsByTagName('head')[0].appendChild(script);
}