package com.cj.bigVisibleCruiseWeb.server;

import java.util.List;

import org.springframework.util.StringUtils;

import static java.util.Arrays.asList;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class MainOptions {
	OptionSet options;

	public MainOptions(String[] args) {
		OptionParser parser = new OptionParser(){{
			acceptsAll(asList("port", "p" ))
				.withRequiredArg()
				.ofType( Integer.class )
				.describedAs( "The Server Port" )
				.defaultsTo( 8080 );
			acceptsAll(asList("f", "feed"))
				.withRequiredArg()
				.ofType(String.class)
				.describedAs("The URL to a cruise.xml feed including the 'http://'.  Multiple occurances are supported.")
				.defaultsTo("http://localhost/cruise.xml");
		}};
		
		options = parser.parse( args );
	}
	
	public Integer getPort() {
		return Integer.valueOf(options.valueOf("p").toString());
	}

	@SuppressWarnings("unchecked")
	public List<String> getCruiseFeeds() {
		return (List<String>)options.valuesOf("f");
	}

	public String getCruiseFeedsAsCommaSeparatedString() {
		return StringUtils.collectionToCommaDelimitedString(getCruiseFeeds());
	}

}
