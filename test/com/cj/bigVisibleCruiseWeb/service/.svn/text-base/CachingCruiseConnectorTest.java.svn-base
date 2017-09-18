package com.cj.bigVisibleCruiseWeb.service;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

public class CachingCruiseConnectorTest {
    Mockery context = new Mockery(){{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};;
    CachingCruiseConnector connector;
    UrlDocumentParser parser;
    private final static String[] CRUISE_LOCATIONS = {"http://localhost/1.html", "http://localhost/2.html"};
    
    @Before
    public void setUp(){
    	parser = context.mock(UrlDocumentParser.class);
    	connector = new CachingCruiseConnector();
    	connector.setParser(parser);
    	connector.setCruiseLocations(Arrays.asList(CRUISE_LOCATIONS));
    }
	
	@Test public void testXMLIsScannedOnceIfNeverScanned()throws Exception{
		List <BuildStatus> statuses;
		allowConnectorToCallUrlDocumentParserOnce();
		
		//Run twice
		statuses = connector.getBuildStatuses();
		statuses = connector.getBuildStatuses();
		assertTrue(statuses!=null);
		
	}
	
	@Test public void testXMLIsScannedOnRefreshByQuartz()throws Exception{
		connector.lastRefreshTime=new DateTime().minusSeconds(31);
		allowConnectorToCallUrlDocumentParserOnce();
		connector.refresh();
		context.assertIsSatisfied();
	}
	
	@Test public void testCanAddCruiseLocationsByCommaSeparatedList(){
		String locationList = "http://one.com,http://two.com";
		connector.setCruiseLocationsFromCommaSeparatedString(locationList);
		assertEquals(2, connector.getCruiseLocations().size());
		assertTrue(connector.getCruiseLocations().contains("http://one.com"));
		assertTrue(connector.getCruiseLocations().contains("http://two.com"));
	}

	private void allowConnectorToCallUrlDocumentParserOnce() throws Exception{
		context.checking(new Expectations() {{
			exactly(CRUISE_LOCATIONS.length).of(parser).parse(with(any(URL.class)));
		}});
	}
	

	
	

}
