package com.hung.utils.selenium2webdriverbacked;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;

import com.hung.utils.selenium.MySelenium;

public class MyWebDriverBackedSelenium extends WebDriverBackedSelenium implements MySelenium {

    public MyWebDriverBackedSelenium(WebDriver baseDriver, String baseUrl) {
        super(baseDriver, baseUrl);
    }

    @Override
    public int getCSSCount(String aCSSLocator) {
        String jsScript = "var cssMatches = eval_css(\"%s\", window.document);cssMatches.length;";
        return Integer.parseInt(getEval(String.format(jsScript, aCSSLocator))); 
    }

}
