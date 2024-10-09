<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BUYSELL - Редактирование пользователя</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 20px;
        }
        h1 {
            color: #333;
        }
        h3 {
            color: #555;
        }
        .container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            max-width: 400px;
            margin: auto;
        }
        label {
            display: block;
            margin: 10px 0;
        }
        input[type="checkbox"] {
            margin-right: 10px;
        }
        button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
            width: 100%;
        }
        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <h1>BUYSELL</h1>
    <hr>
    <div class="container">
        <h3>Редактирование пользователя <i>${user.name! 'неизвестен'}</i></h3>

        <form action="/admin/user/edit" method="post">
            <#if roles?has_content>
                <#list roles as role>
                    <div>
                        <label>
                            <input type="checkbox" name="roles" value="${role}" ${user.roles?seq_contains(role)?string("checked", "")} aria-label="Роль ${role}">
                            ${role}
                        </label>
                    </div>
                </#list>
            <#else>
                <p>Нет доступных ролей для редактирования.</p>
            </#if>
            <input type="hidden" value="${user.id! '0'}" name="userId">
            <input type="hidden" value="${_csrf.token}" name="_csrf">
            <button type="submit">Сохранить</button>
        </form>
    </div>
</body>
</html>