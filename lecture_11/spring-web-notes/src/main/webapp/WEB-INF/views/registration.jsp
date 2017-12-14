<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"/>
<div class="row">
    <form class="form-horizontal" action='registration' method="POST">
        <fieldset>
            <div class="control-group">
                <!-- Username -->
                <label class="control-label" for="firstname">First name</label>
                <div class="controls">
                    <input type="text" id="firstname" name="firstname" placeholder="" class="form-control">
                </div>
            </div>
            <div class="control-group">
                <!-- Username -->
                <label class="control-label" for="lastname">Last name</label>
                <div class="controls">
                    <input type="text" id="lastname" name="lastname" placeholder="" class="form-control">
                    <p class="help-block">Username can contain any letters or numbers, without spaces</p>
                </div>
            </div>

            <div class="control-group">
                <!-- E-mail -->
                <label class="control-label" for="username">Username</label>
                <div class="controls">
                    <input type="text" id="username" name="username" placeholder="" class="form-control">
                    <p class="help-block">Please provide your username</p>
                </div>
            </div>

            <div class="control-group">
                <!-- Password-->
                <label class="control-label" for="password">Password</label>
                <div class="controls">
                    <input type="password" id="password" name="password" placeholder="" class="form-control">
                    <p class="help-block">Password should be at least 4 characters</p>
                </div>
            </div>
            <div class="control-group">
                <!-- Button -->
                <div class="controls">
                    <button class="btn btn-success">Register</button>
                </div>
            </div>
        </fieldset>
    </form>
</div>
<jsp:include page="footer.jsp"/>