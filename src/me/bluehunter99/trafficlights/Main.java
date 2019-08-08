package me.bluehunter99.trafficlights;

import me.bluehunter99.trafficlights.car.Car;
import me.bluehunter99.trafficlights.car.CarSensor;
import me.bluehunter99.trafficlights.light.Light;
import me.bluehunter99.trafficlights.light.LightDirection;
import me.bluehunter99.trafficlights.light.LightSide;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * The Main class starts the application and is the manager of the dynamic lists containing information.
 *
 * @author BlueHunter99
 *
 */

public class Main {

    /**
     * Normal variables
     */
    public JFrame frame;
    public Render render;
    public CopyOnWriteArrayList<Car> cars;
    public CopyOnWriteArrayList<Light> lights;
    public CopyOnWriteArrayList<CarSensor> sensors;
    public CopyOnWriteArrayList<TestResult> results;
    public boolean stopped = false;
    public int tps = 100;
    public int multiplier = 1;
    private static int programWidth = 800;
    private static int programHeight = 600;

    /**
     * Car position variables
     * TODO Make car position variables user positionable
     */
    public int stopL = 200;
    public int stopU = 90;
    public int stopR = 520;
    public int stopD = 413;
    public Coordinate spawnCoordLL = new Coordinate(0, 260);
    public Coordinate spawnCoordLS1 = new Coordinate(0, 280);
    public Coordinate spawnCoordLS2 = new Coordinate(0, 300);
    public Coordinate spawnCoordLS3 = new Coordinate(0, 320);
    public Coordinate spawnCoordLR = new Coordinate(0, 340);
    public Coordinate spawnCoordUL = new Coordinate(360, -30);
    public Coordinate spawnCoordUS = new Coordinate(340, -30);
    public Coordinate spawnCoordUR = new Coordinate(320, -30);
    public Coordinate spawnCoordRL = new Coordinate(760, 240);
    public Coordinate spawnCoordRS1 = new Coordinate(760, 200);
    public Coordinate spawnCoordRS2 = new Coordinate(760, 220);
    public Coordinate spawnCoordRR = new Coordinate(760, 160);
    public Coordinate spawnCoordDL = new Coordinate(400, 530);
    public Coordinate spawnCoordDS = new Coordinate(420, 530);
    public Coordinate spawnCoordDR = new Coordinate(440, 530);

