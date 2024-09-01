<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="style.css">
        <title>Log in</title>
    </head>
    <body>
        <div class="main entry">
            <span class="entry-text">Log in</span>
            <form method="post" class="entry-form center">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <input type="text" class="entry-form_details" placeholder="Email" name="email"/>
                <input type="password" class="entry-form_details" placeholder="Password" name="password"/>
                <span class="error">${SPRING_SECURITY_LAST_EXCEPTION.message}</span>
                <button type="submit" class="entry-form_details">Log in</button>
            </form>
            <a href="register" class="entry-link">Sign up</a>
        </div>
    </body>
</html>