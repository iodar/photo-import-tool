package utility;

import static enums.MetadataDirectoryNames.EXIF_INFO;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
			metadata.getDirectories().forEach(dir -> dir.getTags().forEach(tag -> {
				if (dir.getName().equals(EXIF_INFO.toString())) {
					metadataHashMap.put(tag.getTagName(), tag.getDescription());
				}
			}));
		}

		return metadataHashMap;
	}
	
	public static class NoMetadataException extends Exception {
		private static final long serialVersionUID = 6859687464263124718L;
		
		public NoMetadataException(String message) {
			super(message);
		}
	}
}
