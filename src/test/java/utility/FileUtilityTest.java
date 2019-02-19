package utility;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static enums.FileExtension.JPG;
import static enums.FileExtension.NONE;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FileUtilityTest {

    private final File jpg = new File("src/test/data/DSC_0001.JPG");
    private final File txt = new File("src/test/data/noPictureFile.txt");
    private final File fileWithoutExtension = new File("src/test/data/fileWithoutExtension");

    @Test
    public void methodGetExtensionCalledOnJpg_shouldReturnJpgEnum() {
        assertThat(FileUtility.getExtension(jpg), equalTo(JPG));
    }

    @Test
    public void methodGetExtensionCalledOnNonJpgFile_shouldReturnNoneEnum() {
        assertThat(FileUtility.getExtension(txt), equalTo(NONE));
    }

    @Test
    public void methodGetExtensionCalledOnFileWithoutExtension_shouldReturnNoneEnum() {
        assertThat(FileUtility.getExtension(fileWithoutExtension), equalTo(NONE));
    }

    @Test
    public void getFilesOfDirectory_shouldReturnListOfSize7() throws IOException {
        final File folderWith4Pictures = new File("src/test/data/testdataWithDirsAndPics/");
        List<File> files = FileUtility.getFilesOfDirectory(folderWith4Pictures);
        assertThat(files.size(), is(7));
    }
}
