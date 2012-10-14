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
            <h:inputText id="currentDomainName" value="#{managedDomain.domainName}">
            </h:inputText>
            
            <h:outputLabel value="Enter parent domain" for="currentParentDomainName">
            </h:outputLabel>
            <h:selectOneMenu id="currentParentDomainName" value="#{managedDomain.parentDomain}" converter="#{domainConverterService}">
                <f:selectItems value="#{managedDomainItems.domainItems}" />
            </h:selectOneMenu>
        
            <f:facet name="footer">
                <h:panelGroup>
                    <!-- action attribute - efers to an action method which returns a String  from which the Faces navigation 
                         model can use to decide whether or not a navigation is necessary based on the value of the string 
                    --> 
                    <h:commandLink action="#{managedDomain.createDomain}">
                        <h:outputText value="Create domain (Action)" />
                    </h:commandLink>
        
                    <!-- actionListener attribute - An actionlistener method compared to an action method does not return a 
                         String. Instead it returns void. It is basically identical to the action method but instead it just 
                         executes the code after an action event (button click or link click) but a navigation is not needed
                    -->
                    <h:commandLink actionListener="#{managedDomain.createDomain}">
                        <h:outputText value="Create domain (ActionListener)" />
                    </h:commandLink>
            </h:panelGroup>
        </f:facet> 
    

        </h:panelGrid>
    </h:form>
</f:view>
</body>

</html> 