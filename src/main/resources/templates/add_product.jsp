<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="style.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
        <title>Add product</title>
    </head>
    <body>
        <main>
            <div class="main center">
                <sf:form method="post" modelAttribute="productDTO" enctype="multipart/form-data">
                    <div class="main-upper">
                        <div class="info-left">
                            <sf:input path="name" cssClass="product-name product-name-input" placeholder="Name" /> <sf:errors path="name" cssClass="error" />
                            <span>Photo:</span>
                            <input type="file" name="photo">
                        </div>
                        <div class="info-right">
                            <sf:input path="price" cssClass="product-price product-price-input" placeholder="Price" /> <sf:errors path="price" cssClass="error" />
                            <button class="product-buy" disabled>Buy</button>
                            <span class="product-seller">Seller: <a href="store-${store_id}" target="_blank">${store_name}</a></span>
                        </div>
                    </div>
                    <div class="main-lower main-lower-input">
                        <div class="product-description product-description-input">
                            <span class="product-description-name">Description</span>
                            <sf:textarea path="description" rows="10" cols="70" class="product-description-text" placeholder="Product description" /> <sf:errors path="description" cssClass="error" />
                        </div>
                        <span class="product-category-text">Category:</span>
                        <sf:select path="categoryId" cssClass="product-category-select">
                            <sf:option value="1">Laptops and computers</sf:option>
                            <sf:option value="2">Smartphones, TV and electronics</sf:option>
                            <sf:option value="3">Products for gamers</sf:option>
                            <sf:option value="4">Household equipment</sf:option>
                            <sf:option value="5">Goods for the home</sf:option>
                            <sf:option value="6">Tools and auto products</sf:option>
                            <sf:option value="7">Plumbing and repair</sf:option>
                            <sf:option value="8">Goods for the garden</sf:option>
                            <sf:option value="9">Sports and hobbies</sf:option>
                            <sf:option value="10">Clothes, shoes and jewelry</sf:option>
                            <sf:option value="11">Health and Beauty</sf:option>
                            <sf:option value="12">Baby products</sf:option>
                            <sf:option value="13">Pet products</sf:option>
                            <sf:option value="14">Office, school, books</sf:option>
                            <sf:option value="15">Alcoholic drinks and products</sf:option>
                            <sf:option value="16">Household chemicals</sf:option>
                        </sf:select> <sf:errors path="categoryId" cssClass="error" />
                        <button type="submit" class="product-save">Save</button>
                    </div>
                </sf:form>
            </div>
        </main>
    </body>
</html>