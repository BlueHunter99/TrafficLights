package me.bluehunter99.trafficlights;

import javax.swing.JLabel;

/**
 *
 * De Car klasse is de klasse die elke auto heeft. Hierin staat hoe hij moet
 * reageren op stoplichten en hoe hij getekend moet zijn.
 *
 * @author Joeri
 *
 */

public class Car {

    // Variabelen
    public LightSide side;
    public LightDirection direction;
    public int rotation;
    public Light conflictLight;
    public boolean stopped = false;
    public int timerCount;
    public int timer;
    public int posX;
    public int posY;
    public JLabel oldLabel;
    private Main gui;
    private boolean passedLight = false;

    /**
     * Constructor
     *
     * @param side:
     *            bij welke straat begint de auto?
     * @param direction:
     *            welke kant gaat de auto op?
     * @param posX:
     *            wat is de begin X positie?
     * @param posY:
     *            wat is de begin Y positie?
     * @param gui:
     *            Main component
     */
    public Car(LightSide side, LightDirection direction, int posX, int posY, Main gui) {
        // Variabelen initializeren
        this.side = side;
        this.direction = direction;
        this.posX = posX;
        this.posY = posY;
        this.gui = gui;

        // Draai de sprite in de goede richting
        switch (side) {
            case LEFT:
                rotation = 0;
                break;
            case UP:
                rotation = 90;
                break;
            case RIGHT:
                rotation = 180;
                break;
            case DOWN:
                rotation = 270;
                break;
        }

        // Stel in welk verkeerslicht bij deze auto hoort
        for (Light light : gui.lights) {
            if (side == light.side) {
                if (direction == light.direction) {
                    conflictLight = light;
                }
            }
        }
    }

