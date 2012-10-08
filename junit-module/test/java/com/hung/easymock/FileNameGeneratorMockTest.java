package com.hung.easymock;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
public class FileNameGeneratorMockTest  {
    
    // http://www.easymock.org/EasyMock3_1_Documentation.html

    private static Logger log = Logger.getLogger(FileNameGeneratorMockTest.class);
    
    private NameGenerator nameGenerator;
    private XMLNameGenerator xmlNameGeneratorMock;
    private HTMLNameGenerator htmlNameGeneratorMock;
    
    @Before
    public void setUp() { 
        // setup class-under-test and its mocks
        this.nameGenerator = new NameGenerator();
        
        /*
        1>order of method calls:
          On a Mock Object returned by a EasyMock.createMock(), the order of method calls is not checked. If you would 
          like a strict Mock Object that checks the order of method calls, use EasyMock.createStrictMock() to create it.
        2>nice mock:
          On a Mock Object returned by createMock() the default behavior for all methods is to throw an AssertionError 
          for all unexpected method calls. If you would like a "nice" Mock Object that by default allows all method calls 
          and returns appropriate empty values (0, null or false), use createNiceMock() instead. 
        */
        this.xmlNameGeneratorMock = EasyMock.createMock(XMLNameGenerator.class);
        this.htmlNameGeneratorMock = EasyMock.createMock(HTMLNameGenerator.class);
        this.nameGenerator.setXMLNameGenerator(this.xmlNameGeneratorMock);
        this.nameGenerator.setHTMLNameGenerator(this.htmlNameGeneratorMock);
    }
    
    @After
    public void tearDown() {
    }
    
    // Expect and Nothing
    @Test
    public void testStatus() {
        this.xmlNameGeneratorMock.performDiagnostic();
        this.htmlNameGeneratorMock.performDiagnostic();
        
        EasyMock.replay(this.xmlNameGeneratorMock);
        EasyMock.replay(this.htmlNameGeneratorMock);
        
        this.nameGenerator.checkStatus();
        
        EasyMock.verify(this.xmlNameGeneratorMock);
        EasyMock.verify(this.htmlNameGeneratorMock);
    }

    // mock is best way to test NameGenerator code not worry about its dependencies via mock, but
    // http://devlearnings.wordpress.com/2010/05/13/easymock-expectationeasymock-expect-flavours-explained/
    // Expect and Return
    @Test
    public void testNameGeneration() {
        /*
         1>exepect, in the record state (before calling replay), the Mock Object does not behave like a Mock Object, but 
           it records method calls. After calling replay, it behaves like a Mock Object, checking whether the expected 
           method calls are really done. 
         2>we next need to tell EasyMock how we expect that mock to be used.  that is, what methods we expect to be called on it. 
         */
        EasyMock.expect(this.xmlNameGeneratorMock.generateFileName("A")).andReturn("A.xml");
        EasyMock.expect(this.htmlNameGeneratorMock.generateFileName("A")).andReturn("A.html");
        
        /*
         1>Calling replay tells EasyMock that you are no longer specifying expected method calls and moving into replay mode.
         */
        EasyMock.replay(this.xmlNameGeneratorMock);
        EasyMock.replay(this.htmlNameGeneratorMock);
        
        // Run the method.
        String expectedFileName = "A.html";
        String actualFileName = this.nameGenerator.getFileName("A", NameGenerator.HTML_TYPE);
        Assert.assertEquals(expectedFileName, actualFileName);
        
        expectedFileName = "A.xml";
        actualFileName = this.nameGenerator.getFileName("A", NameGenerator.XML_TYPE);
        Assert.assertEquals(expectedFileName, actualFileName);
        
        expectedFileName = "A.txt";
        actualFileName = this.nameGenerator.getFileName("A", "");
        Assert.assertEquals(expectedFileName, actualFileName);
        
        /*
         1>verify behavior, If we specify behavior, we would like to verify that it is actually used. The current test 
           would pass if no method on the Mock Object is called. To verify that the specified behavior has been used, 
           we have to call verify(mock)
         2>The final step is where we ask EasyMock to confirm that all our expectations were met. That is, we ask EasyMock 
           to confirm that a method call corresponding to each expectation we set is actually made.
         */
        EasyMock.verify(this.xmlNameGeneratorMock);
        EasyMock.verify(this.htmlNameGeneratorMock);
    }
    
