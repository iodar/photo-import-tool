package model;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.time.LocalDateTime;

import org.junit.Test;

import managers.ImageFileManager;
import utility.LocalDateTimeUtility;

public class ImageFileTest {

	private static final String imageAbsolutePathName = "C:/test";
	private static final String imageFileName = "DSC_1009.JPG";
	private final LocalDateTime dateTime = LocalDateTimeUtility.fromString("2019-01-02 16:55:11",
			"uuuu-MM-dd HH:mm:ss");
	private final ExifInfo exifInfo = new ExifInfo().setMake("Nikon").setModel("D5300").setDateTime(dateTime);

	private final ImageFile imageWithoutExifInfo = new ImageFile().setFileName(imageFileName)
			.setAbsoluteFilePath(imageAbsolutePathName);

	private final ImageFile imageFile = new ImageFile().setFileName(imageFileName)
			.setAbsoluteFilePath(imageAbsolutePathName).setExifInfo(exifInfo);

	private final ImageFile picture = ImageFileManager.createImageFile(new File("src/test/data/DSC_0001.JPG"));
	private final ImageFile picture2 = ImageFileManager.createImageFile(new File("src/test/data/DSC_0010.JPG"));

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
