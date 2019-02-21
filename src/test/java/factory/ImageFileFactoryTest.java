package factory;

import enums.MetadataStatus;
import model.ExifInfo;
import model.ImageFile;
import org.hamcrest.Matchers;
import org.junit.Test;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static enums.MetadataStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ImageFileFactoryTest {


    @Test
    public void createImageFileWithNoMetadataStatus_shouldReturnImageFileWithNoMetadataStatus() {
        final ImageFile imageFile = ImageFileFactory.createImageFileWithNoMetadataStatus(null, null,
                null);

        assertThat(imageFile.getMetadataStatus(), is(equalTo(NO_DATA)));
    }

    @Test
    public void createImageFileWithNotReadableMetadataStatus_shouldReturnImageFileWithStatusNotReadable() {
        final ImageFile imageFile = ImageFileFactory.createImageFileWithNotReadableMetadataStatus(null,
                null, null);

        assertThat(imageFile.getMetadataStatus(), is(equalTo(NOT_READABLE)));
    }

    @Test
    public void createImageFile_shouldReturnImageFileWithAllAttributesSet() {
        final ImageFile reference = new ImageFile()
                .setFileName("testName")
                .setAbsoluteFilePath("path/to/file/")
                .setExifInfo(new ExifInfo().setMake("NIKON").setModel("D53000").setDateTime(null))
                .setMetadataStatus(OK)
                .setMetadataStatusDescription(null);

        final ImageFile imageFile = ImageFileFactory.createImageFile(reference.getFileName(), reference.getAbsoluteFilePath(),
                reference.getMetadataStatusDescription(), reference.getMetadataStatus(), reference.getExifInfo());

        assertThat(imageFile, sameBeanAs(reference));
    }
}