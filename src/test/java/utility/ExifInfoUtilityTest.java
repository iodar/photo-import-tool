package utility;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.drew.imaging.ImageProcessingException;

import model.exceptions.NoMetadataException;

public class ExifInfoUtilityTest {

	private final File TESTDATA_DIR = new File("src/test/data");
	private File testfile;
	private File imageWithOutExifMetadata;

	@Before
	public void setUp() {
		testfile = new File(TESTDATA_DIR.getAbsolutePath() + "/noPictureFile.txt");
		imageWithOutExifMetadata = new File("src/test/data/DSC_0104.JPG");
	}

	@Test
	public void readingExifInfoOfNonPictureFile_shouldThrowException() throws ImageProcessingException, IOException {
		ImageProcessingException actualException = null;
		
		try {
			ExifInfoUtility.getMetadata(testfile);
		} catch (ImageProcessingException imageProcessingException) {
			actualException = imageProcessingException;
		} catch (NoMetadataException e) {
			
		}
		
		assertThat(actualException, is(notNullValue()));
		assertThat(actualException, is(instanceOf(ImageProcessingException.class)));
		assertThat(actualException.getMessage(), is(notNullValue()));
		
	}

	@Test
	public void readingExifOfJpgWithOutMetadata_shouldThrowNewImageProcessingException() throws Exception {
		NoMetadataException actualException = null;

		final String expectedExceptionMessage = String.format("Metadata of file [%s] could not been read or was null",
				imageWithOutExifMetadata.getName());
		
		try {
			ExifInfoUtility.getMetadata(imageWithOutExifMetadata);
		} catch (NoMetadataException noMetadataException) {
			actualException = noMetadataException;
		}
		
		assertThat(actualException, is(instanceOf(NoMetadataException.class)));
		assertThat(actualException.getMessage(), is(expectedExceptionMessage));
	}
	
}
