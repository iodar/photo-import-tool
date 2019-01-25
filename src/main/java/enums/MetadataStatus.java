package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MetadataStatus {
	
	OK("Ok", "Metadaten sind vollständig"),
	CORRUPT("Korrupt", "Metadaten sind korrupt und konnten nicht gelesen werden"),
	NO_DATA("Keine Daten", "Element hat keine Metadaten");
	
	private String status;
	private String description;

}
