package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MetadataStatus {
	
	OK("Ok", "Metadaten sind vollständig"),
	NOT_READABLE("Nicht lesbar", "Datei konnte nicht gelesen werden oder Metadaten sind korrupt"),
	NO_DATA("Keine Daten", "Element hat keine Metadaten");
	
	private String status;
	private String description;

}
