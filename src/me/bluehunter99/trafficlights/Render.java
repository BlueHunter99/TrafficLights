package me.bluehunter99.trafficlights;

import me.bluehunter99.trafficlights.car.Car;
import me.bluehunter99.trafficlights.light.Light;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * The render class is responsible for rendering all the content to the screen
 *
 * @author BlueHunter99
 *
 */

public class Render {

    /**
     * Variables
     */
    private Main gui;
    public JLabel lblMap;

    /**
     * Constructor.
     *
     * @param gui: Main component
     */
    public Render(Main gui) {
        this.gui = gui;

        // Add background image for the scenario on which the objects can be rendered
        lblMap = new JLabel("");
        lblMap.setIcon(new ImageIcon(Main.class.getResource("/Sources/intersection_image.png")));
        lblMap.setBounds(0, 0, 800, 600);
        lblMap.setLayout(null);

        // Button to return to main menu
        JButton btnClose = new JButton("Back");
        btnClose.setBounds(700, 535, 80, 20);
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                gui.reset(lblMap);
            }
        });
        btnClose.setVisible(true);
        lblMap.add(btnClose);
        gui.frame.add(lblMap);
        gui.frame.repaint();
    }

    /**
     * Run method.
     */
    public void run() {
        // Remove the old label for the car objects and repaint the new one
        for (int i = 0; i < gui.cars.size(); i++) {
            Car car = gui.cars.get(i);

            if (car.stopped == false) {
                lblMap.remove(car.oldLabel);

                ImageIcon fileIcon = new ImageIcon(Main.class.getResource("/Sources/car_image.png"));
                car.oldLabel = new JLabel("");
                RotatedIcon carIcon = new RotatedIcon(fileIcon, car.rotation);

                car.oldLabel.setIcon(carIcon);
                car.oldLabel.setBounds(car.posX, car.posY, 100, 100);

                lblMap.add(car.oldLabel);
            }
        }

        // Draw every light object with it's own values
        for (int i = 0; i < gui.lights.size(); i++) {
            Light light = gui.lights.get(i);

            light.label.setBounds(light.posX, light.posY, 100, 100);
        }

        gui.frame.repaint();
    }

    /**
     * Method to remove cars from the view
     *
     * @param car: the target car
     */
    public void removeCar(Car car) {
        lblMap.remove(car.oldLabel);
        gui.cars.remove(car);
    }
}
