package com.cj.bigVisibleCruiseWeb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.cj.bigVisibleCruiseWeb.service.BuildStatus;
import com.cj.bigVisibleCruiseWeb.service.CruiseConnector;

//http://localhost:8080/BI/cruise/servlet/index
public class IndexController implements Controller {

	public CruiseConnector cruiseConnector;

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.getOutputStream().print(toJson(cruiseConnector.getBuildStatuses()).toString());
		return null;
	}
	
	public void setCruiseConnector(CruiseConnector connector){
		this.cruiseConnector=connector;
	}

	public JSONArray toJson(List<BuildStatus> statuses) {
		JSONArray a = new JSONArray();
		
		for(BuildStatus buildStatus:statuses){
			a.put(buildStatus.toJsonObject());
		}
		
		return a;
	}
}