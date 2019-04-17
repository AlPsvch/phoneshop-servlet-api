<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>

<c:url var="reviewUrl" value="/review"/>
<form method="post" action="${reviewUrl}">
    <p>
    <p>
        <label for="commentatorName">Name:</label>
        <input id="commentatorName" name="commentatorName" value="${param.commentatorName}">
    </p>
    <p>
        <label for="rating">Rating:</label>
        <input id="rating" name="rating" value="${param.rating}">
    </p>
    <p>
        <label for="comment">Comment:</label>
        <input id="comment" name="comment" value="${param.comment}">

        <input type="hidden" name="productId" value="${product.id}">
    </p>
    <p>
        <button>Comment</button>
    </p>
    </p>
</form>