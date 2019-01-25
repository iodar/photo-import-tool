package managers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.ExifInfo;
import model.ImageFile;

public class ImageFileManagerTest {

	private final File TESTDATA_DIR = new File("src/test/data");
	private File picture;
	private File directoryWithPictures;
	private File testfile;
	
	@Before
	public void setUp() {
		picture = new File(TESTDATA_DIR.getAbsolutePath() + "/DSC_0001.JPG");
		directoryWithPictures = 
				new File(TESTDATA_DIR.getAbsolutePath() + "/testdataWithDirsAndPics/");
		testfile = new File(TESTDATA_DIR.getAbsolutePath() + "/DSC_0001.JPG");
	}
	
	@Test
	public void methodForCreatingImageFile_shouldReturnImageFileWithExifData() throws Exception {
		ImageFile image = ImageFileManager.createImageFile(picture);
		
		assertThat(image.getFileName(), equalTo("DSC_0001.JPG"));
		assertThat(image.getAbsoluteFilePath(), equalTo(picture.getAbsolutePath()));
		// contents already tested
		assertThat(image.getExifInfo(), notNullValue());
	}
	
	@Test
	public void methodCreateListFromDirectory_shouldCreateListWithAllItems() throws Exception {
		List<ImageFile> images = ImageFileManager.createImagesFromDirectory(directoryWithPictures);
		
		assertThat(images.size(), is(4));
	}
	
	@Test
	public void createExifInfo_shouldReturnValidExifInfoOfFile() throws Exception {
		ExifInfo testobject = ImageFileManager.createExifInfo(testfile);
		
		final String EXPECTED_MAKE = "NIKON CORPORATION";
		final String EXPECTED_MODEL = "NIKON D3000";
		final String EXPECTED_ORIENTATION = "Top, left side (Horizontal / normal)";
		final String EXPECTED_X_RES = "300 dots per inch";
		final String EXPECTED_Y_RES = "300 dots per inch";
		final String EXPECTED_RES_UNIT = "Inch";
		final String EXPECTED_SOFWARE = "Ver.1.00 ";
		final LocalDateTime EXPECTED_DATE_TIME = LocalDateTime.parse("2017:12:10 23:16:06", DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss"));
		final String EXPECTED_YCBCR_POS = "Datum point";
		
		assertEquals(EXPECTED_MAKE, testobject.getMake());
		assertEquals(EXPECTED_MODEL, testobject.getModel());
		assertEquals(EXPECTED_DATE_TIME, testobject.getDateTime());
	}
}
