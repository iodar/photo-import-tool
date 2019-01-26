package managers;

import static enums.ExifIFD0Info.DATE_TIME;
import static enums.ExifIFD0Info.MAKE;
import static enums.ExifIFD0Info.MODEL;
import static enums.FileExtension.JPG;
import static utility.FileUtility.getExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.drew.imaging.ImageProcessingException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import model.ExifInfo;
import model.ImageFile;
import utility.ExifInfoUtility;
import utility.LocalDateTimeUtility;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageFileManager {

	private static Logger logger = LogManager.getLogger(ImageFileManager.class);

	public static List<ImageFile> createImagesFromDirectory(File directory) throws IOException {
		// get files
		List<File> files = getFilesOfDirectory(directory);
		// creates a list of ImageFiles from a single directory
		List<ImageFile> images = new ArrayList<>();
		
		files.parallelStream().forEach(file -> {
			if (!file.isDirectory() && getExtension(file) == JPG) {
				images.add(createImageFile(file));
			}
		});

		return images;
	}
	
	private static List<File> getFilesOfDirectory(File directory) throws IOException {
		List<File> files = new ArrayList<>();
		
		Stream<Path> paths = Files.walk(directory.toPath());
		paths.parallel().forEach(path -> {
			final File file = path.toFile();
			files.add(file);
		});
		paths.close();
		
		return files;
	}

	public static ImageFile createImageFile(File file) {
		// creates a single Image File from a file
		try {
			final ImageFile imageFile = new ImageFile().setAbsoluteFilePath(file.getAbsolutePath()).setFileName(file.getName())
					.setExifInfo(ImageFileManager.createExifInfo(file));
			logger.info(String.format("Successfully created image file of [%s]", file.getName()));
			return imageFile; 
		} catch (ImageProcessingException e) {
			final String errorMessage = String.format("Reading Metadata of [%s] failed. File read from location [%s]",
					file.getName(), file.getAbsolutePath());
			logger.error(errorMessage, e);
			return new ImageFile().setAbsoluteFilePath(file.getAbsolutePath()).setFileName(file.getName())
					.setExifInfo(null);
		} catch (IOException e) {
			final String errorMessage = String.format("IOError occurred while reading file [%s] from location [%s]",
					file.getName(), file.getAbsolutePath());
			logger.error(errorMessage, e);
			return new ImageFile().setAbsoluteFilePath(null).setFileName(null).setExifInfo(null);
		}
	}

	public static ExifInfo createExifInfo(File file) throws ImageProcessingException, IOException {
		Map<String, String> fileMetadata = null;

		try {
			fileMetadata = ExifInfoUtility.getMetadata(file);
		} catch (ImageProcessingException e) {
			logger.error(String.format("Reading Metadata of file [%s] failed", file.getName()), e.getMessage());
		}

		if (fileMetadata == null) {
			logger.warn(String.format("Metadata of file [%s] is null", file.getName()));
			return null;
		} else {
			return new ExifInfo().setMake(fileMetadata.get(MAKE.toString()))
					.setModel(fileMetadata.get(MODEL.toString())).setDateTime(ImageFileManager
							.getLocalDateFromStringWithExifFormat(fileMetadata.get(DATE_TIME.toString())));
		}

	}

	private static LocalDateTime getLocalDateFromStringWithExifFormat(String dateTimeAsString) {
		return LocalDateTimeUtility.fromString(dateTimeAsString, "yyyy:MM:dd HH:mm:ss");
	}
}
