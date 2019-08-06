package me.bluehunter99.trafficlights;

/**
 *
 * The LightsController class is brain of the intersection. It manages the
 * lights and the sensors, which in turn manage how the cars move.
 *
 * @author Joeri Meijer
 *
 */

public class LightController {

    // Variabelen
    private Light Llr, Lls, Lll, Lur, Lus, Lul, Lrr, Lrs, Lrl, Ldr, Lds, Ldl;
    @SuppressWarnings("unused")
    private CarSensor Slr, Sls1, Sls2, Sls3, Sll, Sur, Sus, Sul, Srr, Srs1, Srs2, Srl, Sdr, Sds, Sdl;
    private Main gui;
    private Light priorityLight;

    /**
     * Constructor
     *
     * @param gui:
     *            Main component
     * @param scenario:
     *            Scenario
     */
    public LightController(Main gui, Scenario scenario) {
        this.gui = gui;
        // Alle lichten maken
        Llr = new Light(gui, LightSide.LEFT, LightDirection.RIGHT, 250, 346);
        Lls = new Light(gui, LightSide.LEFT, LightDirection.STRAIGHT, 250, 300);
        Lll = new Light(gui, LightSide.LEFT, LightDirection.LEFT, 250, 254);

        Lur = new Light(gui, LightSide.UP, LightDirection.RIGHT, 321, 130);
        Lus = new Light(gui, LightSide.UP, LightDirection.STRAIGHT, 341, 130);
        Lul = new Light(gui, LightSide.UP, LightDirection.LEFT, 362, 130);

        Lrr = new Light(gui, LightSide.RIGHT, LightDirection.RIGHT, 495, 153);
        Lrs = new Light(gui, LightSide.RIGHT, LightDirection.STRAIGHT, 495, 208);
        Lrl = new Light(gui, LightSide.RIGHT, LightDirection.LEFT, 495, 245);

        Ldr = new Light(gui, LightSide.DOWN, LightDirection.RIGHT, 442, 375);
        Lds = new Light(gui, LightSide.DOWN, LightDirection.STRAIGHT, 421, 375);
        Ldl = new Light(gui, LightSide.DOWN, LightDirection.LEFT, 401, 375);

        // Check welk scenario het is
        if (scenario.equals(Scenario.SOFTWARE)) {
            // Voeg de sensoren toe
            Slr = new CarSensor(LightSide.LEFT, LightDirection.RIGHT, gui, 150, gui.spawnCoordLR.y);
            Sls1 = new CarSensor(LightSide.LEFT, LightDirection.STRAIGHT, gui, 150, gui.spawnCoordLS1.y);
            Sls2 = new CarSensor(LightSide.LEFT, LightDirection.STRAIGHT, gui, 150, gui.spawnCoordLS2.y);
            Sls3 = new CarSensor(LightSide.LEFT, LightDirection.STRAIGHT, gui, 150, gui.spawnCoordLS3.y);
            Sll = new CarSensor(LightSide.LEFT, LightDirection.LEFT, gui, 150, gui.spawnCoordLL.y);

            Sur = new CarSensor(LightSide.UP, LightDirection.RIGHT, gui, gui.spawnCoordUR.x, 30);
            Sus = new CarSensor(LightSide.UP, LightDirection.STRAIGHT, gui, gui.spawnCoordUS.x, 30);
            Sul = new CarSensor(LightSide.UP, LightDirection.LEFT, gui, gui.spawnCoordUL.x, 30);

            Srr = new CarSensor(LightSide.RIGHT, LightDirection.RIGHT, gui, 595, gui.spawnCoordRR.y);
            Srs1 = new CarSensor(LightSide.RIGHT, LightDirection.STRAIGHT, gui, 595, gui.spawnCoordRS1.y);
            Srs2 = new CarSensor(LightSide.RIGHT, LightDirection.STRAIGHT, gui, 595, gui.spawnCoordRS2.y);
            Srl = new CarSensor(LightSide.RIGHT, LightDirection.LEFT, gui, 595, gui.spawnCoordRL.y);

            Sdr = new CarSensor(LightSide.DOWN, LightDirection.RIGHT, gui, gui.spawnCoordDR.x, 475);
            Sds = new CarSensor(LightSide.DOWN, LightDirection.STRAIGHT, gui, gui.spawnCoordDS.x, 475);
            Sdl = new CarSensor(LightSide.DOWN, LightDirection.LEFT, gui, gui.spawnCoordDL.x, 475);
        } else if (scenario.equals(Scenario.HARDWARE)) {
            // Voeg de sensoren toe
            Slr = new CarSensor(LightSide.LEFT, LightDirection.RIGHT, gui, gui.spawnCoordLR.x + 1, gui.spawnCoordLR.y);
            Sls1 = new CarSensor(LightSide.LEFT, LightDirection.STRAIGHT, gui, gui.spawnCoordLS1.x + 1,
                    gui.spawnCoordLS1.y);
            Sls2 = new CarSensor(LightSide.LEFT, LightDirection.STRAIGHT, gui, gui.spawnCoordLS2.x + 1,
                    gui.spawnCoordLS2.y);
            Sls3 = new CarSensor(LightSide.LEFT, LightDirection.STRAIGHT, gui, gui.spawnCoordLS3.x + 1,
                    gui.spawnCoordLS3.y);
            Sll = new CarSensor(LightSide.LEFT, LightDirection.LEFT, gui, gui.spawnCoordLL.x + 1, gui.spawnCoordLL.y);

            Sur = new CarSensor(LightSide.UP, LightDirection.RIGHT, gui, gui.spawnCoordUR.x, gui.spawnCoordUR.y + 1);
            Sus = new CarSensor(LightSide.UP, LightDirection.STRAIGHT, gui, gui.spawnCoordUS.x, gui.spawnCoordUS.y + 1);
            Sul = new CarSensor(LightSide.UP, LightDirection.LEFT, gui, gui.spawnCoordUL.x, gui.spawnCoordUL.y + 1);

            Srr = new CarSensor(LightSide.RIGHT, LightDirection.RIGHT, gui, gui.spawnCoordRR.x - 1, gui.spawnCoordRR.y);
            Srs1 = new CarSensor(LightSide.RIGHT, LightDirection.STRAIGHT, gui, gui.spawnCoordRS1.x - 1,
                    gui.spawnCoordRS1.y);
            Srs2 = new CarSensor(LightSide.RIGHT, LightDirection.STRAIGHT, gui, gui.spawnCoordRS2.x - 1,
                    gui.spawnCoordRS2.y);
            Srl = new CarSensor(LightSide.RIGHT, LightDirection.LEFT, gui, gui.spawnCoordRL.x - 1, gui.spawnCoordRL.y);

            Sdr = new CarSensor(LightSide.DOWN, LightDirection.RIGHT, gui, gui.spawnCoordDR.x, gui.spawnCoordDR.y - 1);
            Sds = new CarSensor(LightSide.DOWN, LightDirection.STRAIGHT, gui, gui.spawnCoordDS.x,
                    gui.spawnCoordDS.y - 1);
            Sdl = new CarSensor(LightSide.DOWN, LightDirection.LEFT, gui, gui.spawnCoordDL.x, gui.spawnCoordDL.y - 1);
        }
    }

