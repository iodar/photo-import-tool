package enums;

public enum ExifIFD0Info {
	MAKE("Make"),
	MODEL("Model"),
	ORIENTATION("Orientation"),
	X_RESOLUTION("X Resolution"),
	Y_RESOLUTION("Y Resolution"),
	RESOLUTION_UNIT("Resolution Unit"),
	SOFTWARE("Software"),
	DATE_TIME("Date/Time"),
	YCBCR_POSITIONING("YCbCr Positioning");
	
	private String exifInfoTagName;
	
	private ExifIFD0Info(String exifInfoTagName) {
		this.exifInfoTagName = exifInfoTagName;
	}
	
	@Override
	public String toString() {
		return this.exifInfoTagName;
	}

}
