<%@ taglib uri="jakarta.tags.core" prefix="c" %>
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
        <title>${product.name}</title>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <main>
            <div class="main column">
                <div class="main-upper">
                    <div class="info-left">
                        <span class="product-name">${product.name}</span>
                        <img src="${pageContext.request.contextPath}/product/image-${product.id}" alt="An error occurred while loading an image" width="400px">
                    </div>
                    <div class="info-right">
                        <span class="product-price">${product.price}$</span>
                        <form method="post" class="entry-form" action="addToCart">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <input type="hidden" name="productId" value="${product.id}" />
                            <c:set var="productInList" scope="request" value="false"/>
                            <c:forEach items="${sessionScope.cart}" var="thisProduct">
                                <c:if test="${thisProduct.id == product.id}">
                                    <c:set var="productInList" scope="request" value="true"/>
                                </c:if>
                            </c:forEach>
                            <c:choose>
                                <c:when test="${productInList}">
                                    <button class="product-buy" disabled>In the cart</button>
                                </c:when>
                                <c:otherwise>  
                                    <button type="submit" class="product-buy">Buy</button>
                                </c:otherwise>
                            </c:choose>
                        </form>
                        <span class="product-seller">Seller: <a href="store-${product.sellerId}" target="_blank">${store_name}</a></span>
                    </div>
                </div>
                <div class="main-lower">
                    <div class="product-description">
                        <span class="product-description-name">Description</span>
                        <span class="product-description-text">${product.description}</span>
                    </div>
                    <div class="product-reviews">
                        <span class="product-reviews-name">Reviews</span>
                        <c:if test="${pageContext.request.userPrincipal.name == null}">
                            <sf:form method="post" modelAttribute="reviewDTO" action="addProductReview">
                                <sf:hidden path="referenceId" value = "${product.id}" />
                                <div class="product-reviews-item">
                                    <sf:input path="username" cssClass="product-reviews-user product-reviews-user-input" placeholder="Your name" /> <sf:errors path="username" cssClass="error" />
                                    <div class="product-reviews-stars">
                                        <sf:input path="stars" cssClass="product-reviews-stars-amount product-reviews-stars-amount-input" />
                                        <img src="icons/star.png" alt="Star" width="35px" class="product-reviews-stars-image">
                                    </div>
                                    <sf:errors path="stars" cssClass="error" />
                                    <sf:textarea path="text" rows="5" cssClass="product-reviews-text product-reviews-text-input" placeholder="Review text" /> <sf:errors path="text" cssClass="error" />
                                    <button type="submit" class="product-reviews-input">Add review</button>
                                </div>
                            </sf:form>
                        </c:if>
                        <c:choose>
                            <c:when test="${reviews.size() == 0}">
                                <span class="product-reviews-text">No reviews available</span>
                            </c:when>
                            <c:otherwise>  
                                <c:forEach items="${reviews}" var="thisReview">
                                    <div class="product-reviews-item">
                                        <span class="product-reviews-user">${thisReview.username}</span>
                                        <div class="product-reviews-stars">
                                            <span class="product-reviews-stars-amount">${thisReview.stars}</span>
                                            <img src="icons/star.png" alt="Star" width="35px" class="product-reviews-stars-image">
                                        </div>
                                        <span class="product-reviews-text">${thisReview.text}</span>
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