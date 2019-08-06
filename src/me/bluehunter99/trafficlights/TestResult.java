package me.bluehunter99.trafficlights;

/**
 *
 * De TestResult klasse is een simpele klasse om de resultaten van testen te
 * kunnen opslaan en verwerken zonder grote ArrayLists
 *
 * @author Joeri
 *
 */

public class TestResult {

    // Variabelen
    public LightSide side;
    public LightDirection direction;
    public int timer;

    /**
     * Constructor
     *
     * @param side:
     *            straat
     * @param direction:
     *            richting
     * @param timer:
     *            timer
     */
    public TestResult(LightSide side, LightDirection direction, int timer) {
        this.side = side;
        this.direction = direction;
        this.timer = timer;
    }

}
