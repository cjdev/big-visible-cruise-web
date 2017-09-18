package com.cj.bigVisibleCruiseWeb.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Test;

import com.cj.bigVisibleCruiseWeb.service.BuildStatus;

public class IndexControllerTest {

	@Test public void testToJson() throws Exception{
		List<BuildStatus> statuses = new ArrayList<BuildStatus>();
		BuildStatus s = new BuildStatus();
		s.setName("statusName");
		s.setActivity("Building");
		s.setLastBuildLabel("99999");
		//s.setLastBuildTime("2009-03-03T15:53:14");
		s.setStatus("Success");
		statuses.add(s);
		
		IndexController controller = new IndexController();
		JSONObject json = controller.toJson(statuses).getJSONObject(0);
		assertEquals("statusName",json.get("name"));
		assertEquals("Building",json.get("activity"));
		assertEquals("99999",json.get("lastBuildLabel"));
		//assertEquals("2009-03-03T15:53:14",json.get("lastBuildTime"));
		//assertEquals("15:53",json.get("lastBuildTimeShort"));
		assertEquals("Success",json.get("status"));
		//assertEquals("",json.get("nextBuildTime"));
		//assertEquals("",json.get("nextBuildTimeShort"));
		//assertEquals("Building CL 99999 since 15:53 2009-03-03",json.get("toolTip"));
		
		s.setActivity("Sleeping");
		json = controller.toJson(statuses).getJSONObject(0);
		//assertEquals("Built CL 99999 at 15:53 2009-03-03",json.get("toolTip"));
		
		//s.setNextBuildTime("2009-03-03T16:53:34");
		s.setActivity("Sleeping");
		json = controller.toJson(statuses).getJSONObject(0);
		//assertEquals("2009-03-03T16:53:34",json.get("nextBuildTime"));
		//assertEquals("16:53",json.get("nextBuildTimeShort"));
		//assertEquals("Built CL 99999 at 15:53 2009-03-03, next build at 16:53",json.get("toolTip"));
		
	}
}
