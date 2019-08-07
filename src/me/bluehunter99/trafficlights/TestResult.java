package me.bluehunter99.trafficlights;

import me.bluehunter99.trafficlights.light.LightDirection;
import me.bluehunter99.trafficlights.light.LightSide;

/**
 *
 * The TestResult class is a template for the objects which represents different test results
 *
 * @author BlueHunter99
 *
 */

public class TestResult {

    // Variables
    public LightSide side;
    public LightDirection direction;
    public int timer;

    /**
     * Constructor
     *
     * @param side: the side of the intersection of the result
     * @param direction: the direction of the intersection of the result
     * @param timer: the resulting time
     */
    public TestResult(LightSide side, LightDirection direction, int timer) {
        this.side = side;
        this.direction = direction;
        this.timer = timer;
    }

}
