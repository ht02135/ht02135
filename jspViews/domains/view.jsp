<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
    <h2>View Domain</h2>
</div>

<c:if test="${not empty domain}">
<div>
    <table>
        <tr>
            <th>Name:</th>
            <td><c:out value="${domain.name}"/></td>
        </tr>
        <tr>
            <th>Sub-Domains:</th>
            <td>
    <c:forEach var="subDomain" items="${domain.subDomains}">
                [<c:out value="${subDomain.name}"/>]
    </c:forEach>
            </td>
        </tr>
    </table>
</div>
</c:if>