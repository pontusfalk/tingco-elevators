package com.tingco.codechallenge.elevator.api.tingco;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Executors;

import static com.jayway.awaitility.Awaitility.await;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class TingcoElevatorShaftEngineTest {
    private final int speed = 20;
    private TingcoElevator elevator;
    private TingcoElevatorShaftEngine engine;

    @Before
    public void setUp() {
        elevator = mock(TingcoElevator.class);
        engine = new TingcoElevatorShaftEngine(Executors.newSingleThreadScheduledExecutor(), speed);
    }

    @Test
    public void startingElevatorMakesItTick() {
        engine.startElevator(elevator);

        //risk of erratic test
        await()
          .atMost(5 * speed, MILLISECONDS)
          .pollInterval(speed, MILLISECONDS)
          .until(() -> verify(elevator, atLeast(3)).tick());
    }

    @Test
    public void stoppingElevatorMakesItStopTicking() throws InterruptedException {
        //not my proudest test
        engine.startElevator(elevator);
        engine.stopElevator(elevator);

        reset(elevator);
        Thread.sleep(3 * speed);

        verify(elevator, never()).tick();
    }

    @Test
    public void stoppingNonRunningElevatorWillThrow() {
        assertThatThrownBy(() -> engine.stopElevator(elevator))
          .isInstanceOf(IllegalElevatorActionException.class)
          .hasMessageContaining("not running");
    }

    @Test
    public void startingElevatorTwiceWillThrow() {
        engine.startElevator(elevator);

        assertThatThrownBy(() -> engine.startElevator(elevator))
          .isInstanceOf(IllegalElevatorActionException.class)
          .hasMessageContaining("already running");
    }
}
