<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div>
<h2>Document Manager</h2>
</div>

<div>
    <h3>Search Term</h3>
    <s:url var="actoinURI" value="/auction/searchdocuments/search" />
    <form:form method="post" action="${actoinURI}" >
        <table>
            <tr>
                <td>Term</td>
                <td><input type="text" name="term" id="term"></input></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Search Term"/>
                </td>
            </tr>
        </table>
    </form:form>
</div>

<br/>
<div>
    <h3>Document List</h3>
<c:if  test="${clientSearchResult.status == 'BUSY'}">
    <h3>BUSY</h3>
</c:if>

<c:if  test="${!empty clientSearchResult.documents}">
    <table class="data">
        <tr>
            <th>Name</th>
        </tr>
    <c:forEach items="${clientSearchResult.documents}" var="document">
        <tr>
            <td width="100px">${document.name}</td>
        </tr>
    </c:forEach>
    </table>
</c:if>
</div>