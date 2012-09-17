<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="isReadOnly" value="true"/>

<div>
<c:choose>
    <c:when test="${action == 'add'}">
        <c:set var="isReadOnly" value="false" />
        <h2>Add Domain User</h2>
    </c:when>
    <c:when test="${action == 'edit'}">
        <h2>Edit Domain User</h2>
    </c:when>
    <c:otherwise>
        <h2>wrong action=<c:out value="${action}"/></h2>
    </c:otherwise>
</c:choose>
</div>

<div>
    <s:url var="actoinURI" value="/auction/domainUsers/${action}" />
    <form:form method="post" modelAttribute="jaxbDomainUser" action="${actoinURI}">         
        <fieldset> 
        <table cellspacing="0">
            <tr>
                <th><form:label path="loginId">Login Id:</form:label></th>
                <td><form:input path="loginId" readonly="${isReadOnly}" size="15"/><br/></td>
            </tr>
            <tr>
                <th><form:label path="name">Name:</form:label></th>
                <td><form:input path="name" size="15"/><br/></td>
            </tr>
            <tr>
                <th><form:label path="userDomainName">Domain Name:</form:label></th>
                <td><form:select path="userDomainName" items="${domainNames}"/></td>
            </tr>
            <tr>
                <th></th>
                <td><input name="submit" type="submit" value="submit" /></td>
            </tr>
        </table>
        </fieldset>
    </form:form>
</div>