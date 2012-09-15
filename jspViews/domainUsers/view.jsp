<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
    <h2>View Domain User</h2>
</div>

<div>
    <table>
        <tr>
            <th>Login Id:</th>
            <td><c:out value="${domainUser.loginId}"/></td>
        </tr>
        <tr>
            <th>Name:</th>
            <td><c:out value="${domainUser.name}"/></td>
        </tr>
        <tr>
            <th>Domain Name:</th>
            <td><c:out value="${domainUser.userDomain.name}"/></td>
        </tr>
    </table>
</div>


        
        
        