package model;

import org.junit.Test;
import utility.LocalDateTimeUtility;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ExifInfoTest {

	@Test
	public void toString_shouldReturnQualifiedInformationOfTheObject() throws Exception {
		ExifInfo exifInfo = new ExifInfo()
								.setMake("Nikon")
								.setModel("D5300")
								.setDateTime(LocalDateTimeUtility.fromString("2019-01-01 20:21:30","uuuu-MM-dd HH:mm:ss"));
		
		final String expectedOutput = "Make: Nikon, Model: D5300, dateTime: 2019-01-01 20:21:30";
		
		assertThat(exifInfo.toString(), equalTo(expectedOutput));
	}
	
}
