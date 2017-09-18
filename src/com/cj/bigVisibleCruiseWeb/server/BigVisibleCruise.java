package com.cj.bigVisibleCruiseWeb.server;

import java.net.URL;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class BigVisibleCruise{
	private static final Logger logger = Logger.getLogger(BigVisibleCruise.class.getName());
	private static final URL WEB_CONTENT = BigVisibleCruise.class.getClassLoader().getResource("WebContent");
	
	public static void main(String[] args) throws Exception{
		String warUrlString = WEB_CONTENT.toExternalForm();
		
		MainOptions options = new MainOptions(args);
		Server server = new Server(options.getPort());
		
		System.setProperty("bigVisibleCruise.cruiseFeeds", options.getCruiseFeedsAsCommaSeparatedString());

		logger.debug("Web Content: "+warUrlString);
		
		WebAppContext context = new WebAppContext(warUrlString, "/");
		context.setDescriptor("/WEB-INF/web.xml");
		context.setResourceBase(warUrlString);
		context.setParentLoaderPriority(true); 
		server.setHandler(context);
		logger.debug("Starting");
		server.start();
		logger.debug("Started");
		server.join();
	}
}
