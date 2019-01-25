package utility;

import static enums.FileExtension.JPG;
import static enums.FileExtension.NONE;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

public class FileUtilityTest {

	private final File jpg = new File("src/test/data/DSC_0001.JPG");
	private final File txt = new File("src/test/data/noPictureFile.txt");
	private final File fileWithoutExtension = new File("src/test/data/fileWithoutExtension");
	
	@Test
	public void methodGetExtensionCalledOnJpg_shouldReturnJpgEnum() throws Exception {
		assertThat(FileUtility.getExtension(jpg), equalTo(JPG));
	}
	
	@Test
	public void methodGetExtensionCalledOnNonJpgFile_shouldReturnNoneEnum() throws Exception {
		assertThat(FileUtility.getExtension(txt), equalTo(NONE));
	}
	
	@Test
	public void methodGetExtensionCalledOnFileWithoutExtension_shouldReturnNoneEnum() throws Exception {
		assertThat(FileUtility.getExtension(fileWithoutExtension), equalTo(NONE));
	}
}
