<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="reviews" type="com.es.phoneshop.model.productReview.ArrayListProductReviewDao" scope="request"/>
<tags:master pageTitle="ProductReviews">

    <c:url value="/review/processing" var="reviewProcessingPage"/>
    <form method="post" action="${reviewProcessingPage}">
        <table>
            <thead>
            <tr>
                <td>Name</td>
                <td>Rating</td>
                <td>Comment</td>
            </tr>
            </thead>

            <c:forEach var="comment" items="${reviews.submittedReviews}" varStatus="status">
                <tr>
                    <td>
                        ${comment.name}
                    </td>
                    <td>
                        ${comment.rating}
                    </td>
                    <td>
                        ${comment.comment}
                    </td>

                </tr>
            </c:forEach>

        </table>

</tags:master>
