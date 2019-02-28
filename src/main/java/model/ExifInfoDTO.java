package model;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import utility.LocalDateTimeUtility;

import java.time.LocalDateTime;

@Generated
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ExifInfoDTO {
    private String make;
    private String model;
    private String dateTime;

    public ExifInfo toExifInfo() {
        return new ExifInfo()
                .setMake(getMake())
                .setModel(getModel())
                .setDateTime(getDateFromString(getDateTime()));
    }

    private LocalDateTime getDateFromString(String dateTime) {
        return LocalDateTimeUtility.getLocalDateFromStringWithExifFormat(dateTime);
    }
}
