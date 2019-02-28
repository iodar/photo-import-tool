package utility;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import enums.MetadataDirectoryNames;
import exceptions.NoMetadataException;
import factory.ExifInfoDTOFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import model.dto.ExifInfoDTO;

import java.io.File;
import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExifInfoUtility {

    public static final String SUPPORTED_DATETIME_FORMAT = "[\\d]{4}[:][\\d]{2}[:][\\d]{2}[\\x20]([\\d]{2}[:]){2}[\\d]{2}";

    /**
     * Reads metadata from the supplied file. Reads only the necessary metadata that
     * is needed to sort all files by date.
     *
     * @param file File of which the metadata is reads
     * @return Metadata of file as HashMap
     * @throws ImageProcessingException Thrown if the file on which the extraction
     *                                  of exif info is performed is not a image
     *                                  file
     * @throws IOException              Thrown when reading the file went wrong
     * @throws NoMetadataException      Thrown when the there is no exif info the be extracted
     *                                  from the image file
     */
    public static ExifInfoDTO getMetadata(File file) throws ImageProcessingException, IOException, NoMetadataException {
        // read metadata into var
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        final Directory exifInfoDir = extractExifInfoDirIfPresent(metadata);
        if (exifInfoDir != null) {
            return ExifInfoDTOFactory.createDtoFromExifInfoDirectoy(exifInfoDir);
        } else {
            throw new NoMetadataException("No Metadata found.");
        }
    }

    private static Directory extractExifInfoDirIfPresent(final Metadata metadata) {
        if (containsExifInfoType(metadata)) {
            for (Directory directory : metadata.getDirectories()) {
                if (isOfTypeExifInfo(directory)) {
                    return directory;
                }
            }
        }
        return null;
    }

    private static boolean isOfTypeExifInfo(Directory dir) {
        return dir.getName().equals(MetadataDirectoryNames.EXIF_INFO.toString());
    }

    private static boolean containsExifInfoType(Metadata metadata) {
        return metadata.containsDirectoryOfType(ExifIFD0Directory.class);
    }

}
