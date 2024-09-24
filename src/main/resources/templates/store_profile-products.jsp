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
        <title>${store.storeName}</title>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <main>
            <div class="main column">
                <div class="store_profile">
                    <img src="${pageContext.request.contextPath}/store/image-${store.id}" alt="An error occurred while loading an image" width="200px" class="store_profile-image">
                    <c:choose>
                        <c:when test="${pageContext.request.userPrincipal.name == store.email}">
                            <form method="post" action="changeStoreNameAndPhoto" enctype="multipart/form-data" class="column center">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <input type="hidden" name="page" value="about" />
                                <span>Change the store photo:</span><input type="file" name="photo">
                                <input type="text" class="store_profile-name" value="${store.storeName}" name="name" /> <span class="error">${nameError}</span>
                                <button class="edit-store-profile">Save</button>
                            </form>
                        </c:when>
                        <c:otherwise>  
                            <span class="store_profile-name">${store.storeName}</span>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="store_buttons">
                    <a href="store-${store.id}?page=about" class="store_buttons-button">About</a>
                    <a href="store-${store.id}?page=products" class="store_buttons-button">Products</a>
                </div>
                <div class="content">
                    <c:if test="${pageContext.request.userPrincipal.name == store.email}">
                        <a href="addProduct" class="content_add-new">Add new</a>
                    </c:if>
                    <div class="content_list">
                        <c:choose>
                            <c:when test="${products.size() == 0}">
                                <span class="content_list-text">No products available</span>
                            </c:when>
                            <c:otherwise>  
                                <c:forEach items="${products}" var="thisProduct">
                                    <div>
                                        <a href="product-${thisProduct.id}" class="content_list-item">
                                            <img src="${pageContext.request.contextPath}/product/image-${thisProduct.id}" alt="Product photo" width="240px">
                                            <span class="content_list-item-name">${thisProduct.name}</span>
                                            <span class="content_list-item-price">${thisProduct.price}$</span>
                                        </a>
                                    </div>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </main>
        <%@ include file="footer.jsp" %>
    </body>
</html>