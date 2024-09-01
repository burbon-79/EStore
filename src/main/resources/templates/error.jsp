<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="style.css">
        <title>Error</title>
    </head>
    <body>
        <div class="main column center httpError">
            <span class="httpError-text">An error occurred while processing the request</span>
            <a class="httpError-link" href="${pageContext.request.contextPath}">Home</a>
        </div>
    </body>
</html>