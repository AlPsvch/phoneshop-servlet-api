<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product Description">
    <c:if test="${not empty param.message}">
        <br><span style="color:forestgreen">${param.message}</span>
    </c:if>
    <p>
        Product Description.
    </p>
    <table>
        <thead>
        <tr>
            <td>ID</td>
            <td>Image</td>
            <td>Description</td>
            <td>Stock</td>
            <td class="price">Price</td>
        </tr>
        </thead>
        <tr>
            <td>${product.id}</td>
            <td>
                <img class="product-tile"
                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            </td>
            <td>${product.description}</td>
            <td>${product.stock}</td>
            <td class="price">
                <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </td>
        </tr>
    </table>

    <c:url value="/products/${product.id}" var="thisProductPage"/>
    <form method="post" action="${thisProductPage}">
        <p>
            <input name="quantity" value="${not empty param.quantity ? param.quantity : 1}" style="text-align: right">
            <button>Add to cart</button>
            <c:if test="${not empty error}">
                <br><span style="color:red">${error}</span>
            </c:if>
        </p>
    </form>

    <tags:recent recentProducts="${recentProducts}"/>

    <br><br>
    <p>
        <c:if test="${not empty param.reviewError}">
            <span style="color: red;">${param.reviewError}</span>
        </c:if>
        <c:if test="${not empty param.reviewMessage}">
            <span style="color: forestgreen;">${param.reviewMessage}</span>
        </c:if>
        <br>
        <jsp:include page="/review"/>
    </p>

    <c:forEach var="comm" items="${submittedComments}">
        <p>Name: ${comm.name}</p>
        <p>Rating: ${comm.rating}</p>
        <p>Comment: ${comm.comment}</p>
    </c:forEach>

</tags:master>