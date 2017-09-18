package com.cj.bigVisibleCruiseWeb.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.list.UnmodifiableList;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.util.StringUtils;

public class CruiseConnector {
	private final Logger logger = Logger.getLogger(getClass());
	private String[] cruiseLocations = new String[0];
	private static final Integer BUILD_SERVER_TIMEOUT_SECONDS=10;
	private UrlDocumentParser parser;
	public void setParser(UrlDocumentParser parser){this.parser=parser;}
	
	public List<BuildStatus> getBuildStatuses() {
		List<BuildStatus> stats = new ArrayList<BuildStatus>();
		for(String currentCruiseLocation:cruiseLocations){
			logger.debug("Connecting To Server"+currentCruiseLocation);
			stats.addAll(getBuildStatusForCruiseLocation(currentCruiseLocation));
		}
		return stats;
	}

	public List<BuildStatus> getBuildStatusForCruiseLocation(final String cruiseLocation) {
		final List<BuildStatus> stats = new ArrayList<BuildStatus>();
		try{
			new FunctionCallWithTimeout(BUILD_SERVER_TIMEOUT_SECONDS){@Override protected void function() {
				try{
					Document statsDom = parser.parse(new URL(cruiseLocation));
					List<Element> list = statsDom.selectNodes( "//Projects/Project" );
					for(Element element:list){
						BuildStatus build = new BuildStatus();
						build.setStatus(element.attributeValue("lastBuildStatus"));
						build.setName(element.attributeValue("name"));
						build.setActivity(element.attributeValue("activity"));
						build.setWebUrl(element.attributeValue("webUrl"));
						build.setLastBuildLabel(element.attributeValue("lastBuildLabel"));
						stats.add(build);
					}
				}catch(Exception e){
					logger.error("Trouble Connecting To Cruise Server "+cruiseLocation+" "+e);
					throw new RuntimeException(e);
				}
			}};
		}catch (Exception e){
			logger.error("Timeout Connecting To Cruise Server "+cruiseLocation+" "+e);
		}
		
		return stats;
	}
	
	public void setCruiseLocations(List<String> list){
		this.cruiseLocations =  list.toArray(new String[0]);
	}
	
	protected List<String> getCruiseLocations(){
		return Collections.unmodifiableList(Arrays.asList(cruiseLocations));
	}

	public void setCruiseLocationsFromCommaSeparatedString(String locationList) {
		logger.warn("Adding the following servers: "+locationList);
		this.setCruiseLocations(Arrays.asList(StringUtils.commaDelimitedListToStringArray(locationList)));
	}
}
