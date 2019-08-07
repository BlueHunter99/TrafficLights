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
 * @author Joeri Meijer
 *
 */

public class Update implements Runnable {

    /**
     * Variabelen
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
     * @param gui:
     *            main gui object
     * @param scenario:
     *            het scenario wat geladen meot worden
     */
    public Update(Main gui, Scenario scenario) {
        this.gui = gui;
        this.scenario = scenario;
        render = gui.render;
        spawning = new CarSpawner(gui, gui.render);
    }

    /**
     * Run methode.
     */
    @Override
    public void run() {
        lc = new LightController(gui, scenario);
        while (true) {
            // Check of er gestopt is
            if (gui.stopped) {
                return;
            } else {
                // Update de lights controller
                lc.update();

                // Update alle auto's
                for (Car car : gui.cars) {
                    car.update();
                }

                // Update alle verkeerslichten
                for (Light light : gui.lights) {
                    light.update();
                }

                // Update alle sensoren
                for (CarSensor sensor : gui.sensors) {
                    sensor.update();
                }

                // Zorg ervoor dat spawning maar 1 keer per seconde gebeurd
                if (spawnCounter <= 0) {
                    spawning.run();
                    spawnCounter = gui.tps * secondsPerSpawn;
                } else {
                    spawnCounter--;
                }

                // Render
                render.run();

                // Zorg ervoor dat er alleen zoveel geupdate wordt als gespecificeerd door ticks
                // per second
                try {
                    Thread.sleep((1000 / gui.tps));
                } catch (InterruptedException e) {
                    System.out.println("|Error| Update thread could not sleep.");
                }
            }

        }
    }
}
