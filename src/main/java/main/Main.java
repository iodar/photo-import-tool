package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
	
	private static Logger logger = LogManager.getLogger(Main.class);
	
	public static void main(String[] args) throws InterruptedException {
		logger.info("Starting app ...");
		logger.info("Wil sleep for 2 secs ...");
		Thread.sleep(2000);
		logger.info("Shutting down ...");
		logger.error("Just a prank (:");
		try {
			throw new RuntimeException("Something happend");
		} catch (RuntimeException e) {
			logger.error("whoops", e);
		}
	}
}