    /**
     * Start main thread
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Make a new JFrame
                    Main window = new Main();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Constructor
     */
    private Main() {
        // Initialize variables
        cars = new CopyOnWriteArrayList<Car>();
        lights = new CopyOnWriteArrayList<Light>();
        sensors = new CopyOnWriteArrayList<CarSensor>();
        results = new CopyOnWriteArrayList<TestResult>();
        frame = new JFrame();

        // Give the frame the following properties: Size, Centering, Resizing, Layout, Background, Exit on Close
        frame.setSize(programWidth, programHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        resetFrame();
    }

    /**
     * Method to store the results of the test in a textfile on the desktop
     *
     * @throws IOException
     *
     * TODO make storing this file optional
     * TODO make location of file user-assignable
     * TODO add in-application view of results
     * TODO seperate processing results from writing results
     */
    public void writeResults() throws IOException {
        // Make list to hold file content
        List<String> lines = new ArrayList<String>();

        // Make lists for the results for the different directions.
        List<TestResult> ll = new ArrayList<>();
        List<TestResult> ls = new ArrayList<>();
        List<TestResult> lr = new ArrayList<>();
        List<TestResult> ul = new ArrayList<>();
        List<TestResult> us = new ArrayList<>();
        List<TestResult> ur = new ArrayList<>();
        List<TestResult> rl = new ArrayList<>();
        List<TestResult> rs = new ArrayList<>();
        List<TestResult> rr = new ArrayList<>();
        List<TestResult> dl = new ArrayList<>();
        List<TestResult> ds = new ArrayList<>();
        List<TestResult> dr = new ArrayList<>();

        // Variables for more efficient if-statements
        LightSide left = LightSide.LEFT;
        LightSide up = LightSide.UP;
        LightSide right = LightSide.RIGHT;
        LightSide down = LightSide.DOWN;
        LightDirection LEFT = LightDirection.LEFT;
        LightDirection STRAIGHT = LightDirection.STRAIGHT;
        LightDirection RIGHT = LightDirection.RIGHT;

        // Top row of new test
        lines.add("----NEW TEST----");

        // Sort the different results into their own lists to calculate the average, display the singular result. Then remove the object from the list of results.
        for (TestResult result : results) {
            lines.add("Side: " + result.side + " | Direction: " + result.direction + " | Timer: " + result.timer);

            if (result.side == left && result.direction == LEFT) {
                ll.add(result);
            }
            if (result.side == left && result.direction == STRAIGHT) {
                ls.add(result);
            }
            if (result.side == left && result.direction == RIGHT) {
                lr.add(result);
            }
            if (result.side == up && result.direction == LEFT) {
                ul.add(result);
            }
            if (result.side == up && result.direction == STRAIGHT) {
                us.add(result);
            }
            if (result.side == up && result.direction == RIGHT) {
                ur.add(result);
            }
            if (result.side == right && result.direction == LEFT) {
                rl.add(result);
            }
            if (result.side == right && result.direction == STRAIGHT) {
                rs.add(result);
            }
            if (result.side == right && result.direction == RIGHT) {
                rr.add(result);
            }
            if (result.side == down && result.direction == LEFT) {
                dl.add(result);
            }
            if (result.side == down && result.direction == STRAIGHT) {
                ds.add(result);
            }
            if (result.side == down && result.direction == RIGHT) {
                dr.add(result);
            }

            results.remove(result);
        }

        // Add the average values to the file
        lines.add("Average where side: LEFT and direction: LEFT: " + getAverageTime(ll));
        lines.add("Average where side: LEFT and direction: STRAIGHT: " + getAverageTime(ls));
        lines.add("Average where side: LEFT and direction: RIGHT: " + getAverageTime(lr));
        lines.add("Average where side: UP and direction: LEFT: " + getAverageTime(ul));
        lines.add("Average where side: UP and direction: STRAIGHT: " + getAverageTime(us));
        lines.add("Average where side: UP and direction: RIGHT: " + getAverageTime(ur));
        lines.add("Average where side: RIGHT and direction: LEFT: " + getAverageTime(rl));
        lines.add("Average where side: RIGHT and direction: STRAIGHT: " + getAverageTime(rs));
        lines.add("Average where side: RIGHT and direction: RIGHT: " + getAverageTime(rr));
        lines.add("Average where side: DOWN and direction: LEFT: " + getAverageTime(dl));
        lines.add("Average where side: DOWN and direction: STRAIGHT: " + getAverageTime(ds));
        lines.add("Average where side: DOWN and direction: RIGHT: " + getAverageTime(dr));

        // Add line for end of test
        lines.add("----END----");

        // Make or edit the file
        Files.write(Paths.get(System.getProperty("user.home") + "/Desktop/Verkeerslichten_Resultaten.txt"), lines,
                StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    /**
     * Method to generate the average value from a list
     *
     * @param list: The list of which the average value has to be calculated
     * @return int: The average of the list, returns 0 if the list is empty
     */
    private int getAverageTime(List<TestResult> list) {
        if (list.size() == 0) {
            return 0;
        }

        int total = 0;

        for (TestResult result : list) {
            total += result.timer;
        }

        return total / list.size();
    }

    /**
     * Method to remove all of the objects from the screen.
     *
     * @param lblMap: the background object generated with the different scenarios
     */
    public void reset(JLabel lblMap) {
        try {
            writeResults();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Remove all car objects
        for (Car car : cars) {
            cars.remove(car);
        }

        // Remove all light objects
        for (Light light : lights) {
            lights.remove(light);
        }

        // Remove all sensor objects
        for (CarSensor sensor : sensors) {
            sensors.remove(sensor);
        }

        // Tell the update thread to stop
        stopped = true;

        // Reset the screen
        frame.remove(lblMap);
        frame.repaint();
        resetFrame();
    }

    /**
     * Method to paint the main menu screen
     * TODO change to English
     */
    public void resetFrame() {
        // Explanatory text
        JTextArea txtText = new JTextArea();
        txtText.setLocation(10, 46);
        txtText.setSize(764, 151);
        txtText.setWrapStyleWord(true);
        txtText.setLineWrap(true);
        txtText.setEditable(false);
        txtText.setText("This application is part of the final research project I did on traffic lights for my highschool. The junction shown in this simulation is of the Hollandweg and the Burgemeester Matsersingel. This research project was assigned by the Lorentz Lyceum in Arnhem and monitored by J. Anzion of the Candea College in Duiven.\r\n" +
                        "To test the custom software rules I made for this model press Software.\r\n" +
                        "To test the custom sensors I added for this model press Software.\r\n" +
                        "The results of the tests in the model will be logged in a file called TrafficLights_Results.txt which will appear on your desktop after stopping the simulation.\r\n" +
                        "The sliders below can be used to adjust the amount of cars spawning (spawn factor) and the speed at which the simulation is run (speed).");
        frame.getContentPane().add(txtText, BorderLayout.CENTER);

        // Title
        JLabel lblTitle = new JLabel("TrafficLights");
        lblTitle.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 25));
        lblTitle.setBounds(10, 11, 221, 32);
        frame.getContentPane().add(lblTitle);

        // Image
        JLabel pnlFoto = new JLabel(new ImageIcon(getClass().getResource("/Sources/intersection_menu_image.png")));
        pnlFoto.setBounds(474, 250, 300, 300);
        frame.getContentPane().add(pnlFoto);

        // Slider for ticks per second
        JSlider sliderTPS = new JSlider();
        sliderTPS.setPaintTicks(true);
        sliderTPS.setPaintLabels(true);
        sliderTPS.setMajorTickSpacing(50);
        sliderTPS.setValue(100);
        sliderTPS.setMaximum(500);
        sliderTPS.setBackground(Color.white);
        sliderTPS.setBounds(88, 250, 275, 50);
        sliderTPS.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                JSlider sliderTPS = (JSlider) evt.getSource();
                if (!sliderTPS.getValueIsAdjusting()) {
                    tps = sliderTPS.getValue();
                }
            }
        });
        frame.getContentPane().add(sliderTPS);

