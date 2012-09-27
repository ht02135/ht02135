<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>All Domains</title>
</head>

<body>
<f:view>
    <h:panelGroup>
        <h:dataTable border="1" cellpadding="1" cellspacing="1"
        value="#{domainBean.domains}"
        var="domain"
        binding="#{domainBean.domainTable}">
        
            <f:facet name="header">
                <h:outputText value="Domains - start" />
            </f:facet> 
            
            <h:column>
                <f:facet name="header">
                    <h:outputText value="Name"/>
                </f:facet> 
                <h:outputText value="#{domain.name}"></h:outputText>
            </h:column>
            
            <h:column>
                <f:facet name="header">
                    <h:outputText value="Parent Domain Name"/>
                </f:facet> 
                <h:outputText value="#{domain.parentDomain.name}"></h:outputText>
            </h:column>
            
            <f:facet name="footer">
                <!-- commandLink must be within form... -->
                <h:form>
                    <h:panelGroup>
                        <h:commandLink action="#{domainBean.addDomain}">
                            <h:outputText value="Add domain (Action)" />
                        </h:commandLink>
                    </h:panelGroup>
                </h:form>
            </f:facet> 

        </h:dataTable><br><br>
    </h:panelGroup>
</f:view>
</body>

</html> 