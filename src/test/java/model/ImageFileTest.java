package model;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import managers.ImageFileManager;
import utility.LocalDateTimeUtility;
import utility.LocalDateTimeUtility.UnsupportedDateStringException;

public class ImageFileTest {

	private static String imageAbsolutePathName;
	private static final String imageFileName = "DSC_1009.JPG";
	private LocalDateTime dateTime;

	private ExifInfo exifInfo;

	private ImageFile imageWithoutExifInfo;

	private ImageFile imageFile;
	private ImageFile picture;
	private ImageFile picture2;

	@Before
	public void setUpTestdata() throws UnsupportedDateStringException {
		imageAbsolutePathName = "C:/test";
		dateTime = LocalDateTimeUtility.fromString("2019-01-02 16:55:11", "uuuu-MM-dd HH:mm:ss");
		exifInfo = new ExifInfo().setMake("Nikon").setModel("D5300").setDateTime(dateTime);
		imageWithoutExifInfo = new ImageFile().setFileName(imageFileName).setAbsoluteFilePath(imageAbsolutePathName);
		imageFile = new ImageFile().setFileName(imageFileName).setAbsoluteFilePath(imageAbsolutePathName)
				.setExifInfo(exifInfo);
		picture = ImageFileManager.createImageFile(new File("src/test/data/DSC_0001.JPG"));
		picture2 = ImageFileManager.createImageFile(new File("src/test/data/DSC_0010.JPG"));
	}

	@Test
	public void objectWithoutExifCallingToString_shouldReturnOutputWithoutExifInfo() throws Exception {
		final String expectedToStringOutput = String.format("[%s] [Exif] N/A", imageFileName);

		assertThat(imageWithoutExifInfo.toString(), equalTo(expectedToStringOutput));
	}

	@Test
	public void objectWithExifCalligToString_shouldReturnOutputWithExifInfo() throws Exception {
		final String expectedToStringOutput = "[DSC_1009.JPG] [Exif] Make: Nikon,"
				+ " Model: D5300, dateTime: 2019-01-02 16:55:11";

		assertThat(imageFile.toString(), equalTo(expectedToStringOutput));
	}

	@Test
	public void comparingEqualObjects_shouldReturnTrueWhenCallingCompareTo() throws Exception {
		assertThat(picture.compareTo(picture), equalTo(true));
	}

	@Test
	public void comparingNotEqualObejcts_shouldReturnFalseWhenCallingCompareTo() throws Exception {
		assertThat(picture.compareTo(picture2), equalTo(false));
	}
	
}
