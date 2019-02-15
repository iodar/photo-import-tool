package utility;

import com.diffplug.common.base.Errors;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import enums.ExifIFD0Info;
import enums.MetadataDirectoryNames;
import exceptions.UnsupportedDateFormatException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import exceptions.NoMetadataException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExifInfoUtility {

    public static final String SUPPORTED_DATETIME_FORMAT = "[\\d]{4}[:]{1}[\\d]{2}[:]{1}[\\d]{2}[\\x20]{1}([\\d]{2}[:]{1}){2}[\\d]{2}";

    /**
     * Reads metadata from the supplied file. Reads only the necessary metadata that
     * is needed to sort all files by date.
     *
     * @param file File of which the metadata is read
     * @return Metadata of file as HashMap
     * @throws ImageProcessingException Thrown if the file on which the extraction
     *                                  of exif info is performed is not a image
     *                                  file
     * @throws IOException              Thrown when reading the file went wrong
     * @throws NoMetadataException
     */
    public static Map<String, String> getMetadata(File file) throws ImageProcessingException, IOException, NoMetadataException, UnsupportedDateFormatException {
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        HashMap<String, String> metadataHashMap = new HashMap<>();

        /*
        TODO: all this below needs refactoring
        without check if tag is date time UnsupportedDateFormatException is thrown
        every time because anything else does not match the date pattern
        */
        if (!containsExifInfo(metadata)) {
            throw new NoMetadataException(
                    String.format("Metadata of file [%s] could not been read or was null", file.getName()));
        } else {
            metadata.getDirectories().forEach(dir -> dir.getTags().forEach(Errors.rethrow().wrap(tag -> {
                if (isExifInfoGroup(dir)) {
                    if (isDateTimeTag(tag)) {
                        if (!matchesSupportedDateFormat(tag)) {
                            throw new UnsupportedDateFormatException("Supplied DateTime format is not supported");
                        } else if (isBlank(tag)) {
                            throw new NoMetadataException(String.format("Metadata properties of file [%s] were empty", file.getName()));
                        } else {
                            metadataHashMap.put(tag.getTagName(), tag.getDescription());
                        }
                    }
                }
            })));
        }

        return metadataHashMap;
    }

    private static boolean isDateTimeTag(Tag tag) {
        return tag.getTagName().equals(ExifIFD0Info.DATE_TIME.toString());
    }

    private static boolean isExifInfoGroup(Directory dir) {
        return dir.getName().equals(MetadataDirectoryNames.EXIF_INFO.toString());
    }

    private static boolean containsExifInfo(Metadata metadata) {
        return metadata.containsDirectoryOfType(ExifIFD0Directory.class);
    }

    private static boolean isBlank(Tag tag) {
        return StringUtils.isBlank(tag.getDescription());
    }

    private static boolean matchesSupportedDateFormat(Tag tag) {
        return tag.getDescription().matches(SUPPORTED_DATETIME_FORMAT);
    }


}
