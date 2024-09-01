<header class="header">
    <h1 class="header_name"><a href="${pageContext.request.contextPath}" class="header_name-link">EStore</a></h1>
    <form method="get" action="search" class="header_search">
        <input type="text" class="header_search-input" name="name">
        <button type="submit" class="header_search-button">Search</button>
    </form>
    <div class="header_buttons">
        <c:choose>
            <c:when test="${pageContext.request.userPrincipal.name == null}">
                <a class="header_button" href="cart">
                    <img src="icons/shopping-basket.png" alt="Cart" width="35px">
                    <span>Cart</span>
                </a>
                <a class="header_button" href="login">
                    <img src="icons/user.png" alt="Login" width="35px">
                    <span>Sell</span>
                </a>
            </c:when>
            <c:otherwise>
                <a class="header_button" href="store">
                    <img src="icons/user.png" alt="Profile" width="35px">
                    <span>Profile</span>
                </a>
                <form method="post" action="logout">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <button type="submit" class="header_button">
                        <img src="icons/exit.png" alt="Exit" width="35px">
                        <span>Exit</span>
                    </button>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
</header>