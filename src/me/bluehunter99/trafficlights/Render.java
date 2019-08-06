package me.bluehunter99.trafficlights;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * De render klasse zorgt ervoor dat alle objecten verschijnen.
 *
 * @author IK
 *
 */

public class Render {

    /**
     * Variabelen
     */
    private Main gui;
    public JLabel lblMap;

    /**
     * Constructor.
     *
     * @param gui:
     *            Main component
     */
    public Render(Main gui) {
        this.gui = gui;

        // Maak achtergrondafbeelding die bereikbaar is vanaf andere klassen.
        lblMap = new JLabel("");
        lblMap.setIcon(new ImageIcon(Main.class.getResource("/Sources/intersection_image.png")));
        lblMap.setBounds(0, 0, 800, 600);
        lblMap.setLayout(null);

        // Maak knop om terug te gaan naar het hoofdmenu
        JButton btnClose = new JButton("Back");
        btnClose.setBounds(720, 580, 80, 20);
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                gui.reset(lblMap);
            }
        });
        lblMap.add(btnClose);
        gui.frame.repaint();
    }

    /**
     * Run methode.
     */
    public void run() {
        // Voeg achtergrond afbeelding toe
        gui.frame.add(lblMap);

        // Teken elke auto met zijn eigen waardes en verwijder het oude plaatje
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

        // Teken elk verkeerslicht met zijn eigen waardes
        for (int i = 0; i < gui.lights.size(); i++) {
            Light light = gui.lights.get(i);

            light.label.setBounds(light.posX, light.posY, 100, 100);
        }

        gui.frame.repaint();
    }

    /**
     * Methode om auto's van het scherm te verwijderen
     *
     * @param car:
     *            de gewilde auto
     */
    public void removeCar(Car car) {
        lblMap.remove(car.oldLabel);
        gui.cars.remove(car);
    }
}
