package managers;

import static enums.MetadataStatus.NOT_READABLE;
import static enums.MetadataStatus.NO_DATA;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import enums.MetadataStatus;
import model.ImageFile;

public class ImageFileManagerTest {

	private File picture;
	private File pictureFileWithoutExifInfo;
	private File nonPictureFile;
	private File unreadableFile;
	private File emptyFolder;

	@Before
	public void setUp() {
		picture = new File("src/test/data/DSC_0001.JPG");
		pictureFileWithoutExifInfo = new File("src/test/data/DSC_0104.JPG");
		nonPictureFile = new File("src/test/data/noPictureFile.txt");

		unreadableFile = new File("src/test/data/fileWithoutExtension");
		unreadableFile.setReadable(false);
		emptyFolder = new File("src/test/data/emptyFolder");
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
		File directoryWithPictures = new File("src/test/data/testdataWithDirsAndPics/");
		List<ImageFile> images = ImageFileManager.createImagesFromDirectory(directoryWithPictures);

		assertThat(images.size(), is(4));
	}

	@Test
	public void createImageFileFromEmptyFolder_shouldReturnEmptyList() throws Exception {
		List<ImageFile> images = ImageFileManager.createImagesFromDirectory(emptyFolder);

		assertThat(images.size(), equalTo(0));
	}

	@Test
	public void createImageFileOfPictureWithoutExifInfo_shouldReturnImageFileMetadataStatusNoData() throws Exception {
		final String metadataStatusDescriptionTemplate = "Metadata of file [%s] could not been read or was null";

		ImageFile actualImageFile = ImageFileManager.createImageFile(pictureFileWithoutExifInfo);

		assertThat(actualImageFile.getMetadataStatus(), is(MetadataStatus.NO_DATA));
		assertThat(actualImageFile.getExifInfo(), is(nullValue()));
		assertThat(actualImageFile.getMetadataStatusDescription(),
				is(String.format(metadataStatusDescriptionTemplate, pictureFileWithoutExifInfo.getName())));
	}

	@Test
	public void createImageFileOfNonPictureFile_shouldReturnImageFileWithMetadataStatusNoDataAndImageProcessingDescription()
			throws Exception {
		final String expectedMetadataStatusDescription = "File format could not be determined";

		ImageFile actualImageFile = ImageFileManager.createImageFile(nonPictureFile);

		assertThat(actualImageFile.getMetadataStatus(), is(NO_DATA));
		assertThat(actualImageFile.getExifInfo(), is(nullValue()));
		assertThat(actualImageFile.getMetadataStatusDescription(), is(expectedMetadataStatusDescription));
	}

	@Test
	public void createImageFileOfFileThatCanNotBeRead_shouldReturnImageFileWithMetadataStatusNoDataAndDesciption()
			throws Exception {
		ImageFile actualImageFile = ImageFileManager.createImageFile(unreadableFile);
		System.out.println(actualImageFile.getMetadataStatusDescription());

		assertThat(actualImageFile.getMetadataStatus(), is(NOT_READABLE));
		assertThat(actualImageFile.getExifInfo(), is(nullValue()));
		assertThat(actualImageFile.getMetadataStatusDescription(), is(not(nullValue())));
	}

}