    // Expect and Throw
    @Test(expected = RuntimeException.class)
    public void testNameGenerationThrowsRuntimeException() throws RuntimeException {
        // expect
        EasyMock.expect(this.xmlNameGeneratorMock.generateFileName(null)).andThrow(new RuntimeException("null or empty name"));
        EasyMock.expect(this.htmlNameGeneratorMock.generateFileName(null)).andThrow(new RuntimeException("null or empty name"));
        EasyMock.expect(this.xmlNameGeneratorMock.generateFileName("")).andThrow(new RuntimeException("null or empty name"));
        EasyMock.expect(this.htmlNameGeneratorMock.generateFileName("")).andThrow(new RuntimeException("null or empty name"));

        // into replay mode
        EasyMock.replay(this.xmlNameGeneratorMock);
        EasyMock.replay(this.htmlNameGeneratorMock);

        // Run the method.
        String actualFileName = this.nameGenerator.getFileName(null, NameGenerator.HTML_TYPE);
        actualFileName = this.nameGenerator.getFileName(null, NameGenerator.XML_TYPE);
        actualFileName = this.nameGenerator.getFileName("", NameGenerator.HTML_TYPE);
        actualFileName = this.nameGenerator.getFileName("", NameGenerator.XML_TYPE);
        
        // Verify behavior.
        EasyMock.verify(this.xmlNameGeneratorMock);
        EasyMock.verify(this.htmlNameGeneratorMock);
    }
    
    ////// Nested Classes //////////////////////////////////////////////////////////////////////////////////////////

    // for now, set to private to avoid public access
    private static class NameGenerator {
        
        public static final String XML_TYPE = "xml";
        public static final String HTML_TYPE = "html";
        
        private XMLNameGenerator xmlNameGenerator = null;
        private HTMLNameGenerator htmlNameGenerator = null;
        
        // methods
        
        // for Expect and Return expect scenario
        public String getFileName(String name, String type) throws RuntimeException {
            if (name == null || name.equalsIgnoreCase("")) throw new RuntimeException("null or empty name");
            if (type.equalsIgnoreCase(XML_TYPE)) return xmlNameGenerator.generateFileName(name);
            if (type.equalsIgnoreCase(HTML_TYPE)) return htmlNameGenerator.generateFileName(name);
            return name+".txt";
        }
        
        // for Expect and Nothing expect scenario
        public void checkStatus() {
            xmlNameGenerator.performDiagnostic();
            htmlNameGenerator.performDiagnostic();
            log.info("NameGenerator.checkStatus complete");
        }
        
        /* Expect and Answer
           1>Sometimes we would like our mock object to return a value or throw an exception that is created at the time 
             of the actual call.
           2>Inside an IAnswer callback, the arguments passed to the mock call are available via EasyMock.getCurrentArguments()
         */
        
        // setter
        
        public void setXMLNameGenerator(XMLNameGenerator xMLNameGenerator) {
            this.xmlNameGenerator = xMLNameGenerator;
        }
        
        public void setHTMLNameGenerator(HTMLNameGenerator hTMLNameGenerator) {
            this.htmlNameGenerator = hTMLNameGenerator;
        }
    }
    
    // for now, set to private to avoid public access
    private static class XMLNameGenerator {
        // for Expect and Return expect scenario
        public String generateFileName(String name) throws RuntimeException {
            if (name == null || name.equalsIgnoreCase("")) throw new RuntimeException("null or empty name");
            return name+".xml";
        }
        
        // for Expect and Nothing expect scenario
        public void performDiagnostic() {
            log.info("XMLNameGenerator.performDiagnostic complete");
        }
    }
    
    // for now, set to private to avoid public access
    private static class HTMLNameGenerator {
        // for Expect and Return expect scenario
        public String generateFileName(String name) throws RuntimeException {
            if (name == null || name.equalsIgnoreCase("")) throw new RuntimeException("null or empty name");
            return name+".html";
        }
        
        // for Expect and Nothing expect scenario
        public void performDiagnostic() {
            log.info("HTMLNameGenerator.performDiagnostic complete");
        }
    }
}