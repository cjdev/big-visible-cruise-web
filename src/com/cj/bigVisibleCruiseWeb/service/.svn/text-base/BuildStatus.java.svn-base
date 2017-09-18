package com.cj.bigVisibleCruiseWeb.service;

import org.json.JSONObject;

public class BuildStatus {

	private String status;
	private String name;
	private String activity;
	private String webUrl;
	private String lastBuildLabel;

	public void setStatus(String status) {
		this.status=status;
	}

	public void setName(String name) {
		this.name=name;
	}

	public void setActivity(String activity) {
		this.activity=activity;
		
	}

	public JSONObject toJsonObject() {
		
		JSONObject json = new JSONObject();
		try{
			json.put("status", status);
			json.put("name", name);
			json.put("activity", activity);
			json.put("webUrl",webUrl);
			json.put("lastBuildLabel",lastBuildLabel);
		}catch (Exception e){throw new RuntimeException(e);}
		
		return json;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public void setLastBuildLabel(String lastBuildLabel) {
		this.lastBuildLabel = lastBuildLabel;
	}


}
