package com.hung.junit.experiment.mockito;

import java.io.Serializable;
import java.util.Hashtable;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class PricingServiceMockTest {
    
    // http://mockito.googlecode.com/svn/branches/1.7/javadoc/org/mockito/Mockito.html#doNothing%28%29
    
    private static Logger log = Logger.getLogger(PricingServiceMockTest.class);
    
    private IPricingService pricingService;
    
    @MockitoAnnotations.Mock
    private IPricingDAO pricingDAOMock;
    
    private Hashtable<String, SkuPriceMapping> SkuPricingMappings = null;

    @Before
    public void setUp() { 
        MockitoAnnotations.initMocks(this); // Initializes objects annotated with @Mock
        SkuPricingMappings = new Hashtable<String,SkuPriceMapping>();
        
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
    public void testPriceThenThrowsRuntimeException() throws RuntimeException {
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
    
    /*
     embed data into mock object.  not quite sure that is a good idea.  reason:
     1>system in test shouldnt be mock.  mock should be light
     2>for persist method, do nothing strategy should be sufficient to make sure pricingDAOMock.save() is called
     3>for find method, Mockito.when() stratey should be sufficient to make sure pricingDAOMock.getPrice() return right mocked price
     */
    @Test
    public void testSave() {
        try {
            // save via SkuPricingMappings
            Mockito.doAnswer(new Answer<Void>() {
                @Override
                public Void answer(InvocationOnMock invocation) throws Throwable {
                    Object[] arguments = invocation.getArguments();
                    if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                        SkuPriceMapping skuPriceMapping = (SkuPriceMapping) arguments[0];
                        SkuPricingMappings.put(skuPriceMapping.getSku(), skuPriceMapping);
                    }
                    return null;
                }
            }).when(pricingDAOMock).save(Mockito.any(SkuPriceMapping.class));

            // getPrice via SkuPricingMappings
            Mockito.when(pricingDAOMock.getPrice(Mockito.anyString())).thenAnswer(new Answer<Integer>() {
                @Override
                public Integer answer(InvocationOnMock invocation) throws Throwable {
                    Object[] arguments = invocation.getArguments();
                    if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                        String sku = (String) arguments[0];
                        if (SkuPricingMappings.containsKey(sku)) {
                            return SkuPricingMappings.get(sku).getPrice();
                        }
                    }
                    return null;
                }
            });
            
            SkuPriceMapping skuPriceMapping = new SkuPriceMapping("one", 1);
            pricingService.save(skuPriceMapping);
            
            Integer expectedPrice = skuPriceMapping.getPrice();
            Integer actualPrice = pricingService.getPrice(skuPriceMapping.getSku());
            Assert.assertEquals(expectedPrice, actualPrice);
            
            // Verify behavior
            Mockito.verify(pricingDAOMock).save(skuPriceMapping);
            Mockito.verify(pricingDAOMock).getPrice("one");
            
        } catch (SkuNotFoundException e) {}
    }
    
    //////// Nested Class ////////////////////////////////////////////////////////////////////////////////////
    
    // stock-keeping unit - SKU
    // to keep test sample, SKU will be the name
    
    private static class SkuPriceMapping implements Serializable {
        private String sku;
        private Integer price;
        
        public SkuPriceMapping(String sku, Integer price) {
            this.sku = sku;
            this.price = price;
        }
        
        public String getSku() {
            return this.sku;
        }
        
        public Integer getPrice() {
            return this.price;
        }
    }
    
    private static class SkuNotFoundException extends Exception {
        public SkuNotFoundException() {
            super();
        }
    }
   
    private static interface IPricingService {
        public void save(SkuPriceMapping skuPriceMapping) throws RuntimeException;
        
        public Integer getPrice(String sku) throws SkuNotFoundException, RuntimeException;
        
        public void checkStatus();
        
        public void setPricingDAO(IPricingDAO pricingDAO);
    }
    
    private static interface IPricingDAO {
        public void save(SkuPriceMapping skuPriceMapping) throws RuntimeException;
        
        public Integer getPrice(String sku) throws SkuNotFoundException, RuntimeException;
        
        public void performDiagnostic();
    }
    
    private static class PricingService implements IPricingService {
        
        private IPricingDAO pricingDAO = null;
        
        public void save(SkuPriceMapping skuPriceMapping) throws RuntimeException {
            pricingDAO.save(skuPriceMapping);
        }

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
        
        private Hashtable<String, SkuPriceMapping> SkuPricingMappings = null;
        
        public PricingDAO() {
            SkuPricingMappings = new Hashtable<String,SkuPriceMapping>();
        }
        
        public void save(SkuPriceMapping skuPriceMapping) throws RuntimeException {
            SkuPricingMappings.put(skuPriceMapping.getSku(), skuPriceMapping);
        }

        @Override
        public Integer getPrice(String sku) throws SkuNotFoundException, RuntimeException {
            if (sku == null) throw new RuntimeException("sku is null");
            
            SkuPriceMapping skuPricingMapping = SkuPricingMappings.get(sku);
            if (skuPricingMapping == null) throw new SkuNotFoundException();
            return skuPricingMapping.getPrice();
        }
        
        public void performDiagnostic() {
        }
    }
}
