<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="style.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
        <title>Main page</title>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <main>
            <div class="main">
                <div class="sidebar">
                    <div class="sidebar_name">Catalog</div>
                    <span class="sidebar_item"><a href="category-1">Laptops and computers</a></span>
                    <span class="sidebar_item"><a href="category-2">Smartphones, TV and electronics</a></span>
                    <span class="sidebar_item"><a href="category-3">Products for gamers</a></span>
                    <span class="sidebar_item"><a href="category-4">Household equipment</a></span>
                    <span class="sidebar_item"><a href="category-5">Goods for the home</a></span>
                    <span class="sidebar_item"><a href="category-6">Tools and auto products</a></span>
                    <span class="sidebar_item"><a href="category-7">Plumbing and repair</a></span>
                    <span class="sidebar_item"><a href="category-8">Goods for the garden</a></span>
                    <span class="sidebar_item"><a href="category-9">Sports and hobbies</a></span>
                    <span class="sidebar_item"><a href="category-10">Clothes, shoes and jewelry</a></span>
                    <span class="sidebar_item"><a href="category-11">Health and Beauty</a></span>
                    <span class="sidebar_item"><a href="category-12">Baby products</a></span>
                    <span class="sidebar_item"><a href="category-13">Pet products</a></span>
                    <span class="sidebar_item"><a href="category-14">Office, school, books</a></span>
                    <span class="sidebar_item"><a href="category-15">Alcoholic drinks and products</a></span>
                    <span class="sidebar_item"><a href="category-16">Household chemicals</a></span>
                </div>
                <div class="content">
                    <div class="content_section">You might like it</div>
                    <div class="content_list">
                        <c:forEach items="${products}" var="thisProduct">
                            <div>
                                <a href="product-${thisProduct.id}" class="content_list-item">
                                    <img src="${pageContext.request.contextPath}/product/image-${thisProduct.id}" alt="Product photo" width="240px">
                                    <span class="content_list-item-name">${thisProduct.name}</span>
                                    <span class="content_list-item-price">${thisProduct.price}$</span>
                                </a>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </main>
        <%@ include file="footer.jsp" %>
    </body>
</html>