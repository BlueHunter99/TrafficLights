package me.bluehunter99.trafficlights.car;

import me.bluehunter99.trafficlights.light.LightDirection;
import me.bluehunter99.trafficlights.light.LightSide;
import me.bluehunter99.trafficlights.Main;

/**
 * De CarSensor klasse is de klasse van alle sensoren. Ze bepalen zelf of er een
 * auto is of niet en laten dat weten aan het verkeerslicht.
 *
 * @author Joeri
 *
 */

public class CarSensor {

    // Variabelen
    private LightSide side;
    private LightDirection direction;
    private Main gui;
    private int posX, posY;

    /**
     * Constructor
     *
     * @param side:
     *            Straat
     * @param direction:
     *            Richting
     * @param gui:
     *            Main component
     * @param posX:
     *            X positie
     * @param posY:
     *            Y positie
     */
    public CarSensor(LightSide side, LightDirection direction, Main gui, int posX, int posY) {
        // Initializeer variabelen
        this.side = side;
        this.direction = direction;
        this.posX = posX;
        this.posY = posY;
        this.gui = gui;

        // Voeg sensor toe aan lijst
        gui.sensors.add(this);
    }

    /**
     * Update methode
     */
    public void update() {
        // Check of er een auto op de sensor staat en voeg die dan toe aan de sensed
        // lijst van het verkeerslicht
        for (Car car : gui.cars) {
            if (car.side == side && car.direction == direction && car.posX == posX && car.posY == posY) {
                car.conflictLight.addSensedCar(car);
            }
        }
    }

}
