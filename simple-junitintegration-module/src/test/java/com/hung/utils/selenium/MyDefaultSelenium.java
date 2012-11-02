package com.hung.utils.selenium;

import com.thoughtworks.selenium.CommandProcessor;
import com.thoughtworks.selenium.DefaultSelenium;

public class MyDefaultSelenium extends DefaultSelenium implements MySelenium {
    
    public MyDefaultSelenium(CommandProcessor processor) {
        super(processor);
    }
    
    public MyDefaultSelenium(String serverHost, int serverPort, String browserStartCommand, String browserURL) {
        super(serverHost, serverPort, browserStartCommand, browserURL);
    }

    @Override
    public int getCSSCount(String aCSSLocator) {
        String jsScript = "var cssMatches = eval_css(\"%s\", window.document);cssMatches.length;";
        return Integer.parseInt(getEval(String.format(jsScript, aCSSLocator))); 
    }

}
