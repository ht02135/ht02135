<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<div>   
<h2>List Domain Users</h2>
</div>   

<div>   
<table cellspacing="0">

    <tr>
        <th><s:message code="domainUsers.name" text="User Name"/></th>
        <th><s:message code="domainUsers.loginId" text="User Login Id"/></th>
        <th><s:message code="domainUsers.domainName" text="Domain Name"/></th>
        <th><s:message code="domainUsers.view" text="View"/></th>
        <th><s:message code="domainUsers.edit" text="Edit"/></th>
    </tr>

<c:if test="${not empty domainUsers}">
    <c:forEach var="domainUser" items="${domainUsers}">
    <s:url var="viewDomainUserUrl" value="/auction/domainUsers/${domainUser.loginId}" />
    <s:url var="editDomainUserUrl" value="/auction/domainUsers/${domainUser.loginId}?edit" />
    <tr>
        <td><c:out value="${domainUser.name}"/></td>
        <td><c:out value="${domainUser.loginId}"/></td>
        <td><c:out value="${domainUser.userDomain.name}"/></td>
        <td><a href="${viewDomainUserUrl}">View</a></td>
        <td><a href="${editDomainUserUrl}">Edit</td>
    </tr>
    </c:forEach>
</c:if>
    
</table> 
</div>   

<div>  
    <s:url var="newDomainUserUrl" value="/auction/domainUsers?new" />
    <a href="${newDomainUserUrl}">Create a new domain user</a> 
</div>   