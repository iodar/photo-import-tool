package enums;

public enum MetadataDirectoryNames {
	EXIF_INFO("Exif IFD0"),
	EXIF_INFO_SUB("Exif SubIFD");
	
	private final String metadataDirectoryName;
	
	private MetadataDirectoryNames(String MetadataDirectoryName) {
		this.metadataDirectoryName = MetadataDirectoryName;
	}
	
	@Override
	public String toString() {
		return this.metadataDirectoryName;
	}
}
