package model;

import java.io.File;

import enums.MetadataStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
public class ImageFile {

	private String fileName;
	private String absoluteFilePath;
	private ExifInfo exifInfo;
	private MetadataStatus metadataStatus;

	
	public String toString() {
		if (getExifInfo() == null) {
			return String.format("[%s] [Exif] N/A", getFileName());
		} else {
			return String.format("[%s] [Exif] %s", getFileName(), getExifInfo().toString());
		}
		
	}
	
	public boolean compareTo(ImageFile differenceFile) {
		return (this.getSize() == differenceFile.getSize());
	}
	
	private long getSize() {
		return new File(this.getAbsoluteFilePath()).length();
	}
}
