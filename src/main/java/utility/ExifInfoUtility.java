package utility;

import static enums.MetadataDirectoryNames.EXIF_INFO;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class ExifInfoUtility {

	/**
	 * Reads metadata from the supplied file. Reads only the necessary metadata
	 * that is needed to sort all files by date.
	 * 
	 * @param file File of which the metadata is read
	 * @return Metadata of file as HashMap
	 * @throws ImageProcessingException Thrown if the file on which the extraction of exif info is performed
	 * is not a image file
	 * @throws IOException Thrown when reading the file went wrong
	 */
	public static HashMap<String, String> getMetadata(File file) throws ImageProcessingException, IOException {
		Metadata metadata = ImageMetadataReader.readMetadata(file);
		HashMap<String, String> metadataHashMap = new HashMap<>();
		
		for (Directory directory : metadata.getDirectories()) {
			for (Tag tag : directory.getTags()) {
				if (directory.getName() == EXIF_INFO.toString()) {
					if (tag.getDescription() == null || tag.getDescription() == "") {
						throw new ImageProcessingException(String.format("Tag [%s] was [\" \"] or [null]", tag.getTagName()));
					} else {
						metadataHashMap.put(tag.getTagName(), tag.getDescription());
					}
				}
			}
		}
		return metadataHashMap;
		
	}

}
