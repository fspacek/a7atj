<%@ taglib prefix="c" uri="http://xmlns.jcp.org/jsp/jstl/core" %>
<jsp:include page="header.jsp"/>
<div class="row">
    <form class="form-signin" method="POST" action="login">
        <h2 class="form-signin-heading">Please login</h2>
        <span class="text-warning"><c:out value="${message}"/></span>
        <input type="text" class="form-control" name="username" placeholder="Email Address" required="" autofocus=""/>
        <input type="password" class="form-control" name="password" placeholder="Password" required=""/>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>

        <div style="padding-top:15px; font-size:85%">
            Don't have an account!
            <a href="registration">
                Sign Up Here
            </a>
        </div>
    </form>
</div>
<jsp:include page="footer.jsp"/>