package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import utility.LocalDateTimeUtility;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
public class ExifInfo {

	private String make;
	private String model;
	private LocalDateTime dateTime;

	public String toString() {
		return String.format("Make: %s, Model: %s, dateTime: %s", getMake(), getModel(),
				LocalDateTimeUtility.fromLocalDate(getDateTime(), "uuuu-MM-dd HH:mm:ss"));
	}
}
