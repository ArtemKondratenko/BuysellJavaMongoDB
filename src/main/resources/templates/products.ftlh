<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BUYSELL</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 20px;
        }
        .container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            width: 100%;
            margin: 0;
        }
        h1 {
            color: #333;
        }
        h4 {
            color: #555;
        }
        input[type="text"], input[type="email"], input[type="password"], input[type="number"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
            width: 100%;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        .error-message {
            color: red;
            margin-top: 10px;
        }
        a {
            display: block;
            margin-top: 10px;
            color: #4CAF50;
            text-decoration: none;
            transition: color 0.3s;
        }
        a:hover {
            color: #45a049;
        }
        .no-products {
            color: #555;
            text-align: center;
        }
        .product {
            margin: 10px 0;
        }
        .search-form {
            margin: 20px 0;
        }
        .logout-form {
            margin-top: 10px;
        }
        .user-info {
            margin-bottom: 20px;
        }
        .user-info img {
            width: 100px; /* Задайте желаемую ширину */
            height: 100px; /* Задайте желаемую высоту */
            border-radius: 50%; /* Делаем изображение круглым */
            object-fit: cover; /* Обеспечиваем корректное масштабирование */
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>BUYSELL</h1>
        <#if user??>
            <div class="user-info">
                <div>
                    <img src="${avatarUrl}" alt="Аватарка пользователя">
                    <h3>Имя пользователя: <i>${user.name! 'Гость'}</i></h3>
                </div>
                <form action="/logout" method="post" class="logout-form">
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="submit" value="Выйти"/>
                </form>
            </div>
            <#if user.isAdmin()>
                <a href="/admin">Панель администратора</a>
            </</#if>
        <#else>
            <a href="/login">Войти</a>
        </#if>
        <hr>

        <h4>Товары со всей России</h4>
        <div class="search-form">
            <form action="/" method="get">
                Поиск по названию объявления:
                <input type="text" name="title" placeholder="Введите название" required>
                <input type="submit" value="Поиск"/>
            </form>
        </div>

        <#if products?has_content>
            <#list products as product>
                <div class="product">
                    <p><b>${product.title! 'Без названия'}</b> - ${product.price! 'Не указана'} руб. | <a href="/product/${product.id}">Подробнее...</a></p>
                </div>
            </#list>
        <#else>
            <h3 class="no-products">У пользователя пока нет товаров.</h3>
        </#if>

        <#if user??>
            <hr>
            <h3>Создать новый товар</h3>
            <form action="/product/create" method="post" enctype="multipart/form-data">
                <input type="text" name="title" placeholder="Название товара" required/>
                <input type="text" name="description" placeholder="Описание товара" required/>
                <input type="number" name="price" placeholder="Цена" required/>
                <input type="text" name="city" placeholder="Город" required/>
                <input type="file" name="files" multiple required/>
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input type="submit" value="Добавить товар"/>
            </form>
        </#if>
    </div>
</body>
</html>