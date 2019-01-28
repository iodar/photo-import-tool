package enums;

public enum MetadataDirectoryNames {
	EXIF_INFO("Exif IFD0"),
	EXIF_INFO_SUB("Exif SubIFD");
	
	private final String metadataDirectoryName;
	
	private MetadataDirectoryNames(String metadataDirectoryName) {
		this.metadataDirectoryName = metadataDirectoryName;
	}
	
	@Override
	public String toString() {
		return this.metadataDirectoryName;
	}
}
