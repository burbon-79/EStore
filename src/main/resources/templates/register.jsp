<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="style.css">
        <title>Sign up</title>
    </head>
    <body>
        <div class="main entry">
            <span class="entry-text">Sign up</span>
            <sf:form method="post" cssClass="entry-form center" modelAttribute="sellerDTO">
                <sf:input path="email" cssClass="entry-form_details" placeholder="Email" /> <sf:errors path="email" cssClass="error" />
                <sf:password path="password" cssClass="entry-form_details" placeholder="Password" /> <sf:errors path="password" cssClass="error" />
                <span class="error">${duplicateError}</span>
                <button type="submit" class="entry-form_details">Sign up</button>
            </sf:form>
            <a href="login" class="entry-link">Log in</a>
        </div>
    </body>
</html>