    /**
     * Update methode: bepaald welke lichten wanneer op groen gaan
     */
    public void update() {
        // Als er een licht is met voorrang
        if (priorityLight != null) {
            // Check bij elk verkeerslicht of er een rij staat langer dan bij de auto met
            // voorrang. Zo ja zet dan alle lichten op rood behalve de nieuwe met voorrang
            // en de lichten die daar samen mee op groen kunnen.
            for (Light light : gui.lights) {
                if (light.sensedCars.size() > priorityLight.sensedCars.size()) {
                    priorityLight = light;
                    for (Light light1 : gui.lights) {
                        if (isConflicting(priorityLight, light1)) {
                            if (light1.state != LightState.RED) {
                                light1.toRed();
                            }
                        } else {
                            light1.toGreen();
                        }
                    }
                }
            }
            // Als er geen licht is met voorrang
        } else {
            // Check bij elk verkeerslicht of er een rij staat. Zo ja maak dat het licht met
            // voorrang en maak hem groen.
            for (Light light : gui.lights) {
                if (light.sensedCars.size() > 0) {
                    priorityLight = light;
                    priorityLight.toGreen();
                    for (Light light1 : gui.lights) {
                        if (!isConflicting(priorityLight, light1)) {
                            light1.toGreen();
                        }
                    }
                    return;
                }
            }
        }
    }

