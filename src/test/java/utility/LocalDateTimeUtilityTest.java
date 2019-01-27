package utility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static utility.LocalDateTimeUtility.fromString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.hamcrest.Matchers;
import org.junit.Test;

public class LocalDateTimeUtilityTest {
	
	@Test
	public void fromStringMethod_shouldReturnLocalDateOfString() throws Exception {
		final String format = "uuuu-MM-dd HH:mm:ss";
		final String dateAsString = "2019-01-12 16:45:59";
		
		LocalDateTime localDate = fromString(dateAsString, format);
		LocalDateTime expectedDateTime = LocalDateTime.parse(dateAsString,
									DateTimeFormatter.ofPattern(format));
		
		assertEquals(expectedDateTime, localDate);
	}
	
	@Test
	public void fromLocalDateMethod_shouldReturnFormattedOutput() throws Exception {
		final String dateAsString = "2019-01-11 16:45:32";
		final String format = "uuuu-MM-dd HH:mm:ss";
		final LocalDateTime dateTime = 
				LocalDateTime.parse(dateAsString, DateTimeFormatter.ofPattern(format));
		
		assertThat(LocalDateTimeUtility.fromLocalDate(dateTime, format),
					Matchers.equalTo(dateAsString));
	}
	
}
