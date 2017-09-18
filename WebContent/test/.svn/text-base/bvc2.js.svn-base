
function loadPage(){
	$.get("./resources/testCruiseOutputXml.xml", function(data){
  		alert("Data Loaded: " + data);
	});

	//Go Again in 30 seconds...
	setTimeout('loadPage()', 30000)
	
}


function handleNewData(builds){
	var pageData="";
	for(var i=0; i<builds.size(); i++){
		var build = builds[i];
		var status;
		if (build.activity=='Sleeping'){
			status=build.status;
		}else{
			status=build.activity;		
		}
		pageData += "<a href='"+build.webUrl+"'>"
		pageData += "<div class='"+status+"'>";
		pageData += build.name;
		pageData += "</div>";
		pageData += "</a>";
	}
	$('buildStatusContainer').innerHTML=pageData;
}