    /**
     * Methode om te controleren of 2 lichten tegelijkertijd op groen kunnen
     *
     * @param light1:
     *            het eerste licht
     * @param light2:
     *            het tweede licht:
     * @return: false wanneer ze wel op groen kunnen, true wanneer ze niet op groen
     *          kunnen
     */
    private boolean isConflicting(Light light1, Light light2) {
        if (light1 == Lll) {
            if (light2 == Lll) {
                return false;
            }
            if (light2 == Lls) {
                return false;
            }
            if (light2 == Llr) {
                return false;
            }
            if (light2 == Lur) {
                return false;
            }
        }
        if (light1 == Lls) {
            if (light2 == Lls) {
                return false;
            }
            if (light2 == Lll) {
                return false;
            }
            if (light2 == Llr) {
                return false;
            }
            if (light2 == Lur) {
                return false;
            }
        }
        if (light1 == Llr) {
            if (light2 == Llr) {
                return false;
            }
            if (light2 == Lll) {
                return false;
            }
            if (light2 == Lls) {
                return false;
            }
            if (light2 == Lur) {
                return false;
            }
        }
        if (light1 == Lul) {
            if (light2 == Lul) {
                return false;
            }
            if (light2 == Lus) {
                return false;
            }
            if (light2 == Lur) {
                return false;
            }
            if (light2 == Lrr) {
                return false;
            }
            if (light2 == Ldr) {
                return false;
            }
        }
        if (light1 == Lus) {
            if (light2 == Lus) {
                return false;
            }
            if (light2 == Lul) {
                return false;
            }
            if (light2 == Lur) {
                return false;
            }
            if (light2 == Lrr) {
                return false;
            }
            if (light2 == Ldr) {
                return false;
            }
        }
        if (light1 == Lur) {
            if (light2 == Lur) {
                return false;
            }
            if (light2 == Lul) {
                return false;
            }
            if (light2 == Lus) {
                return false;
            }
            if (light2 == Lrr) {
                return false;
            }
            if (light2 == Ldr) {
                return false;
            }
        }
        if (light1 == Lrl) {
            if (light2 == Lrl) {
                return false;
            }
            if (light2 == Lrs) {
                return false;
            }
            if (light2 == Lrr) {
                return false;
            }
            if (light2 == Ldr) {
                return false;
            }
        }
        if (light1 == Lrs) {
            if (light2 == Lrs) {
                return false;
            }
            if (light2 == Lrl) {
                return false;
            }
            if (light2 == Lrr) {
                return false;
            }
            if (light2 == Ldr) {
                return false;
            }
        }
        if (light1 == Lrr) {
            if (light2 == Lrr) {
                return false;
            }
            if (light2 == Lrl) {
                return false;
            }
            if (light2 == Lrr) {
                return false;
            }
            if (light2 == Ldr) {
                return false;
            }
        }
        if (light1 == Ldl) {
            if (light2 == Ldl) {
                return false;
            }
            if (light2 == Lds) {
                return false;
            }
            if (light2 == Ldr) {
                return false;
            }
            if (light2 == Lur) {
                return false;
            }
            if (light2 == Llr) {
                return false;
            }
        }
        if (light1 == Lds) {
            if (light2 == Ldl) {
                return false;
            }
            if (light2 == Lds) {
                return false;
            }
            if (light2 == Ldr) {
                return false;
            }
            if (light2 == Lur) {
                return false;
            }
            if (light2 == Llr) {
                return false;
            }
        }
        if (light1 == Ldr) {
            if (light2 == Ldl) {
                return false;
            }
            if (light2 == Lds) {
                return false;
            }
            if (light2 == Ldr) {
                return false;
            }
            if (light2 == Lur) {
                return false;
            }
            if (light2 == Llr) {
                return false;
            }
        }
        return true;
    }
}
