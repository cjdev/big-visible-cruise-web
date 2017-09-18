var builds = new Array();
var showHiddenBuilds=false;
var store = new Persist.Store('BigVisibleCruise');
var filter=null;

//Called when we first start up...
function loadPage(){
	loadState();
	refreshPage();
}


//Called Every 30 Seconds.
function refreshPage(){
	new Ajax.Request("./cruise/servlet/index", {
	//new Ajax.Request("./resources/testCruiseOutput", {
		method: 'get',
		onSuccess: function(transport) {
			var rawBuildData = transport.responseText.evalJSON(true);
			builds = makeBuilds(rawBuildData, builds);
			redrawPage();
		}
	});
	//Go Again in 30 seconds...
	setTimeout('refreshPage()', 30000)
}

function querySt(key) {
	var strQueryString = window.location.search.substring(1);
	var queryStringArray = strQueryString.split("&");
	for (i=0;i<queryStringArray.length;i++) {
		var field = queryStringArray[i].split("=");
		if (field[0] == key) {
			return field[1];
		}
	}
}
	
function loadState(){
	store.get('storedBuilds', function(ok, val) {
    	if (ok && val!=null) builds = (val+" ").evalJSON(true);
    });
    store.get('storedShowHiddenBuilds', function(ok, val) {
    	if (ok && val!=null) showHiddenBuilds = (val+" ").evalJSON(true);
    });    
}

function saveState(){
	store.set('storedBuilds', Object.toJSON(builds));
	store.set('storedShowHiddenBuilds', Object.toJSON(showHiddenBuilds));
}

function forgetState(){
	
	if (confirm ("Will forget your hidden builds and other saved data.  Are you sure?")){
		store.remove('storedBuilds');
		store.remove('storedShowHiddenBuilds');
	}
}


function toggleBuild(buildName){
	var build = builds.get(buildName); 
	toggleBuildMenu(buildName);
	build.hidden = build.hidden ? false : true;
	redrawPage();

}

function toggleBuildMenu(buildName){
	Effect.toggle('menu_'+buildName, 'slide', { duration: 0.15 });
}

function toggleShowHideBuilds(){
	showHiddenBuilds = showHiddenBuilds ? false : true;
	redrawPage();
}

function redrawPage(){
	$('buildStatusContainer').innerHTML=builds.drawBuilds();
}


/********************************* Build Array Factory *************/
function makeBuilds(rawBuildData, builds){


	builds.drawBuilds = function(){
		var pageData="";
		for(var i=0; i<builds.size(); i++){
			var build = builds[i];
			pageData += build.drawBuild();
		}
		return pageData;
	};

	builds.get = function(buildName){
		//alert("finding "+buildName);
		return builds.find(function(build) {
 			return build.name == buildName;
		});
	}

	builds.contains = function(buildName){
		return builds.get(input)!=undefined;
	};
	

	for(var i=0; i<rawBuildData.size(); i++){
		makeBuild(rawBuildData[i]);
	}

	return builds;

	/*********** Build Object Factory *************************/
	function makeBuild(inputBuildFromJson){
	
		var buildName = inputBuildFromJson.name;
		var build = builds.get(buildName);
		
		if(build==undefined){
			build=new Object();
			build.hidden=false;
			build.name=buildName;
			builds.push(build);
			builds.sort(sortByName);
		}
		
		function sortByName(a,b) {
		    var a = a.name;
		    var b = b.name;
		    return ((a < b) ? -1 : ((a > b) ? 1 : 0));
		}

		//will overwrite the data in build with the new data from json.
		Object.extend(build, inputBuildFromJson);
		build.drawBuild=function(){
			var pageData="";
			if (buildNotHidden(build)){
				pageData += "<div class='"+build.getClasses()+"'>";
				pageData += "   <span onclick=\"window.open('"+build.webUrl+"')\" class='buildName'>"+build.name+"</span>";
				pageData += "	<div class='buildMenuButton' onclick=\"toggleBuildMenu('"+build.name+"')\" >&#8595;</div>";
				pageData += "   <div id='menu_"+build.name+"' class='buildMenu' style='display:none'><div>";
				pageData += "      <span class='buildMenuOption' onclick='toggleBuild(\""+build.name+"\")'>Show/Hide This Build</span><br>";
				pageData += "   </div></div>";
				pageData += "</div>";
			}
			return pageData
		};

		function buildNotHidden(build){
			var ret=true;
			if(build.hidden && !showHiddenBuilds){
				ret=false;
			}
			if(filter!=null){
				var filterExpression = new RegExp(filter);
				if(!filterExpression.exec(build.name)){
					ret=false;
				}
			}
			return ret;
		}

		build.getClasses=function(){
			var buildClasses = "buildBlock help "+build.status+" "+build.activity;
			if (build.hidden) buildClasses +=" buildHidden";
			return buildClasses
		}

		//build.drawBuild
		build.menuHidden=true;
		return build;
	};
}