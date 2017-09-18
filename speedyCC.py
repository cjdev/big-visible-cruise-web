#!/usr/bin/python -O

from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
from pprint import pprint
from threading import Thread
import time
import urllib2
import traceback

try:
    import json
except ImportError:
    import simplejson as json
global requestCache

##############
#Configuration Options Below
##############
speedyGPath="http://build117.wl.cj.com:8080"
testRunDetailsAction=speedyGPath+"/testRunDetails.html?resource="
wipeoutPath="http://devops101.wl.cj.com:4000/api/pipelines?limit=10"
port = 4480
##############
#End Configuration Options
##############

requestCache = {}

class Handler(BaseHTTPRequestHandler):
    def do_GET(self):
        self.send_response(200)
        self.send_header("Content-type", "text/html")
        self.end_headers()
        self.wfile.write(buildResponse()) #Return 10 second old data.  First 10 seconds are empty.
        #Thread(target=buildResponse).start() #Spawn Refresh thread because SpeedyG is slow and we don't want to wait for it.

def getJson(url):
    global requestCache
    
    if (not url in requestCache):
        requestCache[url] = {"lastRequestTime": 0,  "response":"" }

    if (requestCache[url]["lastRequestTime"] < time.time()-20 ):
        requestCache[url]["lastRequestTime"] = time.time()
        Thread(target=refetch, args=(url,)).start()
        
    
    return requestCache[url]["response"] if url in requestCache else ""

def refetch(url):
    global requestCache
    response = ""
    try:
        response = json.loads(urllib2.urlopen(url).read())        
        print "Fetched "+url
    except Exception:
        ""
    requestCache[url] = {"lastRequestTime": time.time(),  "response":response }
    
def cruisifyBuildState(speedyGState):
    if speedyGState.upper()=="GREEN":
        return "Success"
    if speedyGState.upper()=="RED":
        return "Failure"
    return speedyGState

def cruisifyActivityStatus(speedyGStatus):
    if speedyGStatus.upper()=="RUNNING":
        return "Building"
    return "Sleeping"    

def getSpeedyBuilds():
    returnXml = ""
    allBuildsData = getJson(speedyGPath+'/source')
    for build in allBuildsData:    
        try: 
            buildDataUrl = speedyGPath+build["links"]["lastTestRunDetails"]    
            buildWebUrl = testRunDetailsAction+build["links"]["lastTestRunDetails"]
            singleBuildData =  getJson(buildDataUrl)
            name=singleBuildData["sourceName"]
            lastBuildLabel="?"
            lastBuildTime=singleBuildData["dateFinished"]
            lastBuildStatus=cruisifyBuildState(build["state"])
            activity=cruisifyActivityStatus(singleBuildData["status"])

            buildXml= "<Project "+ \
                'webUrl="'+buildWebUrl+'" '+\
                'name="SpeedyG '+name+'" '+\
                'lastBuildLabel="'+lastBuildLabel+'" '+\
                'lastBuildTime="'+lastBuildTime+'" '+\
                'lastBuildStatus="'+lastBuildStatus+'" '+\
                'activity="'+activity+'" '+\
                "/>"
            returnXml+=buildXml
        except Exception as e:
            traceback.print_exc()
            print "Trouble Loading "+buildDataUrl
    print "returning "+returnXml
    return returnXml  

def getWipeoutBuildStatus(pipelines):
    knownStages = set(["DEPLOY-SCHEMAS-MAIN", "DEPLOY-ALL-MAIN"]) #Initial Whitelist 

    for pipeline in pipelines:
        for stage in pipeline["stages"]:
            if stage["status"] == None:
                "" #Unknown stage
            elif stage["status"].upper() == "SUCCESS":
                #print "Whitelisting "+ stage["name"].upper()
                knownStages.add(stage["name"].upper())
            elif stage["name"].upper() not in knownStages and (stage["status"].upper() == "FAILURE" or stage["status"].upper() == "ABORTED"):
                #print "Non-whitelisted build failed: "+ stage["name"].upper()
                return "Failure"
    return "Success"

def getWipeout():
    ret=""
    try:
        pipelines = getJson(wipeoutPath)
        activity = "Building" if not "status" in pipelines[0] else "Sleeping"
        lastBuildStatus = getWipeoutBuildStatus(pipelines)
        canICheckIn = "May I Check In?  NO!" if lastBuildStatus.upper == "FAILURE" else "May I Check In?  Yes"
        ret= "<Project "+ \
                    'webUrl="http://devops101.wl.cj.com:4000/page/1" '+\
                    'name="Wipeout CJO" '+\
                    'lastBuildLabel="unknown" '+\
                    'lastBuildTime="unknown" '+\
                    'lastBuildStatus="'+lastBuildStatus+'" '+\
                    'activity="'+activity+'" '+\
                    "/>"+\
                    "<Project "+ \
                    'webUrl="http://devops101.wl.cj.com:4000/page/1" '+\
                    'name="'+canICheckIn+'" '+\
                    'lastBuildLabel="unknown" '+\
                    'lastBuildTime="unknown" '+\
                    'lastBuildStatus="'+lastBuildStatus+'" '+\
                    'activity="'+activity+'" '+\
                    "/>"
        
    except Exception:
        traceback.print_exc()
    return ret    

def buildResponse():
    return "<Projects>"+getWipeout()+getSpeedyBuilds()+"</Projects>"

server = HTTPServer(('', port), Handler)
print "Starting Server On Port "+str(port)
server.serve_forever()
