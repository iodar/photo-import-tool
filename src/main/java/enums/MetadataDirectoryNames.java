package enums;

public enum MetadataDirectoryNames {
	EXIF_INFO("Exif IFD0"),
	EXIF_INFO_SUB("Exif SubIFD");
	
	private final String MetadataDirectoryName;
	
	private MetadataDirectoryNames(String MetadataDirectoryName) {
		this.MetadataDirectoryName = MetadataDirectoryName;
	}
	
	public String toString() {
		return this.MetadataDirectoryName;
	}
}
