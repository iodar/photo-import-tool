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

import enums.MetadataStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import model.ExifInfo;
import model.ImageFile;
import utility.ExifInfoUtility;
import utility.ExifInfoUtility.NoMetadataException;
import utility.LocalDateTimeUtility;
import utility.LocalDateTimeUtility.UnsupportedDateStringException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageFileManager {

	private static final String EXCEPTION_MESSAGE_FORMAT = "%s: %s";
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
		try {
			return new ImageFile().setFileName(file.getName()).setAbsoluteFilePath(file.getAbsolutePath())
					.setMetadataStatus(MetadataStatus.OK).setExifInfo(createExifInfo(file));
		} catch (ImageProcessingException imageProcessingException) {
			logger.error(String.format(EXCEPTION_MESSAGE_FORMAT, imageProcessingException.getClass().getName(),
					imageProcessingException.getMessage()));
			
			return new ImageFile().setFileName(file.getName()).setAbsoluteFilePath(file.getAbsolutePath())
					.setMetadataStatus(MetadataStatus.NO_DATA).setExifInfo(null);
		} catch (IOException ioException) {
			logger.error(String.format(EXCEPTION_MESSAGE_FORMAT, ioException.getClass().getName(), ioException.getMessage()));
			
			return new ImageFile().setFileName(file.getName()).setAbsoluteFilePath(file.getAbsolutePath())
					.setMetadataStatus(MetadataStatus.NO_DATA).setExifInfo(null);
		} catch (UnsupportedDateStringException unsupportedDateStringException) {
			logger.error(String.format(EXCEPTION_MESSAGE_FORMAT, unsupportedDateStringException.getClass().getName(),
					unsupportedDateStringException.getMessage()));
			
			return new ImageFile().setFileName(file.getName()).setAbsoluteFilePath(file.getAbsolutePath())
					.setMetadataStatus(MetadataStatus.NO_DATA).setExifInfo(null);
		}
	}

	public static ExifInfo createExifInfo(File file) throws ImageProcessingException, IOException, UnsupportedDateStringException {
		Map<String, String> fileMetadata = null;

		try {
			fileMetadata = ExifInfoUtility.getMetadata(file);
			return new ExifInfo().setMake(fileMetadata.get(MAKE.toString()))
					.setModel(fileMetadata.get(MODEL.toString())).setDateTime(ImageFileManager
							.getLocalDateFromStringWithExifFormat(fileMetadata.get(DATE_TIME.toString())));
		} catch (NoMetadataException metadataException) {
			logger.error(String.format(EXCEPTION_MESSAGE_FORMAT, metadataException.getClass().getName(),
					metadataException.getMessage()));
			return null;
		}

	}

	private static LocalDateTime getLocalDateFromStringWithExifFormat(String dateTimeAsString)
			throws UnsupportedDateStringException {
		return LocalDateTimeUtility.fromString(dateTimeAsString, "yyyy:MM:dd HH:mm:ss");
	}
}
