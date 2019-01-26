package model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class StopWatch {

	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Long hours;
	private Long minutes;
	private Long seconds;
	private Long milliseconds;
	
	public void start() {
		this.startTime = LocalDateTime.now();
	}
	
	public void stop() throws NotStartedException {
		if (!isStarted()) {
			throw new NotStartedException("stopwatch not started yet");
		} else {
			this.endTime = LocalDateTime.now();
		}
	}
	
	public Boolean isStarted() {
		return this.startTime != null;
	}
	
	public Boolean isStopped() {
		return this.endTime != null;
	}
	
	private void getDifference() throws NotStartedException, NotStoppedException {
		if (!isStarted()) {
			throw new NotStartedException("stopwatch not started yet");
		} else if (!isStopped()) {
			throw new NotStoppedException("stopwatch not stoppped yet");
		} else {
			LocalDateTime tempDateTime = LocalDateTime.from(startTime);
			
			this.hours = tempDateTime.until(endTime, ChronoUnit.HOURS);
			tempDateTime = tempDateTime.plusHours(hours);
			
			this.minutes = tempDateTime.until(endTime, ChronoUnit.MINUTES);
			tempDateTime = tempDateTime.plusMinutes(minutes);
			
			this.seconds = tempDateTime.until(endTime, ChronoUnit.SECONDS);
			tempDateTime = tempDateTime.plusSeconds(seconds);
			
			this.milliseconds = tempDateTime.until(endTime, ChronoUnit.MILLIS);
		}
	}
	
	public String getDifferenceMessage() throws NotStartedException, NotStoppedException {
		getDifference();
		return String.format("%sh %sm %ss %sms", getHours(), getMinutes(), getSeconds(), getMilliseconds());
	}
	
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public class NotStartedException extends Exception {
		private static final long serialVersionUID = 6444549966500438283L;

		public NotStartedException(String message) {
			super(message);
		}
	}
	
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public class NotStoppedException extends Exception {
		private static final long serialVersionUID = 8393937595454410902L;

		public NotStoppedException(String message) {
			super(message);
		}
	}
}
