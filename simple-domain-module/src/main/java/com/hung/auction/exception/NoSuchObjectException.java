package com.hung.auction.exception;

/*
Item 39: Use exceptions only for exceptional conditions. That is, do not use exceptions for control flow, 
         such as catching NoSuchElementException when calling Iterator.next() instead of first checking
          Iterator.hasNext().

Item 40: Use checked exceptions for recoverable conditions and runtime exceptions for programming errors. 
         Here, Bloch echoes the conventional Sun wisdom -- that runtime exceptions should be used only to 
         indicate programming errors, such as precondition violations.

Item 41: Avoid unnecessary use of checked exceptions. In other words, don't use checked exceptions for 
         conditions from which the caller could not possibly recover, or for which the only foreseeable 
         response would be for the program to exit.

Item 43: Throw exceptions appropriate to the abstraction. In other words, exceptions thrown by a method 
         should be defined at an abstraction level consistent with what the method does, not necessarily 
         with the low-level details of how it is implemented. For example, a method that loads resources 
         from files, databases, or JNDI should throw some sort of ResourceNotFound exception when it cannot 
         find a resource (generally using exception chaining to preserve the underlying cause), rather 
         than the lower-level IOException, SQLException, or NamingException.
*/

/*
	normally, exception like no such object should be checked exception (extends exception).
	But NoSuchObjectWebApplicationException is mean to mimic WebApplicationException which
	will be caught by ExceptionMapper and status code be sent to client 
 */
public class NoSuchObjectException extends RuntimeException {
}
