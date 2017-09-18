package com.cj.bigVisibleCruiseWeb.server;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.quartz.SchedulerFactory;

public class MainOptionsTest {
    @Test 
    public void testDefaultPort(){
    	Integer defaultPort = 8080;
    	MainOptions mainOptions = new MainOptions(new String[0]);
    	assertEquals("The default port was not properly set up",defaultPort, mainOptions.getPort());
    }
    
    @Test
    public void testCanChangeServerPort(){
    	Integer unstandardPort = 999;
    	
    	String[] argsWithSpace = {"-p", unstandardPort.toString()};
    	MainOptions mainOptions = new MainOptions(argsWithSpace);
    	assertEquals("The default port was not properly set up",unstandardPort, mainOptions.getPort());
    	
    	String[] argsWithEquals = {"--port="+unstandardPort.toString()};
    	mainOptions = new MainOptions(argsWithEquals);
    	assertEquals("The default port was not properly set up",unstandardPort, mainOptions.getPort());
    }
    
    @Test
    public void testCanPassThreeCruiseFeeds(){
    	String cruiseUrl1 = "cruiseUrl1";
    	String cruiseUrl2 = "cruiseUrl2";
    	String cruiseUrl3 = "cruiseUrl3";
    	
    	String[] args = {"-f", cruiseUrl1, "--feed="+cruiseUrl2, "--feed", cruiseUrl3};
    	MainOptions mainOptions = new MainOptions(args);
    	assertTrue(mainOptions.getCruiseFeeds().contains(cruiseUrl1));
    	assertTrue(mainOptions.getCruiseFeeds().contains(cruiseUrl2));
    	assertTrue(mainOptions.getCruiseFeeds().contains(cruiseUrl3));
    	
    }
    
    @Test 
    public void testCanGetCommaSeparatedListOfFeeds(){
    	String cruiseUrl1 = "cruiseUrl1";
    	String cruiseUrl2 = "cruiseUrl2";
    	
    	String[] args = {"-f", cruiseUrl1, "--feed="+cruiseUrl2};
    	MainOptions mainOptions = new MainOptions(args);
    	assertEquals(
    			"Comma Separated List Should Have Been Created",
    			"cruiseUrl1,cruiseUrl2", 
    			mainOptions.getCruiseFeedsAsCommaSeparatedString()
			);
    	
    }
}
