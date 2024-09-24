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
                    <div class="content_about">
                        <span class="content_about-name">About</span>
                        <c:choose>
                            <c:when test="${pageContext.request.userPrincipal.name == store.email}">
                                <form method="post" action="changeStoreAbout" class="main edit-store">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                    <textarea rows="12" cols="80" class="edit-store-profile" name="text">${store.about}</textarea>
                                    <span class="error">${aboutError}</span>
                                    <button type="submit" class="edit-store-profile">Save</button>
                                </form>
                            </c:when>
                            <c:otherwise>  
                                <span class="content_about-text">${store.about}</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="content_reviews">
                        <span class="content_reviews-name">Reviews</span>
                        <c:if test="${pageContext.request.userPrincipal.name == null}">
                            <sf:form method="post" modelAttribute="reviewDTO" action="addStoreReview">
                                <sf:hidden path="referenceId" value = "${store.id}" />
                                <div class="content_review">
                                    <sf:input path="username" cssClass="content_reviews-user content_reviews-user-input" placeholder="Your name" /> <sf:errors path="username" cssClass="error" />
                                        <div class="content_reviews-stars">
                                            <sf:input path="stars" cssClass="content_reviews-stars-amount content_reviews-stars-amount-input" />
                                            <img src="icons/star.png" alt="Star" width="35px" class="content_reviews-stars-image">
                                        </div>
                                    <sf:errors path="stars" cssClass="error" />
                                    <sf:textarea path="text" rows="5" cssClass="content_reviews-text" placeholder="Review text" /> <sf:errors path="text" cssClass="error" />
                                    <button type="submit" class="content_reviews-input">Add review</button>
                                </div>
                            </sf:form>
                        </c:if>
                        <c:choose>
                            <c:when test="${reviews.size() == 0}">
                                <span class="content_reviews-text">No reviews available</span>
                            </c:when>
                            <c:otherwise>  
                                <c:forEach items="${reviews}" var="thisReview">
                                    <div class="content_review">
                                        <span class="content_reviews-user">${thisReview.username}</span>
                                        <div class="content_reviews-stars">
                                            <span class="content_reviews-stars-amount">${thisReview.stars}</span>
                                            <img src="icons/star.png" alt="Star" width="35px" class="content_reviews-stars-image">
                                        </div>
                                        <span class="content_reviews-text">${thisReview.text}</span>
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