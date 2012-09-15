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
    <h:form>
        <h:panelGrid columns="2">
        
            <h:outputLabel value="Enter domain name" for="currentDomainName">
            </h:outputLabel>
            <h:inputText id="currentDomainName" value="#{domainBean.domainName}">
            </h:inputText>
            
            <h:outputLabel value="Enter parent domain" for="currentParentDomainName">
            </h:outputLabel>
            <h:selectOneMenu id="currentParentDomainName" value="#{domainBean.parentDomain}" converter="#{domainConverterService}">
                <f:selectItems value="#{domainBean.domainItems}" />
            </h:selectOneMenu>
        
            <f:facet name="footer">
                <h:panelGroup>
                    <h:commandLink action="#{domainBean.createDomain}">
                        <h:outputText value="Create domain (Action)" />
                    </h:commandLink>
                    <h:commandLink actionListener="#{domainBean.createDomain}">
                        <h:outputText value="Create domain (ActionListener)" />
                    </h:commandLink>
            </h:panelGroup>
        </f:facet> 
    

        </h:panelGrid>
    </h:form>
</f:view>
</body>

</html> 