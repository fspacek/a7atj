<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp" />
<div class="row">
    <span class="pull-right" style="margin-bottom: 5px">
        <a class="btn btn-primary" href="note" role="button">Add Note</a>
    </span>
</div>

<div class="row">
    <c:if test="${!notes.isEmpty()}">
        <c:forEach items="${notes}" var="note">
            <div class="panel panel-success">
                <div class="panel-heading"><c:out value="${note.title}"/>   
                    <span class="pull-right">
                        <a class="text-danger" href="?action=delete&id=<c:out value='${note.id}'/>">
                            <i class="material-icons">clear</i>
                        </a>
                    </span>
                </div>
                <div class="panel-body"> <c:out value="${note.text}"/></div>
            </div>
        </c:forEach>
    </c:if>
</div>
<jsp:include page="footer.jsp" />