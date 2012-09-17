<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div>
<h2>Login</h2>
</div>

<div>
<form:form method="post" modelAttribute="jaxbClientSession">         
   <fieldset> 
   <table cellspacing="0">
      <tr>
         <th><form:label path="domainName">Domain Name:</form:label></th>
         <td><form:input path="domainName" size="15" /><br/></td>
      </tr>
      <tr>
         <th><form:label path="loginId">Login Id:</form:label></th>
         <td><form:input path="loginId" size="15" maxlength="15" /></td>
      </tr>
      <tr>
         <th></th>
         <td><input name="login" type="submit" value="Login" /></td>
      </tr>
   </table>
   </fieldset>
</form:form>
</div>