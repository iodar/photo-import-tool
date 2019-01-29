package utility;

import static enums.MetadataDirectoryNames.EXIF_INFO;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.diffplug.common.base.Errors;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;

import enums.MetadataDirectoryNames;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import model.exceptions.NoMetadataException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExifInfoUtility {

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
	public static Map<String, String> getMetadata(File file) throws ImageProcessingException, IOException, NoMetadataException {
		Metadata metadata = ImageMetadataReader.readMetadata(file);
		HashMap<String, String> metadataHashMap = new HashMap<>();

		if (!metadata.containsDirectoryOfType(ExifIFD0Directory.class)) {
			throw new NoMetadataException(
					String.format("Metadata of file [%s] could not been read or was null", file.getName()));
		} else {
			metadata.getDirectories().forEach(dir -> dir.getTags().forEach(Errors.rethrow().wrap(tag -> {
				if (dir.getName().equals(MetadataDirectoryNames.EXIF_INFO.toString())) {
					if (StringUtils.isBlank(tag.getDescription())) {
						throw new NoMetadataException(String.format("Metadata properties of file [%s] were empty", file.getName()));
					} else {
						metadataHashMap.put(tag.getTagName(), tag.getDescription());
					}
				}
			})));
		}

		return metadataHashMap;
	}

}
