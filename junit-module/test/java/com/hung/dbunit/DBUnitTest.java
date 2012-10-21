package com.hung.dbunit;

import java.io.FileInputStream;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={"classpath:applicationContext-test2.xml"})
public class DBUnitTest {

    private static Logger log = Logger.getLogger(DBUnitTest.class);
    
    @Autowired 
    @Qualifier("dataSource")
    protected DataSource dataSource;

    protected IDatabaseTester dbTester;
    
    protected IDatabaseConnection dbConnection;
    
    // http://www.dbunit.org/faq.html#generatedtd
    // http://www.dbunit.org/components.html
    // great, now i can subclass this class to inherited my default "test" db environment via dbunit

    @Before
    public void setUp() throws Exception {
        dbTester = new DataSourceDatabaseTester(dataSource, "test");
        
        // build table
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/data.xml"));
        dbTester.setDataSet(dataSet);
        dbTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        dbTester.onSetup();
    }

    @After
    public void tearDown() throws Exception {
        dbTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        dbTester.onTearDown();
    }
}
