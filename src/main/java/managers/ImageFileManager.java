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
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import com.drew.imaging.ImageProcessingException;

import model.ExifInfo;
import model.ImageFile;
import utility.ExifInfoUtility;
import utility.LocalDateTimeUtility;

public class ImageFileManager {

	public static List<ImageFile> createImagesFromDirectory(File directory) throws IOException {
		// creates a list of ImageFiles from a single directory
		List<ImageFile> images = new ArrayList<>();

		Stream<Path> files = Files.walk(directory.toPath());
		files.forEach((path) -> {
			final File file = path.toFile();
			if (!file.isDirectory()) {
				if (getExtension(file) == JPG) {
					images.add(createImageFile(file));
				}
			}
		});

		files.close();

		return images;
	}

	public static ImageFile createImageFile(File file) {
		// creates a single Image File from a file
		try {
			ImageFile imageFile = new ImageFile().setAbsoluteFilePath(file.getAbsolutePath())
					.setFileName(file.getName()).setExifInfo(ImageFileManager.createExifInfo(file));
			return imageFile;
		} catch (ImageProcessingException e) {
			final String errorMessage = String.format("Reading Metadata of [%s] failed. File read from location [%s]",
					file.getName(), file.getAbsolutePath());
			System.err.println(errorMessage);
			e.printStackTrace();
			ImageFile imageFile = new ImageFile().setAbsoluteFilePath(file.getAbsolutePath())
					.setFileName(file.getName()).setExifInfo(null);
			return imageFile;
		} catch (IOException e) {
			final String errorMessage = String.format("IOError occurred while reading file [%s] from location [%s]",
					file.getName(), file.getAbsolutePath());
			System.err.println(errorMessage);
			e.printStackTrace();
			ImageFile imageFile = new ImageFile().setAbsoluteFilePath(null).setFileName(null).setExifInfo(null);
			return imageFile;
		}
	}

	public static ExifInfo createExifInfo(File file) throws ImageProcessingException, IOException {
		HashMap<String, String> fileMetadata = null;

		try {
			fileMetadata = ExifInfoUtility.getMetadata(file);
		} catch (ImageProcessingException e) {
			// TODO: handle exception
		}

		if (fileMetadata == null) {
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
