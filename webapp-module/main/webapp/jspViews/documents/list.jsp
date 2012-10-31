<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div>  
<h2>Document Manager</h2>
</div>  

<div>   
    <h3>Add new document</h3>
    <s:url var="actoinURI" value="/auction/documents/save" />
    <form:form method="post" action="${actoinURI}" modelAttribute="document" enctype="multipart/form-data">
        <table>
            <tr>
                <td><form:label path="name">Name</form:label></td>
                <td><form:input path="name" /></td>
            </tr>
            <tr>
                <td><form:label path="content">Document</form:label></td>
                <td><input type="file" name="file" id="file"></input></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Add Document"/>
                </td>
            </tr>
        </table> 
    </form:form>
</div>   

<br/>
<div> 
    <h3>Document List</h3>
<c:if  test="${!empty documents}">
    <table class="data">
        <tr>
            <th>Name</th>
            <th>Download</th>
            <th>Delete</th>
        </tr>
    <c:forEach items="${documents}" var="document">
        <s:url var="downloadDocumentUrl" value="/auction/documents/${document.id}?download" />
        <s:url var="deleteDocumentUrl" value="/auction/documents/${document.id}?remove" />
        <tr>
            <td width="100px">${document.name}</td>
            <td width="20px"><a href="${downloadDocumentUrl}">Download</a></td>
            <td width="20px"><a href="${deleteDocumentUrl}">Delete</td>
        </tr>
    </c:forEach>
    </table>
</c:if>
</div> 

<script  type="text/javascript">
    // http://www.javascript-coder.com/html-form/javascript-form-validation.phtml
    var documentFrmValidator = new Validator("document");
    documentFrmValidator.addValidation("name", "req");
    documentFrmValidator.addValidation("file", "req_file");
</script>