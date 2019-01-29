package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;

import managers.ImageFileManager;
import model.ImageFile;
import model.StopWatch;
import model.StopWatch.NotStartedException;
import model.StopWatch.NotStoppedException;

public class Main {

	private static Logger logger = LogManager.getLogger(Main.class);
	private static File rootDir = new File("N:\\Fotos\\Fotos Dario");

	public static void main(String[] args) throws ImageProcessingException, IOException {
		dirtyTestApp();
//		testMetadataReader();
	}

	private static void dirtyTestApp() {
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

		try {
			stopWatch.stop();
			logger.info(stopWatch.getDifferenceMessage());
		} catch (NotStartedException notStartedException) {
			logger.error(notStartedException.getMessage());
		} catch (NotStoppedException notStoppedException) {
			logger.error(notStoppedException.getMessage());
		}
	}

	private static void testMetadataReader() throws ImageProcessingException, IOException {
		Metadata metadata = ImageMetadataReader.readMetadata(new File("src/test/data/DSC_0001.JPG"));

		metadata.getDirectories().forEach(dir -> dir.getTags().forEach(tag -> logger
				.info(String.format("%s: %s [%s]", dir.getName(), tag.getTagName(), tag.getDescription()))));
	}
}
