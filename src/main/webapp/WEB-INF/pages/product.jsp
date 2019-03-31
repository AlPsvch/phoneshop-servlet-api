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
    <form method="post" action="${pageContext.servletContext.contextPath}/products/${product.id}">
        <p>
            <input name="quantity" value="${not empty param.quantity ? param.quantity : 1}" style="text-align: right">
            <button>Add to cart</button>
            <c:if test="${not empty error}">
                <br><span style="color:red">${error}</span>
            </c:if>
        </p>
    </form>

    <c:if test="${not empty recentProducts}">
        <br>
        <h3>Recently Viewed</h3>
        <table>
            <thead>
            <c:forEach var="product1" items="${recentProducts}">
                <th>
                <td align="center">
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product1.imageUrl}">
                    <br>
                    <a href="${pageContext.servletContext.contextPath}/products/${product1.id}">${product1.description}</a>
                    <br>
                    <fmt:formatNumber value="${product1.price}" type="currency"
                                      currencySymbol="${product1.currency.symbol}"/>
                </td>
                </th>
            </c:forEach>
            </thead>
        </table>
    </c:if>

</tags:master>