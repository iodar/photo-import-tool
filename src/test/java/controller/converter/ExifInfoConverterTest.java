package controller.converter;

import model.ExifInfo;
import model.dto.ExifInfoDTO;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static utility.LocalDateTimeUtility.fromString;

public class ExifInfoConverterTest {

    @Test
    public void convertToExifInfo() {
        final ExifInfoDTO dto = new ExifInfoDTO()
                .setMake("Nikon")
                .setModel("D5300")
                .setDateTime("2019:01:01 23:23:42");

        final ExifInfo exifInfo = ExifInfoConverter.convertToExifInfo(dto);

        assertThat(exifInfo, allOf(hasProperty("make", is(equalTo(dto.getMake()))),
                hasProperty("model", is(equalTo(dto.getModel()))),
                hasProperty("dateTime", is(equalTo(fromString(dto.getDateTime(), "uuuu:MM:dd HH:mm:ss"))))));

    }
}