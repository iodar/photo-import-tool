package controller.converter;

import model.ExifInfo;
import model.dto.ExifInfoDTO;
import org.hamcrest.Matchers;
import org.junit.Test;
import utility.LocalDateTimeUtility;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static utility.LocalDateTimeUtility.fromString;

public class ExifInfoConverterTest {

    @Test
    public void convertToExifInfo() {
        final ExifInfoDTO dto = new ExifInfoDTO()
                .setMake("Nikon")
                .setModel("D5300")
                .setDateTime("2019:01:01 23:23:42");

        final ExifInfo exifInfo = ExifInfoConverter.convertToExifInfo(dto);

        assertThat(exifInfo.getMake(), is(equalTo(dto.getMake())));
        assertThat(exifInfo.getModel(), is(equalTo(dto.getModel())));
        assertThat(exifInfo.getDateTime(),
                is(equalTo(fromString(dto.getDateTime(), "uuuu:MM:dd HH:mm:ss"))));
    }
}