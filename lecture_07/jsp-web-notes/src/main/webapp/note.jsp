<jsp:include page="header.jsp" />
<h3>Edit Note</h4>
<form class="form-horizontal" method="POST" action="note">
    <input type="hidden" name="id" value="${form.id}" />
    <!-- Text input-->
    <div class="form-group">
        <label class="col-md-4 control-label" for="title">Title</label>  
        <div class="col-md-8">
            <input id="title" name="title" placeholder="Enter note title" class="form-control input-md" required="" type="text" value="${form.title}">

        </div>
    </div>

    <div class="form-group">
        <label class="col-md-4 control-label" for="text">Text</label>
        <div class="col-md-8">                     
            <textarea style="max-width: none; height: 300px" class="form-control" id="text" name="text" value="${form.text}"></textarea>
        </div>
    </div>

    <div class="form-group">
        <label class="col-md-4 control-label" for="save"></label>
        <div class="col-md-8">
            <button id="save" name="save" class="btn btn-primary">Save</button>
            <a id="cancel" name="cancel" class="btn btn-danger" role="button" href="home">Cancel</a>
        </div>
    </div>
</form>
<jsp:include page="footer.jsp" />