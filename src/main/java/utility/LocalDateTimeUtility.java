package utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalDateTimeUtility {

	/**
	 * Parses string representation of a date and time into
	 * {@linkplain LocalDateTime} object.
	 * 
	 * @param dateAsString string representation of date and time to convert to {@link LocalDateTime}
	 * @param format used to specify the format of the string representation provided
	 * @return {@link LocalDateTime} object of the provided string
	 * 
	 * @see LocalDateTime
	 * @see DateTimeFormatter
	 */
	public static LocalDateTime fromString(String dateAsString, String format) {
		if (dateAsString == null || dateAsString == "") {
			return null;
		} else  {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
			return LocalDateTime.parse(dateAsString, formatter);
		}
	}
	
	/**
	 * Formats {@linkplain LocalDateTime} into a string using the provided
	 * format string. For format string see {@linkplain DateTimeFormatter}.
	 * 
	 * @param dateTime input to convert to string representation
	 * @param format used to specify the string output 
	 * @return string representation of the provided input
	 * 
	 * @see DateTimeFormatter
	 * @see LocalDateTime
	 */
	public static String fromLocalDate(LocalDateTime dateTime, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return dateTime.format(formatter);
	}

}
