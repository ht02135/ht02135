<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<div>  
<h2>List Domains</h2>
</div>  

<div>   
<table cellspacing="0">

    <tr>
        <th><s:message code="domains.name" text="Domain Name"/></th>
        <th><s:message code="domains.subDomainName" text="Sub-Domains"/></th>
    </tr>

<c:if test="${not empty domains}">
    <c:forEach var="domain" items="${domains}">
    <tr>
        <td><c:out value="${domain.name}"/></td>
        <td>
        <c:forEach var="subDomain" items="${domain.subDomains}">
            [<c:out value="${subDomain.name}"/>]
        </c:forEach>
        </td>
    </tr>
    </c:forEach>
</c:if>
    
</table> 
</div>   