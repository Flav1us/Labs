package main;

import org.apache.logging.log4j.*;

public class Log {
	public static void log(String loggerName, String message) {
		Logger logger;
		if(LogManager.exists(loggerName)) {
			logger = LogManager.getLogger(loggerName);
		}
		else {
			logger = LogManager.getRootLogger();
			logger.info("ACHTUNG! Logger with name \"" + loggerName + "\" was not found at " + System.currentTimeMillis() + "ms" );
		}
		logger.info(message);
	}
	
	
	public static void log(String message) {
		Logger logger = LogManager.getRootLogger();
		logger.info(message);
	}
}
