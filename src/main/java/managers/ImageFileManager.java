package managers;

import com.drew.imaging.ImageProcessingException;
import enums.MetadataStatus;
import exceptions.UnsupportedDateFormatException;
import factory.ImageFileFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import model.ExifInfo;
import model.ImageFile;
import exceptions.NoMetadataException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utility.ExifInfoUtility;
import utility.FileUtility;
import utility.LocalDateTimeUtility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static enums.ExifIFD0Info.*;
import static enums.FileExtension.JPG;
import static enums.MetadataStatus.NOT_READABLE;
import static enums.MetadataStatus.OK;
import static utility.FileUtility.getExtension;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageFileManager {

    private static final String EXCEPTION_MESSAGE_FORMAT = "%s: %s";
    private static Logger logger = LogManager.getLogger(ImageFileManager.class);

    public static List<ImageFile> createImagesFromDirectory(File directory) throws IOException {
        // get files
        List<File> files = FileUtility.getFilesOfDirectory(directory);
        // creates a list of ImageFiles from a single directory
        List<ImageFile> images = new ArrayList<>();

        files.parallelStream().forEach(file -> {
            if (!file.isDirectory() && getExtension(file) == JPG) {
                images.add(createImageFile(file));
                logger.info(String.format("Metadata of file [%s] successfully read", file.getName()));
            }
        });

        return images;
    }

    public static ImageFile createImageFile(File file) {
        try {
            return ImageFileFactory.createImageFile(file.getName(), file.getAbsolutePath(), null,
                    OK, createExifInfo(file));
        } catch (ImageProcessingException imageProcessingException) {
            logger.error(String.format(EXCEPTION_MESSAGE_FORMAT, imageProcessingException.getClass().getName(),
                    imageProcessingException.getMessage()));

            return ImageFileFactory.createImageFileWithNoMetadataStatus(file.getName(), file.getAbsolutePath(),
                    imageProcessingException.getMessage());
        } catch (IOException ioException) {
            logger.error(String.format(EXCEPTION_MESSAGE_FORMAT, ioException.getClass().getName(),
                    ioException.getMessage()));

            return ImageFileFactory.createImageFileWithNotReadableMetadataStatus(file.getName(), file.getAbsolutePath(),
                    ioException.getMessage());
        } catch (NoMetadataException noMetadataException) {
            logger.error(String.format(EXCEPTION_MESSAGE_FORMAT, noMetadataException.getClass().getName(),
                    noMetadataException.getMessage()));

            return ImageFileFactory.createImageFileWithNoMetadataStatus(file.getName(), file.getAbsolutePath(),
                    noMetadataException.getMessage());
        } catch (UnsupportedDateFormatException e) {
            logger.error(String.format(EXCEPTION_MESSAGE_FORMAT, e.getClass().getName(), e.getMessage()));

            return ImageFileFactory.createImageFileWithNotReadableMetadataStatus(file.getName(), file.getAbsolutePath(),
                    e.getMessage());
        }
    }

    private static ExifInfo createExifInfo(File file)
            throws ImageProcessingException, IOException, NoMetadataException, UnsupportedDateFormatException {

        Map<String, String> fileMetadata = ExifInfoUtility.getMetadata(file);
        return new ExifInfo().setMake(fileMetadata.get(MAKE.toString())).setModel(fileMetadata.get(MODEL.toString()))
                .setDateTime(
                        LocalDateTimeUtility.getLocalDateFromStringWithExifFormat(fileMetadata.get(DATE_TIME.toString())));
    }

}