        // Label for slider for ticks per second
        JLabel lblTPS = new JLabel("Speed (%):");
        lblTPS.setBounds(5, 250, 100, 50);
        frame.getContentPane().add(lblTPS);

        // Slider for spawner multiplier
        JSlider sliderMuliplier = new JSlider();
        sliderMuliplier.setPaintTicks(true);
        sliderMuliplier.setPaintLabels(true);
        sliderMuliplier.setMajorTickSpacing(1);
        sliderMuliplier.setValue(1);
        sliderMuliplier.setMaximum(5);
        sliderMuliplier.setBackground(Color.white);
        sliderMuliplier.setBounds(88, 200, 275, 50);
        sliderMuliplier.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                JSlider sliderMultiplier = (JSlider) evt.getSource();
                if (!sliderMultiplier.getValueIsAdjusting()) {
                    multiplier = sliderMultiplier.getValue();
                }
            }
        });
        frame.getContentPane().add(sliderMuliplier);

        // Label for slider for spawner multiplier
        JLabel lblSpawn = new JLabel("Spawn Factor:");
        lblSpawn.setBounds(5, 200, 100, 50);
        frame.getContentPane().add(lblSpawn);

        // Button for hardware scenario
        JButton btnHardware = new JButton("Hardware");
        btnHardware.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runScenario(Scenario.HARDWARE);
            }
        });
        btnHardware.setFont(new Font("Monospaced", Font.PLAIN, 20));
        btnHardware.setBounds(88, 369, 275, 50);
        frame.getContentPane().add(btnHardware);

        // Button to exit application
        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnExit.setFont(new Font("Monospaced", Font.PLAIN, 20));
        btnExit.setBounds(88, 430, 275, 50);
        frame.getContentPane().add(btnExit);

        // Label for copyright
        JLabel lblCopyright = new JLabel("\u00A9 Joeri Meijer 2019");
        lblCopyright.setBounds(10, 536, 140, 14);
        frame.getContentPane().add(lblCopyright);

        // Button for software scenario
        JButton btnSoftware = new JButton("Software");
        btnSoftware.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                runScenario(Scenario.SOFTWARE);
            }
        });
        btnSoftware.setFont(new Font("Monospaced", Font.PLAIN, 20));
        btnSoftware.setBounds(88, 308, 275, 50);
        frame.getContentPane().add(btnSoftware);

        frame.repaint();
    }

    /**
     * Run a scenario
     *
     * @param scenario: the scenario enum type
     */
    public void runScenario(Scenario scenario) {
        // Make sure update will be running
        stopped = false;

        // Clear the screen
        frame.getContentPane().removeAll();
        frame.repaint();

        // Add a new update thread
        render = new Render(this);
        Update update = new Update(this, scenario);
        Thread updateThread = new Thread(update, "Update");

        // Start the scenario
        updateThread.start();
    }
}
