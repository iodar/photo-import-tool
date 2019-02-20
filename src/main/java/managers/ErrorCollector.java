package managers;

import enums.MetadataStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import model.ImageFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static enums.MetadataStatus.NOT_READABLE;
import static enums.MetadataStatus.NO_DATA;

/**
 * Collects all image images which metadata could not been read.
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ErrorCollector {

    private List<ImageFile> imagesWhereReadingMetadataFailed = new ArrayList<>();

    public Boolean hasEntries() {
        return !imagesWhereReadingMetadataFailed.isEmpty();
    }

    public void addImageToCollector(final ImageFile imageFileWithoutMetadata) {
        imagesWhereReadingMetadataFailed.add(imageFileWithoutMetadata);
    }

    /**
     * Filters the list of images and returns only the images
     * that have the {@linkplain MetadataStatus} <code>NO_DATA</code>.
     *
     * @return List of images without Metadata
     */
    public List<ImageFile> getFilesWithoutMetadata() {
        return imagesWhereReadingMetadataFailed.stream()
                .filter(imageFile -> imageFile.getMetadataStatus() == NO_DATA)
                .collect(Collectors.toList());
    }

    /**
     * Filters the list of images and returns only the images that
     * have the {@linkplain MetadataStatus} <code>NOT_READABLE</code>.
     *
     * @return List of images that could not been read
     */
    public List<ImageFile> getCorruptFiles() {
        return imagesWhereReadingMetadataFailed.stream()
                .filter(imageFile -> imageFile.getMetadataStatus() == NOT_READABLE)
                .collect(Collectors.toList());
    }
}
