package model;

import enums.FileExtension;
import org.junit.Test;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FileExtensionTest {
	
	private FileExtension fileExtension;

	@Test
	public void fileExtensionNone_shouldReturnEmptyStringWhenToStringIsCalled() throws Exception {
		fileExtension = FileExtension.NONE;
		assertThat(fileExtension.toString(), emptyOrNullString());
	}
	
	@Test
	public void fileExtensionJpg_shouldReturnDotJpgValueWhenToStringIsCalled() throws Exception {
		fileExtension = FileExtension.JPG;
		assertThat(fileExtension.toString(), is(".jpg"));
	}
}
