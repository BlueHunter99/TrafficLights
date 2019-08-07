package me.bluehunter99.trafficlights.light;

import me.bluehunter99.trafficlights.Main;
import me.bluehunter99.trafficlights.car.Car;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * De Light klasse is de klasse van alle verkeerslichten.
 *
 * @author Joeri
 *
 */

public class Light {

    // Variabelen
    public LightSide side;
    public LightDirection direction;
    public LightState state;
    public JLabel label;
    public int posX, posY;
    public CopyOnWriteArrayList<Car> stoppedCars;
    public ArrayList<Car> waitingCars;
    public ArrayList<Car> sensedCars;
    private int yellowCounter, greenCounter;
    private boolean changingRed, changingGreen;

    /**
     * Constructor
     *
     * @param gui:
     *            Main component
     * @param side:
     *            Straat
     * @param direction:
     *            Richting
     * @param posX:
     *            X positie
     * @param posY:
     *            Y positie
     */
    public Light(Main gui, LightSide side, LightDirection direction, int posX, int posY) {
        // Variabelen initializeren
        this.side = side;
        this.direction = direction;
        this.posX = posX;
        this.posY = posY;
        stoppedCars = new CopyOnWriteArrayList<Car>();
        waitingCars = new ArrayList<Car>();
        sensedCars = new ArrayList<Car>();
        label = new JLabel("");
        label.setBounds(posX, posY, 100, 100);
        gui.lights.add(this);
        gui.render.lblMap.add(label);

        // Zet verkeerslicht op rood wanneer hij wordt gemaakt
        setState(LightState.RED);
    }

    /**
     * Update methode
     */
    public void update() {
        // Counter voor overgang naar rood: eerst 2 seconden op geel.
        if (changingRed) {
            if (yellowCounter > 0) {
                yellowCounter--;
            }
            if (yellowCounter == 0) {
                setState(LightState.RED);
                changingRed = false;
            }
        }

        // Counter voor overgang naar groen: eerst 5 seconden wachten.
        if (changingGreen) {
            if (greenCounter > 0) {
                greenCounter--;
            }
            if (greenCounter == 0) {
                setState(LightState.GREEN);
                changingGreen = false;
            }
        }
    }

    /**
     * Methode om de staat van het verkeerslicht in te stellen. Veranderd het
     * plaatje van het verkeerslicht
     *
     * @param state:
     *            de gewenste staat
     */
    private void setState(LightState state) {
        this.state = state;
        if (state.equals(LightState.GREEN)) {
            label.setIcon(new ImageIcon(Main.class.getResource("/Sources/traffic_light_green.png")));
            // Haalt alle auto's van het gestopte lijstje af zodat ze beginnen te rijden
            for (Car car : stoppedCars) {
                stoppedCars.remove(car);
                car.stopped = false;
            }
        } else if (state.equals(LightState.RED)) {
            label.setIcon(new ImageIcon(Main.class.getResource("/Sources/traffic_light_red.png")));
        } else {
            label.setIcon(new ImageIcon(Main.class.getResource("/Sources/traffic_light_yellow.png")));
        }
    }

    /**
     * Methode om gestopte auto's toe te voegen
     *
     * @param car:
     *            gewilde auto
     */
    public void addStoppedCar(Car car) {
        stoppedCars.add(car);
    }

    /**
     * Methode om wachtende auto's toe te voegen
     *
     * @param car:
     *            gewilde auto
     */
    public void addWaitingCar(Car car) {
        waitingCars.add(car);
    }

    /**
     * Methode om gedetecteerde auto's toe te voegen
     *
     * @param car:
     *            gewilde auto
     */
    public void addSensedCar(Car car) {
        sensedCars.add(car);
    }

    /**
     * Methode om stoplicht op rood te zetten. Eerst 2 seconden op oranje.
     */
    public void toRed() {
        changingRed = true;
        setState(LightState.YELLOW);
        yellowCounter = 200;
    }

    /**
     * Methode om stoplicht op groen te zetten. Eerst 5 seconden wachten.
     */
    public void toGreen() {
        changingGreen = true;
        greenCounter = 500;
    }
}
