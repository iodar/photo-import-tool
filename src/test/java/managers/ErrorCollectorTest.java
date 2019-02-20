package managers;

import enums.MetadataStatus;
import model.ImageFile;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static enums.MetadataStatus.NOT_READABLE;
import static enums.MetadataStatus.NO_DATA;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class ErrorCollectorTest {

    private ErrorCollector errorCollector;
    private List<ImageFile> testFiles = new ArrayList<>();
    private ImageFile imageFile1;
    private ImageFile imageFile2;
    private ImageFile imageFile3;

    @Before
    public void setUp() throws Exception {
        errorCollector = new ErrorCollector();

        imageFile1 = new ImageFile()
                .setFileName("file1")
                .setAbsoluteFilePath("path/to/file/")
                .setExifInfo(null)
                .setMetadataStatus(NO_DATA)
                .setMetadataStatusDescription(null);

        imageFile2 = new ImageFile()
                .setFileName("file2")
                .setAbsoluteFilePath("path/to/file/")
                .setExifInfo(null)
                .setMetadataStatus(NO_DATA)
                .setMetadataStatusDescription(null);

        imageFile3 = new ImageFile()
                .setFileName("file3")
                .setAbsoluteFilePath("path/to/file/")
                .setExifInfo(null)
                .setMetadataStatus(NOT_READABLE)
                .setMetadataStatusDescription(null);

        testFiles.add(imageFile1);
        testFiles.add(imageFile2);
        testFiles.add(imageFile3);
    }

    @Test
    public void emtpyCollector_whenHasEntriesIsCalled_shouldReturnFalse() {
        assertThat(errorCollector.hasEntries(), is(equalTo(false)));
    }

    @Test
    public void collector_afterOneImageIsAdded_shouldReturnTrue() {
        final ImageFile imageFile = new ImageFile()
                .setFileName("file1.jpg")
                .setAbsoluteFilePath("path/to/file/")
                .setExifInfo(null)
                .setMetadataStatus(NO_DATA)
                .setMetadataStatusDescription(null);
        errorCollector.addImageToCollector(imageFile);

        assertThat(errorCollector.hasEntries(), is(equalTo(true)));
    }

    @Test
    public void collectorWithDifferentFiles_getImagesWithNoMetadata_shouldReturnOnlyImagesWithNoMetadata() {
        testFiles.stream().forEach(imageFile -> errorCollector.addImageToCollector(imageFile));

        List<ImageFile> imagesWithoutMetadata = errorCollector.getFilesWithoutMetadata();

        assertThat(imagesWithoutMetadata, contains(imageFile1, imageFile2));
        assertThat(imagesWithoutMetadata, not(contains(imageFile3)));
    }

    @Test
    public void collectorWithDifferentFiles_getCorruptImages_shouldReturnOnlyImagesWithStatusNotReadable() {
        testFiles.stream().forEach(imageFile -> errorCollector.addImageToCollector(imageFile));

        List<ImageFile> corruptImages = errorCollector.getCorruptFiles();

        assertThat(corruptImages, contains(imageFile3));
        assertThat(corruptImages, not(contains(imageFile1, imageFile2)));
    }
}