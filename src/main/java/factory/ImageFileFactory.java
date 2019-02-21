package factory;

import enums.MetadataStatus;
import model.ExifInfo;
import model.ImageFile;

import static enums.MetadataStatus.NOT_READABLE;
import static enums.MetadataStatus.NO_DATA;

public class ImageFileFactory {

    public static ImageFile createImageFileWithNoMetadataStatus(final String fileName,
                                                                final String absoluteFilePath,
                                                                final String metadataStatusDescription) {
        return createImageFile(fileName, absoluteFilePath, metadataStatusDescription,
                NO_DATA, null);
    }

    public static ImageFile createImageFileWithNotReadableMetadataStatus(final String fileName,
                                                                         final String absoluteFilePath,
                                                                         final String metadataStatusDescription) {

        return createImageFile(fileName, absoluteFilePath, metadataStatusDescription,
                NOT_READABLE, null);
    }

    public static ImageFile createImageFile(final String fileName, final String absoluteFilePath,
                                             final String metatdataStatusDescription,
                                             final MetadataStatus metadataStatus,
                                             final ExifInfo exifInfo) {
        return new ImageFile()
                .setFileName(fileName)
                .setAbsoluteFilePath(absoluteFilePath)
                .setMetadataStatus(metadataStatus)
                .setExifInfo(exifInfo)
                .setMetadataStatusDescription(metatdataStatusDescription);
    }
}
