package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import managers.ImageFileManager;
import model.ImageFile;
import model.StopWatch;
import model.StopWatch.NotStartedException;
import model.StopWatch.NotStoppedException;

public class Main {
	
	private static Logger logger = LogManager.getLogger(Main.class);
	private static File rootDir = new File("N:\\Fotos\\Unsortiert\\Bilder Bremen 2018-2019");
	
	public static void main(String[] args) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		logger.info("Starting app ...");
		logger.info(String.format("Reading all files at [%s]", rootDir.getName()));
		List<ImageFile> imageFiles = new ArrayList<>();
		try {
			imageFiles = ImageFileManager.createImagesFromDirectory(rootDir);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
		if (!imageFiles.isEmpty()) {
			logger.info(String.format("[%s] images imported", imageFiles.size()));
		}
		stopWatch.stop();
		try {
			logger.info(stopWatch.getDifferenceMessage());
		} catch (NotStartedException notStartedException) {
			logger.error(notStartedException.getMessage());
		} catch (NotStoppedException notStoppedException) {
			logger.error(notStoppedException.getMessage());
		}
	}
}
