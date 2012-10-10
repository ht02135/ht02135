package com.hung.fixture;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hung.auction.domain.Domain;

/*
 test fixture is a fixed state of a set of objects used as a baseline for running tests. In other word, 
 creating a test fixture is to create a set of objects initialized to certain states.
 1>Loading a database with a specific, known set of data
 2>Copying a specific known set of files
 3>Preparation of input data and setup/creation of fake or mock objects
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
public class DomainUsingMockTest extends DomainMockFixture  {
    
    // domainNameDomainMappings is already pre-populated with root domain in fixture
    
    @Test
    public void testFindDomainViaService() {
        IAnswer findByName = new IAnswer() {
            public Domain answer() throws Throwable {
                Object[] arguments = EasyMock.getCurrentArguments();
                Domain domain = null;
                if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                    String domainName = (String) arguments[0];
                    domain = domainNameDomainMappings.get(domainName);
                }
                return domain;
            }
        };
        EasyMock.expect(domainDAOMock.findByName("root")).andAnswer(findByName);
        
        // into replay mode
        EasyMock.replay(domainDAOMock);
        
        // run the method
        String expectedDomainName = "root";
        String actualDomainName = domainServiceWithDAOMock.findByName("root").getName();
        Assert.assertEquals(expectedDomainName, actualDomainName);
        
        // Verify behavior.
        EasyMock.verify(domainDAOMock);
    }
}
