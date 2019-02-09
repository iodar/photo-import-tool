package utility;

import enums.FileExtension;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtility {

	public static FileExtension getExtension(File file) {
		if (getExtensionOfFile(file).equalsIgnoreCase("jpg")) {
			return FileExtension.JPG;
		} else {
			return FileExtension.NONE;
		}
	}
	
	private static String getExtensionOfFile(File file) {
		return FilenameUtils.getExtension(file.getName());
	}
}
