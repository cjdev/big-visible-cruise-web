package com.cj.bigVisibleCruiseWeb.service;

import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;

import com.cj.bigVisibleCruiseWeb.controller.IndexController;

public class CruiseConnectorTest {
    Mockery context = new Mockery(){{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};;
    CruiseConnector connector;
    UrlDocumentParser parser;
    
    @Before
    public void setUp(){
    	parser = context.mock(UrlDocumentParser.class);
    	connector = new CruiseConnector();
    	connector.setParser(parser);
    }
	
	@Test public void testMainFtIsReturned()throws Exception{
		allowConnectorToCallUrlDocumentParser();
		List <BuildStatus> statuses = connector.getBuildStatuses();
		assertTrue(statuses!=null);
	}

	private void allowConnectorToCallUrlDocumentParser() throws Exception{
		context.checking(new Expectations() {{
			allowing(parser).parse(with(any(URL.class)));
		}});
		
	}
	
	// no assertions, but a sanity check on parsing and JSON marshaling
	public static void main(String[] args) {
		CruiseConnector connector = new CruiseConnector();
		connector.setParser(new UrlDocumentParser());
		System.out.println(new IndexController().toJson(connector.getBuildStatuses()));
	}
}
