package com.cj.bigVisibleCruiseWeb.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

public class CachingCruiseConnector extends CruiseConnector {
	private Logger logger = Logger.getLogger(getClass());
	DateTime lastRefreshTime=null;
	List<BuildStatus> buildStatuses=null;
	
	@Override
	public List<BuildStatus> getBuildStatuses() {
		if(buildStatuses==null){
			refresh();
		}
		return buildStatuses;
	}

	public void refresh() {
		logger.info("Refreshing CachingCruiseConnector Cache.");
		buildStatuses= super.getBuildStatuses();
		lastRefreshTime=new DateTime();
	}

}
