package utility;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static utility.LocalDateTimeUtility.fromString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.hamcrest.Matchers;
import org.junit.Test;

import utility.LocalDateTimeUtility.UnsupportedDateStringException;

public class LocalDateTimeUtilityTest {
	
	private final String expectedUnsupportedDateStringExceptionMessage = "datestring can not be null or empty";
	
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
	
//	@Test
//	public void fromStringMethodWithNullValueForDateString_shouldThrowUnsupportedDateStringException() throws Exception {
//		UnsupportedDateStringException actualException = null;
//		
//		try {
//			LocalDateTimeUtility.fromString(null, "uuuu-MM-dd HH:mm:ss");
//		} catch (UnsupportedDateStringException unsupportedDateStringException) {
//			actualException = unsupportedDateStringException;
//		}
//		
//		assertThat(actualException, is(notNullValue()));
//		assertThat(actualException, is(instanceOf(UnsupportedDateStringException.class)));
//		assertThat(actualException.getMessage(), is(expectedUnsupportedDateStringExceptionMessage));
//	}
	
//	@Test
//	public void fromStringMethodWithEmptyValueForDateString_shouldThrowUnsupportedDateStringException() throws Exception {
//		UnsupportedDateStringException actualException = null;
//		
//		try {
//			LocalDateTimeUtility.fromString("", "uuuu-MM-dd HH:mm:ss");
//		} catch (UnsupportedDateStringException unsupportedDateStringException) {
//			actualException = unsupportedDateStringException; 
//		}
//		
//		assertThat(actualException, is(notNullValue()));
//		assertThat(actualException, is(instanceOf(UnsupportedDateStringException.class)));
//		assertThat(actualException.getMessage(), is(expectedUnsupportedDateStringExceptionMessage));
//	}
	
}
