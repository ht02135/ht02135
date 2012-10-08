package com.hung.mockito;

import java.util.Hashtable;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PricingServiceMockTest {
    
    // http://mockito.googlecode.com/svn/branches/1.7/javadoc/org/mockito/Mockito.html#doNothing%28%29
    
    private static Logger log = Logger.getLogger(PricingServiceMockTest.class);
    
    private IPricingService pricingService;
    
    @MockitoAnnotations.Mock
    private IPricingDAO pricingDAOMock;

    @Before
    public void setUp() { 
        MockitoAnnotations.initMocks(this); // Initializes objects annotated with @Mock
        pricingService = new PricingService();
        pricingService.setPricingDAO(pricingDAOMock);
    }
    
    @Test
    public void testPrice() {
        try {
            // stubbing
            //Mockito.stub(pricingDAOMock.getPrice("one")).toReturn(1);   // deprecated, use when()
            Mockito.when(pricingDAOMock.getPrice("one")).thenReturn(1);   // use auto-boxing
            
            Integer expectedPrice = 1;
            Integer actualPrice = pricingService.getPrice("one");
            Assert.assertEquals(expectedPrice, actualPrice);
            
            // Verify behavior
            Mockito.verify(pricingDAOMock).getPrice("one");
        } catch (Exception e) {}
    }
    
    @Test(expected = SkuNotFoundException.class)
    public void testPriceThrowsSKUNotFound() throws SkuNotFoundException {
        Mockito.when(pricingDAOMock.getPrice("non-existence")).thenThrow(new SkuNotFoundException());
        Integer actualPrice = pricingService.getPrice("non-existence");
    }
    
    @Test(expected = RuntimeException.class)
    public void testPriceThrowsRuntimeException() throws RuntimeException {
        try {
            Mockito.when(pricingDAOMock.getPrice("two")).thenThrow(new RuntimeException("Fatal data access exception"));
            Integer actualPrice = pricingService.getPrice("two");
        } catch (SkuNotFoundException e) {}
    }
    
    // consecutive stubbing
    @Test(expected = RuntimeException.class)
    public void testConsecutiveStubbing() throws RuntimeException {
        try {
            Mockito.when(pricingDAOMock.getPrice("one"))
            .thenReturn(1)
            .thenThrow(new RuntimeException("Fatal data access exception"));
        
        Integer expectedPrice = 1;
        Integer actualPrice = pricingService.getPrice("one");
        Assert.assertEquals(expectedPrice, actualPrice);
        
        actualPrice = pricingService.getPrice("one");   // throw exception
        actualPrice = pricingService.getPrice("one");   // throw exception again (last stubbing wins)
        
        // Verify behavior
        Mockito.verify(pricingDAOMock).getPrice("one");
        Mockito.verify(pricingDAOMock).getPrice("one");
        Mockito.verify(pricingDAOMock).getPrice("one");
        } catch (SkuNotFoundException e) {}
    }
    
    // do nothing
    @Test
    public void testStatus()  {
        Mockito.doNothing().when(pricingDAOMock).performDiagnostic();
        
        pricingService.checkStatus();
        
        // Verify behavior
        Mockito.verify(pricingDAOMock).performDiagnostic();
    }
    
    //////// Nested Class ////////////////////////////////////////////////////////////////////////////////////
    
    private static class SkuNotFoundException extends Exception {
        public SkuNotFoundException() {
            super();
        }
    }
   
    private static interface IPricingService {
        public Integer getPrice(String sku) throws SkuNotFoundException, RuntimeException;
        
        public void checkStatus();
        
        public void setPricingDAO(IPricingDAO pricingDAO);
    }
    
    private static interface IPricingDAO {
        public Integer getPrice(String sku) throws SkuNotFoundException, RuntimeException;
        
        public void performDiagnostic();
    }
    
    private static class PricingService implements IPricingService {
        
        private IPricingDAO pricingDAO = null;

        // return value or throw exception
        public Integer getPrice(String sku) throws SkuNotFoundException, RuntimeException {
            return pricingDAO.getPrice(sku);
        }
        
        // return nothing
        public void checkStatus() {
            pricingDAO.performDiagnostic();
        }
        
        // setter
        
        public void setPricingDAO(IPricingDAO pricingDAO) {
            this.pricingDAO = pricingDAO;
        }
    }
    
    private static class PricingDAO implements IPricingDAO {
        
        private Hashtable<String,Integer> SkuPricingMapping = null;
        
        public PricingDAO() {
            SkuPricingMapping= new Hashtable<String,Integer>(3);
            SkuPricingMapping.put("one", new Integer(1));
            SkuPricingMapping.put("two", new Integer(2));
            SkuPricingMapping.put("three", new Integer(3));
        }

        @Override
        public Integer getPrice(String sku) throws SkuNotFoundException, RuntimeException {
            if (sku == null) throw new RuntimeException("sku is null");
            
            Integer price = SkuPricingMapping.get(sku);
            if (price == null) throw new SkuNotFoundException();
            return price;
        }
        
        public void performDiagnostic() {
            log.info("PricingDAO.performDiagnostic complete");
        }
    }
}
