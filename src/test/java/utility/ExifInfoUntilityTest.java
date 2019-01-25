package utility;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.drew.imaging.ImageProcessingException;

public class ExifInfoUntilityTest {

	private final File TESTDATA_DIR = new File("src/test/data");
	private File testfile;

	@Before
	public void setUp() {
		testfile = new File(TESTDATA_DIR.getAbsolutePath() + "/noPictureFile.txt");
	}

	@Test(expected = ImageProcessingException.class)
	public void readingExifInfoOfNonPictureFile_shouldThrowException() throws ImageProcessingException, IOException {
		ExifInfoUtility.getMetadata(testfile);
	}

}
