package com.hung.engine;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@TransactionConfiguration
@Transactional
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
        
        this.xmlNameGeneratorMock = EasyMock.createMock(XMLNameGenerator.class);
        this.htmlNameGeneratorMock = EasyMock.createMock(HTMLNameGenerator.class);
        this.nameGenerator.setXMLNameGenerator(this.xmlNameGeneratorMock);
        this.nameGenerator.setHTMLNameGenerator(this.htmlNameGeneratorMock);
    }
    
    @After
    public void tearDown() {
    }

    // mock is best way to test NameGenerator code not worry about its dependencies via mock, but
    // http://devlearnings.wordpress.com/2010/05/13/easymock-expectationeasymock-expect-flavours-explained/
    // Expect and Return
    @Test
    public void testNameGeneration() {
        // expect
        EasyMock.expect(this.xmlNameGeneratorMock.getFileName("A")).andReturn("A.xml");
        EasyMock.expect(this.htmlNameGeneratorMock.getFileName("A")).andReturn("A.html");
        
        // into replay mode
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
        
        // Verify behavior.
        EasyMock.verify(this.xmlNameGeneratorMock);
        EasyMock.verify(this.htmlNameGeneratorMock);
    }
    
    // Expect and Throw
    @Test(expected = RuntimeException.class)
    public void testNameGenerationThrowsRuntimeException() throws RuntimeException {
        // expect
        EasyMock.expect(this.xmlNameGeneratorMock.getFileName(null)).andThrow(new RuntimeException("null or empty name"));
        EasyMock.expect(this.htmlNameGeneratorMock.getFileName(null)).andThrow(new RuntimeException("null or empty name"));
        EasyMock.expect(this.xmlNameGeneratorMock.getFileName("")).andThrow(new RuntimeException("null or empty name"));
        EasyMock.expect(this.htmlNameGeneratorMock.getFileName("")).andThrow(new RuntimeException("null or empty name"));

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
    
    // cant really apply Expect and Delegate, Expect and Answer, and Only Expect and Nothing to Return option
    // on mocks that dont really need...

    public static class NameGenerator {
        
        public static final String XML_TYPE = "xml";
        public static final String HTML_TYPE = "html";
        
        private XMLNameGenerator xmlNameGenerator = null;
        private HTMLNameGenerator htmlNameGenerator = null;
        
        public String getFileName(String name, String type) throws RuntimeException {
            if (name == null || name.equalsIgnoreCase("")) throw new RuntimeException("null or empty name");
            if (type.equalsIgnoreCase(XML_TYPE)) return xmlNameGenerator.getFileName(name);
            if (type.equalsIgnoreCase(HTML_TYPE)) return htmlNameGenerator.getFileName(name);
            return name+".txt";
        }
        
        public void setXMLNameGenerator(XMLNameGenerator xMLNameGenerator) {
            this.xmlNameGenerator = xMLNameGenerator;
        }
        
        public void setHTMLNameGenerator(HTMLNameGenerator hTMLNameGenerator) {
            this.htmlNameGenerator = hTMLNameGenerator;
        }
    }
    
    public static class XMLNameGenerator {
        public String getFileName(String name) throws RuntimeException {
            if (name == null || name.equalsIgnoreCase("")) throw new RuntimeException("null or empty name");
            return name+".xml";
        }
    }
    
    public static class HTMLNameGenerator {
        public String getFileName(String name) throws RuntimeException {
            if (name == null || name.equalsIgnoreCase("")) throw new RuntimeException("null or empty name");
            return name+".html";
        }
    }
}
