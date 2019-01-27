package enums;

public enum FileExtension {

	JPG(".jpg"),
	NONE("");
	
	private String extensionWithDotNotation;
	
	private FileExtension(String fileExtension) {
		this.extensionWithDotNotation = fileExtension;
	}
	
	@Override
	public String toString() {
		return this.extensionWithDotNotation;
	}
}
