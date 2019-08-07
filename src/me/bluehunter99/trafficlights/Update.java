package me.bluehunter99.trafficlights;

import me.bluehunter99.trafficlights.car.Car;
import me.bluehunter99.trafficlights.car.CarSensor;
import me.bluehunter99.trafficlights.car.CarSpawner;
import me.bluehunter99.trafficlights.light.Light;
import me.bluehunter99.trafficlights.light.LightController;

/**
 *
 * The update class is the class which manages all of the run cycles of the
 * program. It controls the spawning of cars and their updating. It also
 * controls the rendering of the individual scenarios and their components.
 *
 * @author BlueHunter99
 *
 */

public class Update implements Runnable {

    /**
     * Variables
     */
    private Main gui;
    private CarSpawner spawning;
    private Render render;
    private Scenario scenario;
    private double secondsPerSpawn = 1;
    private double spawnCounter;
    private LightController lc;

    /**
     * Constructor
     *
     * @param gui: main gui object
     * @param scenario: the scenario to be loaded
     */
    public Update(Main gui, Scenario scenario) {
        this.gui = gui;
        this.scenario = scenario;
        render = gui.render;
        spawning = new CarSpawner(gui, gui.render);
    }

    /**
     * Run method
     */
    @Override
    public void run() {
        lc = new LightController(gui, scenario);
        while (true) {
            // Check whether the process has been stopped
            if (gui.stopped) {
                return;
            } else {
                // Update the lights controller
                lc.update();

                // Update all cars
                for (Car car : gui.cars) {
                    car.update();
                }

                // Update all lights
                for (Light light : gui.lights) {
                    light.update();
                }

                // Update all sensors
                for (CarSensor sensor : gui.sensors) {
                    sensor.update();
                }

                // Control the spawnrate
                if (spawnCounter <= 0) {
                    spawning.run();
                    spawnCounter = gui.tps * secondsPerSpawn;
                } else {
                    spawnCounter--;
                }

                // Render
                render.run();

                // Implement ticks per second
                try {
                    Thread.sleep((1000 / gui.tps));
                } catch (InterruptedException e) {
                    System.out.println("|Error| Update thread could not sleep.");
                }
            }
        }
    }
}
