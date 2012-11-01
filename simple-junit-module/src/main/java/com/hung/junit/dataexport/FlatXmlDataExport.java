package com.hung.junit.dataexport;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.Logger;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

public class FlatXmlDataExport {
    
    private static Logger log = Logger.getLogger(FlatXmlDataExport.class);
    
    /*
     dbunit is not able to create the database structure but it is able to
     read it in order to generate a dataset from a pre-populated database.

     If you need to convert your DTD to a DDL script there are some tools
     out there able to perform such a conversion. 
     */
    public static void exportTable(IDatabaseConnection connection) throws Exception {
        FlatDtdDataSet.write(connection.createDataSet(), System.out);
        FlatDtdDataSet.write(connection.createDataSet(), new FileOutputStream("src/main/resources/table.dtd"));
    }
    
    /*
     public class AmbiguousTableNameExceptionextends DataSetException
     This exception is thrown by IDataSet when multiple tables having the same name are accessible. This usually 
     occurs when the database connection have access to multiple schemas containing identical table names. 
     
     Possible solutions: 
     1) Use a database connection credential that has access to only one database schema. 
     2) Specify a schema name to the DatabaseConnection or DatabaseDataSourceConnection constructor. 
     3) Enable the qualified table name support (see How-to documentation).
     */
    public static void exportData(IDatabaseConnection connection) throws Exception {
        IDataSet dataSet = connection.createDataSet();
        FlatXmlDataSet.write(dataSet, System.out);
        FlatXmlDataSet.write(dataSet, new FileOutputStream("src/main/resources/data.xml"));
    }
    
    /*
     db.driver=oracle.jdbc.driver.OracleDriver
     db.url=jdbc:oracle:thin:@localhost:1521/xe
     db.username=root
     db.password=admin
     db.dialect=org.hibernate.dialect.Oracle10gDialect
     db.schema=root
     
     Oracle Schema:
     1>Collection of database objects, including logical structures such as tables, views, sequences, stored procedures, 
       synonyms, indexes, clusters, and database links. 
     2>A schema has the name of the user who controls it.  in my case is "root"
     */
    
    // http://www.dbunit.org/faq.html#generatedtd
    public static void main(String[] args) throws Exception {
        Class driverClass = Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection jdbcConnection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe", "root", "admin");
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection,"root");
        
        try {
            exportTable(connection);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        try {
            exportData(connection);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
