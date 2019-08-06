package me.bluehunter99.trafficlights;

import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * De CarSpawner klasse is de klasse die over het verschijnen van alle auto's
 * gaat.
 *
 * @author IK
 *
 */

public class CarSpawner {

    // Variabelen
    private Main gui;
    private Render render;

    /**
     * Constructor.
     *
     * @param gui:
     *            Main component
     * @param render:
     *            Render component
     */
    public CarSpawner(Main gui, Render render) {
        this.gui = gui;
        this.render = render;
    }

    /**
     * Run methode.
     */
    public void run() {
        // Maak een willekeurige waarde voor elke straat en elke richting binnen de
        // waarde die is berekend met het veldonderzoek (SPS).
        Random rand = new Random();
        test(rand.nextInt(45 / gui.multiplier), LightSide.LEFT, LightDirection.LEFT);
        test(rand.nextInt(16 / gui.multiplier), LightSide.LEFT, LightDirection.STRAIGHT);
        test(rand.nextInt(49 / gui.multiplier), LightSide.LEFT, LightDirection.RIGHT);
        test(rand.nextInt(95 / gui.multiplier), LightSide.UP, LightDirection.LEFT);
        test(rand.nextInt(41 / gui.multiplier), LightSide.UP, LightDirection.STRAIGHT);
        test(rand.nextInt(32 / gui.multiplier), LightSide.UP, LightDirection.RIGHT);
        test(rand.nextInt(50 / gui.multiplier), LightSide.RIGHT, LightDirection.LEFT);
        test(rand.nextInt(15 / gui.multiplier), LightSide.RIGHT, LightDirection.STRAIGHT);
        test(rand.nextInt(129 / gui.multiplier), LightSide.RIGHT, LightDirection.RIGHT);
        test(rand.nextInt(100 / gui.multiplier), LightSide.DOWN, LightDirection.LEFT);
        test(rand.nextInt(62 / gui.multiplier), LightSide.DOWN, LightDirection.STRAIGHT);
        test(rand.nextInt(47 / gui.multiplier), LightSide.DOWN, LightDirection.RIGHT);

    }

