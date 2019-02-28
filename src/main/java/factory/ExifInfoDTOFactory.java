package factory;

import com.drew.metadata.Directory;
import enums.ExifIFD0Info;
import lombok.Data;
import lombok.experimental.Accessors;
import model.dto.ExifInfoDTO;

@Data
@Accessors(chain = true)
public class ExifInfoDTOFactory {
    public static ExifInfoDTO createDtoFromExifInfoDirectoy(Directory directory) {
        ExifInfoDTO exifInfoDTO = new ExifInfoDTO();

        directory.getTags().forEach(tag -> {
            final ExifIFD0Info exifIFD0Info = getValueOfString(tag.getTagName());
            switch (exifIFD0Info) {
                case DATE_TIME:
                    exifInfoDTO.setDateTime(tag.getDescription());
                    break;
                case MAKE:
                    exifInfoDTO.setMake(tag.getDescription());
                    break;
                case MODEL:
                    exifInfoDTO.setModel(tag.getDescription());
                    break;
            }
        });

        return  exifInfoDTO;
    }

    private static ExifIFD0Info getValueOfString(String value) {
        try {
            return ExifIFD0Info.valueOf(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
