package enums;

public enum FileExtension {

	JPG(".jpg"),
	NONE("");
	
	private String fileExtension;
	
	private FileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	
	@Override
	public String toString() {
		return this.fileExtension;
	}
}