    /**
     * De test methode kijkt of er een auto mag verschijnen en laat die dan
     * verschijnen. Dit is gebaseerd op de positie waardes die zijn gedefinieerd in
     * de Main klasse. Wanneer er meerdere banen zijn loopt eerst de bovenste vol en
     * daarna de rest.
     *
     * @param testRandom:
     *            de meegegeven waarde
     * @param side:
     *            de straat
     * @param direction:
     *            de richting
     */
    private void test(int testRandom, LightSide side, LightDirection direction) {
        if (testRandom == 1) {
            boolean exit = false;
            switch (side) {
                case LEFT:
                    switch (direction) {
                        case LEFT:
                            for (Car car : gui.cars) {
                                if (car.posY == (260)) {
                                    if (car.posX < 40) {
                                        exit = true;
                                    }
                                }
                            }
                            if (exit == false) {
                                spawnCar(LightSide.LEFT, LightDirection.LEFT, gui.spawnCoordLL.x, gui.spawnCoordLL.y);
                            }
                            break;
                        case STRAIGHT:
                            boolean multi1 = false;
                            boolean multi2 = false;
                            boolean multi3 = false;
                            for (Car car : gui.cars) {
                                if (car.posY == (280)) {
                                    if (car.posX < 40) {
                                        multi1 = true;
                                    }
                                }
                                if (car.posY == (300)) {
                                    if (car.posX < 40) {
                                        multi2 = true;
                                    }
                                }
                                if (car.posY == (320)) {
                                    if (car.posX < 40) {
                                        multi2 = true;
                                    }
                                }
                            }
                            if (multi1 == false) {
                                spawnCar(LightSide.LEFT, LightDirection.STRAIGHT, gui.spawnCoordLS1.x, gui.spawnCoordLS1.y);
                            } else if (multi2 == false) {
                                spawnCar(LightSide.LEFT, LightDirection.STRAIGHT, gui.spawnCoordLS2.x, gui.spawnCoordLS2.y);
                            } else if (multi3 == false) {
                                spawnCar(LightSide.LEFT, LightDirection.STRAIGHT, gui.spawnCoordLS3.x, gui.spawnCoordLS3.y);
                            }
                            break;
                        case RIGHT:
                            for (Car car : gui.cars) {
                                if (car.posY == (340)) {
                                    if (car.posX < 40) {
                                        exit = true;
                                    }
                                }
                            }
                            if (exit == false) {
                                spawnCar(LightSide.LEFT, LightDirection.RIGHT, gui.spawnCoordLR.x, gui.spawnCoordLR.y);
                            }
                            break;
                    }
                    break;
                case UP:
                    switch (direction) {
                        case LEFT:
                            for (Car car : gui.cars) {
                                if (car.posX == 360) {
                                    if (car.posY < 20) {
                                        exit = true;
                                    }
                                }
                            }
                            if (exit == false) {
                                spawnCar(LightSide.UP, LightDirection.LEFT, gui.spawnCoordUL.x, gui.spawnCoordUL.y);
                            }
                            break;
                        case STRAIGHT:
                            for (Car car : gui.cars) {
                                if (car.posX == 340) {
                                    if (car.posY < 20) {
                                        exit = true;
                                    }
                                }
                            }
                            if (exit == false) {
                                spawnCar(LightSide.UP, LightDirection.STRAIGHT, gui.spawnCoordUS.x, gui.spawnCoordUS.y);
                            }
                            break;
                        case RIGHT:
                            for (Car car : gui.cars) {
                                if (car.posX == 320) {
                                    if (car.posY < 20) {
                                        exit = true;
                                    }
                                }
                            }
                            if (exit == false) {
                                spawnCar(LightSide.UP, LightDirection.RIGHT, gui.spawnCoordUR.x, gui.spawnCoordUR.y);
                            }
                            break;
                    }
                    break;
                case RIGHT:
                    switch (direction) {
                        case LEFT:
                            for (Car car : gui.cars) {
                                if (car.posY == 240) {
                                    if (car.posX > 720) {
                                        exit = true;
                                    }
                                }
                            }
                            if (exit == false) {
                                spawnCar(LightSide.RIGHT, LightDirection.LEFT, gui.spawnCoordRL.x, gui.spawnCoordRL.y);
                            }
                            break;
                        case STRAIGHT:
                            boolean multi1 = false;
                            boolean multi2 = false;
                            for (Car car : gui.cars) {
                                if (car.posY == 200) {
                                    if (car.posX > 720) {
                                        multi1 = true;
                                    }
                                }
                                if (car.posY == 220) {
                                    if (car.posX < 720) {
                                        multi2 = true;
                                    }
                                }
                            }
                            if (multi1 == false) {
                                spawnCar(LightSide.RIGHT, LightDirection.STRAIGHT, gui.spawnCoordRS1.x, gui.spawnCoordRS1.y);
                            } else if (multi2 == false) {
                                spawnCar(LightSide.RIGHT, LightDirection.STRAIGHT, gui.spawnCoordRS2.x, gui.spawnCoordRS2.y);
                            }
                            break;
                        case RIGHT:
                            for (Car car : gui.cars) {
                                if (car.posY == 160) {
                                    if (car.posX > 720) {
                                        exit = true;
                                    }
                                }
                            }
                            if (exit == false) {
                                spawnCar(LightSide.RIGHT, LightDirection.RIGHT, gui.spawnCoordRR.x, gui.spawnCoordRR.y);
                            }
                            break;
                    }
                    break;
                case DOWN:
                    switch (direction) {
                        case LEFT:
                            for (Car car : gui.cars) {
                                if (car.posX == 400) {
                                    if (car.posY > 530) {
                                        exit = true;
                                    }
                                }
                            }
                            if (exit == false) {
                                spawnCar(LightSide.DOWN, LightDirection.LEFT, gui.spawnCoordDL.x, gui.spawnCoordDL.y);
                            }
                            break;
                        case STRAIGHT:
                            for (Car car : gui.cars) {
                                if (car.posX == 420) {
                                    if (car.posY > 530) {
                                        exit = true;
                                    }
                                }
                            }
                            if (exit == false) {
                                spawnCar(LightSide.DOWN, LightDirection.STRAIGHT, gui.spawnCoordDS.x, gui.spawnCoordDS.y);
                            }
                            break;
                        case RIGHT:
                            for (Car car : gui.cars) {
                                if (car.posX == 440) {
                                    if (car.posY > 530) {
                                        exit = true;
                                    }
                                }
                            }
                            if (exit == false) {
                                spawnCar(LightSide.DOWN, LightDirection.RIGHT, gui.spawnCoordDR.x, gui.spawnCoordDR.y);
                            }
                            break;
                    }
                    break;
            }
        }
    }

    /**
     * Auto verschijning methode
     *
     * @param side
     */
    public void spawnCar(LightSide side, LightDirection direction, int posX, int posY) {
        // Maak een nieuwe auto
        Car car = new Car(side, direction, posX, posY, gui);

        // Tekent de auto de eerste keer voordat de reder klasse dat doet
        ImageIcon fileIcon = new ImageIcon(Main.class.getResource("/Sources/car_image.png"));
        car.oldLabel = new JLabel("");
        RotatedIcon carIcon = new RotatedIcon(fileIcon, car.rotation);
        car.oldLabel.setIcon(carIcon);
        car.oldLabel.setBounds(posX, posY, 100, 100);

        render.lblMap.add(car.oldLabel);
        gui.frame.repaint();

        gui.cars.add(car);
    }
}
