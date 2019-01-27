package utility;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import com.drew.imaging.ImageProcessingException;

public class ExifInfoUtilityTest {

	private final File TESTDATA_DIR = new File("src/test/data");
	private File testfile;
	private File imageWithOutExifMetadata;

	@Before
	public void setUp() {
		testfile = new File(TESTDATA_DIR.getAbsolutePath() + "/noPictureFile.txt");
		imageWithOutExifMetadata = new File(TESTDATA_DIR.getAbsolutePath() + "/DSC_0104.jpg");
	}

	@Test
	public void readingExifInfoOfNonPictureFile_shouldThrowException() throws ImageProcessingException, IOException {
		ImageProcessingException actualException = null;
		
		try {
			ExifInfoUtility.getMetadata(testfile);
		} catch (ImageProcessingException imageProcessingException) {
			actualException = imageProcessingException;
		}
		
		assertThat(actualException, is(notNullValue()));
		assertThat(actualException, is(instanceOf(ImageProcessingException.class)));
		assertThat(actualException.getMessage(), is(notNullValue()));
		
	}

	@Test
	public void readingExifOfJpgWithOutMetadata_shouldThrowNewImageProcessingException() throws Exception {
		final String expectedExceptionMessage = String.format("Metadata of file [%s] could not been read or was null",
				imageWithOutExifMetadata.getName());
		ImageProcessingException actualException = null;
		try {
			@SuppressWarnings("unused")
			Map<String, String> info =  ExifInfoUtility.getMetadata(imageWithOutExifMetadata);
		} catch (ImageProcessingException imageProcessingException) {
			actualException = imageProcessingException;
		}
		
		assertThat(actualException, is(notNullValue()));
		assertThat(actualException, is(instanceOf(ImageProcessingException.class)));
		assertThat(actualException.getMessage(), is(expectedExceptionMessage));
	}

}
