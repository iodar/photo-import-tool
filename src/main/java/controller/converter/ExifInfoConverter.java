package controller.converter;

import model.ExifInfo;
import model.dto.ExifInfoDTO;

import static utility.LocalDateTimeUtility.getLocalDateFromStringWithExifFormat;

public class ExifInfoConverter {
    public static ExifInfo convertToExifInfo(ExifInfoDTO dto) {
        return new ExifInfo()
                .setMake(dto.getMake())
                .setModel(dto.getModel())
                .setDateTime(getLocalDateFromStringWithExifFormat(dto.getDateTime()));
    }

}
