package utility;

import enums.FileExtension;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static List<File> getFilesOfDirectory(File directory) throws IOException {
        List<File> files;

        try (Stream<Path> pathStream = Files.walk(directory.toPath())) {
            files = pathStream.map(Path::toFile).collect(Collectors.toList());
        }

        return files;
    }
}
