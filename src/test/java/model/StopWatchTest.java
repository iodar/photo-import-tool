package model;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import model.StopWatch.NotStartedException;
import model.StopWatch.NotStoppedException;

public class StopWatchTest {
	
	private StopWatch stopWatch;
	
	@Before
	public void setUpTestdata() {
		stopWatch = new StopWatch();
	}
	
	@Test(expected = NotStartedException.class)
	public void watchNotStarted_shouldReturnNotStartedExceptionWhenTryingToGetDifference() throws Exception {
		stopWatch.getDifferenceMessage();
	}
	
	@Test(expected = NotStoppedException.class)
	public void watchNotStopped_shouldReturnNotStoppedExceptionWhenTryingToGetDifference() throws Exception {
		stopWatch.start();
		stopWatch.getDifferenceMessage();
	}
	
	@Test(expected = NotStartedException.class)
	public void watchNotStartedAndStopIsCalled_shouldThrowNotStartedException() throws Exception {
		stopWatch.stop();
	}
	
	@Test
	public void stopWatchRunningTwoSeconds_shouldReturnMessageThatIsNotEmptyOrNull() throws Exception {
		stopWatch.start();
		stopWatch.stop();
		assertThat(stopWatch.getDifferenceMessage(), not(emptyOrNullString()));
	}
	
	@Test
	public void stopwatchStarted_shouldReturnTrueWhenCallingIsStarted() throws Exception {
		stopWatch.start();
		assertThat(stopWatch.isStarted(), equalTo(true));
	}
	
	@Test
	public void stopWatchStartedAndStopped_shouldReturnTrueWhenCallingIsStopped() throws Exception {
		stopWatch.start();
		stopWatch.stop();
		assertThat(stopWatch.isStopped(), equalTo(true));
	}
}
