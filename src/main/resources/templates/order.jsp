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
        <title>Order</title>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <main>
            <div class="main column">
                <div class="content content_section">Checkout</div>
                <sf:form method="post" modelAttribute="cartDTO" action="checkout">
                    <div class="main-upper">
                        <div class="info-left">
                            <span class="order-text">Full name:</span><sf:input path="fullName" cssClass="order-input" /> <sf:errors path="fullName" cssClass="error" />
                            <span class="order-text">Phone number:</span><sf:input path="phoneNumber" cssClass="order-input" /> <sf:errors path="phoneNumber" cssClass="error" />
                            <span class="order-text">Email:</span><sf:input path="email" cssClass="order-input" /> <sf:errors path="email" cssClass="error" />
                            <span class="order-text">Shipping:</span>
                            <sf:select path="shipping" cssClass="order-input">
                                <sf:option value="DHL">DHL</sf:option>
                                <sf:option value="FedEx">FedEx</sf:option>
                            </sf:select>
                            <span class="order-text">City:</span><sf:input path="city" cssClass="order-input" /> <sf:errors path="city" cssClass="error" />
                            <span class="order-text">Payment:</span>
                            <sf:select path="payment" cssClass="order-input">
                                <sf:option value="Upon receipt">Upon receipt</sf:option>
                                <sf:option value="By requisites">By requisites</sf:option>
                            </sf:select>
                        </div>
                        <div class="info-right">
                            <span class="error">${AmountError}</span>
                            <span class="order-text order-text-bold">Cart</span>
                            <c:choose>
                                <c:when test="${cartIsEmpty}">
                                    <div class="order-text center">Cart is empty</div>
                                    <span class="error">${emptyCartError}</span>
                                </c:when>
                                <c:otherwise> 
                                    <div id="itemsInCart" hidden>${sessionScope.cart.size()}</div>
                                    <table class="order-basket">
                                        <tr>
                                            <td><div class="order-text center">Name</div></td>
                                            <td><div class="order-text center">Price</div></td>
                                            <td><div class="order-text center">Amount</div></td>
                                            <td><div class="order-text center">Store</div></td>
                                            <td><div class="order-text center">Delete</div></td>
                                        </tr>

                                        <c:forEach items="${sessionScope.cart}" var="item" varStatus="loop">
                                            <tr class="order-basket-item">
                                                <td><div class="order-text center"><a href="product-${item.id}">${item.name}</a></div></td>
                                                <td><div id="price${loop.index}" class="order-text center">${item.price}$</div></td>
                                                <td>
                                                    <div class="order-text center">
                                                        <input id="amount${loop.index}" type="text" class="order-input" value="1" size="5" name="amount${item.id}">
                                                    </div>
                                                </td>
                                                <td><div class="order-text center"><a href="store-${item.sellerId}">${storeNames[loop.index]}</a></div></td>
                                                <td>
                                                    <form method="post" action="removeFromCart">
                                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                                        <input type="hidden" name="idToDelete" value="${loop.index}" />
                                                        <div class="order-text center"><button type="submit">x</button></div>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                    <span id="totalPrice" class="order-text order-text-bold">Total: ${totalPrice}$</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="main-lower">
                        <button class="order-submit" type="submit">Make an order</button>
                    </div>
                </sf:form>    
            </div>
        </main>
        <%@ include file="footer.jsp" %>

        <script src="cart.js" type="text/javascript"></script>
    </body>
</html>