package managers;

import enums.MetadataStatus;
import model.ImageFile;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static enums.MetadataStatus.NOT_READABLE;
import static enums.MetadataStatus.NO_DATA;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

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
    public void methodForCreatingImageFile_shouldReturnImageFileWithExifData() {
        ImageFile image = ImageFileManager.createImageFile(picture);

        assertThat(image.getFileName(), equalTo("DSC_0001.JPG"));
        assertThat(image.getAbsoluteFilePath(), equalTo(picture.getAbsolutePath()));
        assertThat(image.getMetadataStatus(), is(MetadataStatus.OK));
        assertThat(image.getExifInfo(), notNullValue());
    }

    @Test
    public void methodCreateListFromDirectory_shouldCreateListWithAllItems() throws Exception {
        File directoryWithPictures = new File("src/test/data/testdataWithDirsAndPics/");
        List<ImageFile> images = ImageFileManager.createImagesFromDirectory(directoryWithPictures);

        assertThat(images.size(), is(4));
    }

    @Test
    public void createImageFileFromFolderWithoutImages_shouldReturnEmptyList() throws Exception {
        List<ImageFile> images = ImageFileManager.createImagesFromDirectory(emptyFolder);

        assertThat(images.size(), equalTo(0));
    }

    @Test
    public void createImageFileOfPictureWithoutExifInfo_shouldReturnImageFileMetadataStatusNoData() {
        final String metadataStatusDescriptionTemplate = "Metadata of file [%s] could not been read or was null";

        ImageFile actualImageFile = ImageFileManager.createImageFile(pictureFileWithoutExifInfo);

        assertThat(actualImageFile.getMetadataStatus(), is(MetadataStatus.NO_DATA));
        assertThat(actualImageFile.getExifInfo(), is(nullValue()));
        assertThat(actualImageFile.getMetadataStatusDescription(),
                is(String.format(metadataStatusDescriptionTemplate, pictureFileWithoutExifInfo.getName())));
    }

    @Test
    public void createImageFileOfNonPictureFile_shouldReturnImageFileWithMetadataStatusNoDataAndImageProcessingDescription() {
        final String expectedMetadataStatusDescription = "File format could not be determined";

        ImageFile actualImageFile = ImageFileManager.createImageFile(nonPictureFile);

        assertThat(actualImageFile.getMetadataStatus(), is(NO_DATA));
        assertThat(actualImageFile.getExifInfo(), is(nullValue()));
        assertThat(actualImageFile.getMetadataStatusDescription(), is(expectedMetadataStatusDescription));
    }

    @Ignore("Ignored because of different Exceptions on dos and linux")
    @Test
    public void createImageFileOfFileThatCanNotBeRead_shouldReturnImageFileWithMetadataStatusNoDataAndDescription() {
        ImageFile actualImageFile = ImageFileManager.createImageFile(unreadableFile);
        System.out.println(actualImageFile.getMetadataStatusDescription());

        assertThat(actualImageFile.getMetadataStatus(), is(NOT_READABLE));
        assertThat(actualImageFile.getExifInfo(), is(nullValue()));
        assertThat(actualImageFile.getMetadataStatusDescription(), is(not(nullValue())));
    }

}