    /**
     * Update methode. Hierin wordt bepaald hoe de auto moet bewegen.
     */
    public void update() {
        // Timer zodat de auto niet meteen begint met bewegen
        if (timerCount == 100) {
            timerCount = 0;
            timer++;
        }
        timerCount++;

        // Als de auto buiten het scherm is dan moet hij verwijderd worden om resources
        // te besparen
        if (posX < -40 || posX > 840 || posY < -80 || posY > 640) {
            gui.results.add(new TestResult(side, direction, timer));
            gui.cars.remove(this);
        } else {
            // Als het licht groen of oranje is en niet de auto er niet voorbij is
            if (passedLight || conflictLight.state == LightState.GREEN || conflictLight.state == LightState.YELLOW) {
                // Switch statement voor elke straat
                switch (side) {
                    case LEFT:
                        // Beweeg de auto vanaf bepaalde posities op een bepaalde manier
                        if (direction == LightDirection.LEFT) {
                            if (posX > 400 && posX < 420) {
                                rotation -= 5;
                                posY--;
                                posX++;
                            } else if (posX <= 400) {
                                posX++;
                            } else {
                                rotation = 270;
                                posY--;
                            }
                        } else if (direction == LightDirection.RIGHT) {
                            if (posX > 320 && posX < 340) {
                                rotation += 5;
                                posY++;
                                posX++;
                            } else if (posX <= 320) {
                                posX++;
                            } else {
                                rotation = 90;
                                posY++;
                            }
                        } else {
                            posX++;
                        }

                        // Als de auto voorbij het verkeerslicht is dan wordt er een variabele omgezet
                        // en wordt de auto uit de wachtrij gehaald
                        if (posX > gui.stopL) {
                            conflictLight.stoppedCars.remove(this);
                            conflictLight.sensedCars.remove(this);
                            passedLight = true;
                        }
                        break;
                    case UP:
                        // Beweeg de auto vanaf bepaalde posities op een bepaalde manier
                        if (direction == LightDirection.LEFT) {
                            if (posY > 260 && posY < 280) {
                                rotation -= 5;
                                posY++;
                                posX++;
                            } else if (posY <= 260) {
                                posY++;
                            } else {
                                rotation = 0;
                                posX++;
                            }
                        } else if (direction == LightDirection.RIGHT) {
                            if (posY > 180 && posY < 200) {
                                rotation += 5;
                                posX--;
                                posY++;
                            } else if (posY <= 180) {
                                posY++;
                            } else {
                                rotation = 180;
                                posX--;
                            }
                        } else {
                            posY++;
                        }

                        // Als de auto voorbij het verkeerslicht is dan wordt er een variabele omgezet
                        // en wordt de auto uit de wachtrij gehaald
                        if (posY > gui.stopU) {
                            conflictLight.stoppedCars.remove(this);
                            conflictLight.sensedCars.remove(this);
                            passedLight = true;
                        }
                        break;
                    case RIGHT:
                        // Beweeg de auto vanaf bepaalde posities op een bepaalde manier
                        if (direction == LightDirection.LEFT) {
                            if (posX > 340 && posX < 360) {
                                rotation -= 5;
                                posY++;
                                posX--;
                            } else if (posX >= 340) {
                                posX--;
                            } else {
                                rotation = 90;
                                posY++;
                            }
                        } else if (direction == LightDirection.RIGHT) {
                            if (posX > 420 && posX < 440) {
                                rotation += 5;
                                posY--;
                                posX--;
                            } else if (posX >= 420) {
                                posX--;
                            } else {
                                rotation = 270;
                                posY--;
                            }
                        } else {
                            posX--;
                        }

                        // Als de auto voorbij het verkeerslicht is dan wordt er een variabele omgezet
                        // en wordt de auto uit de wachtrij gehaald
                        if (posX < gui.stopR) {
                            conflictLight.stoppedCars.remove(this);
                            conflictLight.sensedCars.remove(this);
                            passedLight = true;
                        }
                        break;
                    case DOWN:
                        // Beweeg de auto vanaf bepaalde posities op een bepaalde manier
                        if (direction == LightDirection.LEFT) {
                            if (posY > 220 && posY < 240) {
                                rotation -= 5;
                                posY--;
                                posX--;
                            } else if (posY >= 220) {
                                posY--;
                            } else {
                                rotation = 180;
                                posX--;
                            }
                        } else if (direction == LightDirection.RIGHT) {
                            if (posY > 320 && posY < 340) {
                                rotation += 5;
                                posX++;
                                posY--;
                            } else if (posY >= 320) {
                                posY--;
                            } else {
                                rotation = 0;
                                posX++;
                            }
                        } else {
                            posY--;
                        }

                        // Als de auto voorbij het verkeerslicht is dan wordt er een variabele omgezet
                        // en wordt de auto uit de wachtrij gehaald
                        if (posY < gui.stopD) {
                            conflictLight.stoppedCars.remove(this);
                            conflictLight.sensedCars.remove(this);
                            passedLight = true;
                        }
                        break;
                }
                // Als het verkeerslicht rood is
            } else if (conflictLight.state == LightState.RED) {
                // Switch statement voor de verschillende straten
                switch (side) {
                    case LEFT:
                        // Als de auto nog niet stil staat
                        if (stopped == false) {
                            int distance = 0;
                            // Bepaal de positie om te stoppen gebaseerd op de hoeveelheid auto's die in de
                            // rij staat te wachten
                            switch (conflictLight.stoppedCars.size()) {
                                case 0:
                                    distance = gui.stopL;
                                    break;
                                case 1:
                                    distance = gui.stopL - 40;
                                    break;
                                case 2:
                                    distance = gui.stopL - 80;
                                    break;
                                case 3:
                                    distance = gui.stopL - 120;
                                    break;
                                case 4:
                                    distance = gui.stopL - 160;
                                    break;
                                case 5:
                                    distance = gui.stopL - 200;
                                    break;
                            }
                            // Bij deze richting zijn 3 banen en er wordt dus 3 keer geteld hoeveel auto's
                            // staan te wachten in de rijen
                            if (conflictLight.stoppedCars.size() > 5 && conflictLight.stoppedCars.size() < 12
                                    && direction == LightDirection.STRAIGHT) {
                                switch (conflictLight.stoppedCars.size()) {
                                    case 6:
                                        distance = gui.stopL;
                                        break;
                                    case 7:
                                        distance = gui.stopL - 40;
                                        break;
                                    case 8:
                                        distance = gui.stopL - 80;
                                        break;
                                    case 9:
                                        distance = gui.stopL - 120;
                                        break;
                                    case 10:
                                        distance = gui.stopL - 160;
                                        break;
                                    case 11:
                                        distance = gui.stopL - 200;
                                        break;
                                }
                            } else if (conflictLight.stoppedCars.size() > 11 && conflictLight.stoppedCars.size() < 18
                                    && direction == LightDirection.STRAIGHT) {
                                switch (conflictLight.stoppedCars.size()) {
                                    case 12:
                                        distance = gui.stopL;
                                        break;
                                    case 13:
                                        distance = gui.stopL - 40;
                                        break;
                                    case 14:
                                        distance = gui.stopL - 80;
                                        break;
                                    case 15:
                                        distance = gui.stopL - 120;
                                        break;
                                    case 16:
                                        distance = gui.stopL - 160;
                                        break;
                                    case 17:
                                        distance = gui.stopL - 200;
                                        break;
                                }
                            } else {
                                // NIET GEÏMPLEMENTEERD: auto in een lijst zetten om later te spawnen wanneer er
                                // ruimte is
                                conflictLight.addWaitingCar(this);
                            }

                            // Als er nog niet gestopt hoeft te worden dan rijd de auto door, zo wel dan
                            // wordt de auto toegevoegd aan de lijst met gestopte auto's.
                            if (posX < distance) {
                                posX++;
                            } else {
                                stopped = true;
                                conflictLight.addStoppedCar(this);
                            }
                        }
                        break;
                    case UP:
                        // Als de auto nog niet stil staat
                        if (stopped == false) {
                            int distance = 0;
                            // Bepaal de positie om te stoppen gebaseerd op de hoeveelheid auto's die in de
                            // rij staat te wachten
                            switch (conflictLight.stoppedCars.size()) {
                                case 0:
                                    distance = gui.stopU;
                                    break;
                                case 1:
                                    distance = gui.stopU - 40;
                                    break;
                                case 2:
                                    distance = gui.stopU - 80;
                                    break;
                            }
                            // NIET GEÏMPLEMENTEERD: auto in een lijst zetten om later te spawnen wanneer er
                            // ruimte is
                            if (conflictLight.stoppedCars.size() > 2) {
                                conflictLight.addWaitingCar(this);
                            } else {
                                // Als er nog niet gestopt hoeft te worden dan rijd de auto door, zo wel dan
                                // wordt de auto toegevoegd aan de lijst met gestopte auto's.
                                if (posY < distance) {
                                    posY++;
                                } else {
                                    stopped = true;
                                    conflictLight.addStoppedCar(this);
                                }
                            }
                        }
                        break;
                    case RIGHT:
                        // Als de auto nog niet stil staat
                        if (stopped == false) {
                            // Bepaal de positie om te stoppen gebaseerd op de hoeveelheid auto's die in de
                            // rij staat te wachten Bij deze richting zijn 2 banen en er wordt dus 2 keer
                            // geteld hoeveel auto's staan te wachten in de rijen
                            int amount = conflictLight.stoppedCars.size();
                            int distance = 0;
                            if (amount == 0) {
                                distance = gui.stopR;
                            } else if (amount == 1) {
                                distance = gui.stopR + 40;
                            } else if (amount == 2) {
                                distance = gui.stopR + 80;
                            } else if (amount == 3) {
                                distance = gui.stopR + 120;
                            } else if (amount == 4) {
                                distance = gui.stopR + 160;
                            } else if (amount == 5) {
                                distance = gui.stopR + 200;
                            } else if (amount == 6) {
                                distance = gui.stopR + 240;
                            } else if (direction == LightDirection.STRAIGHT) {
                                if (amount == 7) {
                                    distance = gui.stopR;
                                } else if (amount == 8) {
                                    distance = gui.stopR + 40;
                                } else if (amount == 9) {
                                    distance = gui.stopR + 80;
                                } else if (amount == 10) {
                                    distance = gui.stopR + 120;
                                } else if (amount == 11) {
                                    distance = gui.stopR + 160;
                                } else if (amount == 12) {
                                    distance = gui.stopR + 200;
                                } else if (amount == 13) {
                                    distance = gui.stopR + 240;
                                } else {
                                    // NIET GEÏMPLEMENTEERD: auto in een lijst zetten om later te spawnen wanneer er
                                    // ruimte is
                                    conflictLight.addWaitingCar(this);
                                    gui.render.removeCar(this);
                                }
                            } else {
                                // NIET GEÏMPLEMENTEERD: auto in een lijst zetten om later te spawnen wanneer er
                                // ruimte is
                                conflictLight.addWaitingCar(this);
                                gui.render.removeCar(this);
                            }
                            // Als er nog niet gestopt hoeft te worden dan rijd de auto door, zo wel dan
                            // wordt de auto toegevoegd aan de lijst met gestopte auto's.
                            if (posX > distance) {
                                posX--;
                            } else {
                                stopped = true;
                                conflictLight.addStoppedCar(this);
                            }
                        }
                        break;
                    case DOWN:
                        // Als de auto nog niet stil staat
                        if (stopped == false) {
                            // Bepaal de positie om te stoppen gebaseerd op de hoeveelheid auto's die in de
                            // rij staat te wachten
                            int distance = 0;
                            switch (conflictLight.stoppedCars.size()) {
                                case 0:
                                    distance = gui.stopD;
                                    break;
                                case 1:
                                    distance = gui.stopD + 40;
                                    break;
                                case 2:
                                    distance = gui.stopD + 80;
                                    break;
                            }
                            if (conflictLight.stoppedCars.size() > 2) {
                                // NIET GEÏMPLEMENTEERD: auto in een lijst zetten om later te spawnen wanneer er
                                // ruimte is
                                conflictLight.addWaitingCar(this);
                            } else {
                                // Als er nog niet gestopt hoeft te worden dan rijd de auto door, zo wel dan
                                // wordt de auto toegevoegd aan de lijst met gestopte auto's.
                                if (posY > distance) {
                                    posY--;
                                } else {
                                    stopped = true;
                                    conflictLight.addStoppedCar(this);
                                }
                            }
                        }
                        break;
                }
            }
        }
    }
}
