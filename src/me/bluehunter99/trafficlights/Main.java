package me.bluehunter99.trafficlights;

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
 * De Main klasse is de hoofdklasse van het programma, vanuit hier wordt alles
 * gestart en deze klasse beheerd ook alle lijsten waar de informatie in staat.
 *
 * @author Joeri Meijer
 *
 */

public class Main {

    /**
     * Normale Variabelen
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
     * Positievariabelen voor de auto's
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
     * Main thread gestart
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Maak een nieuwe frame
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
    public Main() {
        // Variabelen initializeren
        cars = new CopyOnWriteArrayList<Car>();
        lights = new CopyOnWriteArrayList<Light>();
        sensors = new CopyOnWriteArrayList<CarSensor>();
        results = new CopyOnWriteArrayList<TestResult>();
        frame = new JFrame();

        // Geef het frame de volgende eigenschappen: Formaat, Centreren, Niet verklein-
        // of vergrootbaar, Ongedecoreerd, Zonder Layout, Witte Achtergrond, Sluit af
        // wanneer hij wordt gesloten.
        frame.setSize(programWidth, programHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        resetFrame();
    }

    /**
     * Methode om de resultaten van de test in een textbestand op het bureaublad te
     * plaatsen.
     *
     * @throws IOException
     */
    public void writeResults() throws IOException {
        // Maak een lijst voor elke richting voor de gemiddelde waardes.
        List<String> lines = new ArrayList<String>();
        List<TestResult> ll = new ArrayList<TestResult>();
        List<TestResult> ls = new ArrayList<TestResult>();
        List<TestResult> lr = new ArrayList<TestResult>();
        List<TestResult> ul = new ArrayList<TestResult>();
        List<TestResult> us = new ArrayList<TestResult>();
        List<TestResult> ur = new ArrayList<TestResult>();
        List<TestResult> rl = new ArrayList<TestResult>();
        List<TestResult> rs = new ArrayList<TestResult>();
        List<TestResult> rr = new ArrayList<TestResult>();
        List<TestResult> dl = new ArrayList<TestResult>();
        List<TestResult> ds = new ArrayList<TestResult>();
        List<TestResult> dr = new ArrayList<TestResult>();

        // Variabelen voor makkelijkere if-statements
        LightSide left = LightSide.LEFT;
        LightSide up = LightSide.UP;
        LightSide right = LightSide.RIGHT;
        LightSide down = LightSide.DOWN;
        LightDirection LEFT = LightDirection.LEFT;
        LightDirection STRAIGHT = LightDirection.STRAIGHT;
        LightDirection RIGHT = LightDirection.RIGHT;

        // Bovenste lijn van niewe test
        lines.add("----NEW TEST----");

        // Maak voor elk resultaat een lijn in het bestand en voeg het resultaat toe aan
        // de correcte lijst. Verwijder daarna het object uit de lijst van resultaten.
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

        // Voeg de gemiddelde waardes toe aan het bestand
        lines.add("Gemiddelde bij side: LEFT en direction: LEFT: " + getAverageTime(ll));
        lines.add("Gemiddelde bij side: LEFT en direction: STRAIGHT: " + getAverageTime(ls));
        lines.add("Gemiddelde bij side: LEFT en direction: RIGHT: " + getAverageTime(lr));
        lines.add("Gemiddelde bij side: UP en direction: LEFT: " + getAverageTime(ul));
        lines.add("Gemiddelde bij side: UP en direction: STRAIGHT: " + getAverageTime(us));
        lines.add("Gemiddelde bij side: UP en direction: RIGHT: " + getAverageTime(ur));
        lines.add("Gemiddelde bij side: RIGHT en direction: LEFT: " + getAverageTime(rl));
        lines.add("Gemiddelde bij side: RIGHT en direction: STRAIGHT: " + getAverageTime(rs));
        lines.add("Gemiddelde bij side: RIGHT en direction: RIGHT: " + getAverageTime(rr));
        lines.add("Gemiddelde bij side: DOWN en direction: LEFT: " + getAverageTime(dl));
        lines.add("Gemiddelde bij side: DOWN en direction: STRAIGHT: " + getAverageTime(ds));
        lines.add("Gemiddelde bij side: DOWN en direction: RIGHT: " + getAverageTime(dr));

        // Geef aan dat de test is beeïndigd
        lines.add("----END----");

        // Maak/edit het bestand
        Files.write(Paths.get(System.getProperty("user.home") + "/Desktop/Verkeerslichten_Resultaten.txt"), lines,
                StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    /**
     * Methode om het gemiddelde van een lijst te krijgen. Wordt gebruikt om de
     * resultaten van de test overzichtelijk te maken. Hij telt wat de totale
     * hoeveelheid is en deelt dat dan door hoeveel waardes er zijn.
     *
     * @param list:
     *            lijst waarvan het gemiddelde wordt gevraagd
     * @return
     */
    private int getAverageTime(List<TestResult> list) {
        int total = 0;

        for (TestResult result : list) {
            total += result.timer;
        }

        if (list.size() != 0) {
            return total / list.size();
        } else {
            return 0;
        }
    }

    /**
     * Methode om alle waardes en het scherm te resetten.
     *
     * @param lblMap:
     *            het achtergrond object bij de software en hardware scenario's
     */
    public void reset(JLabel lblMap) {
        try {
            writeResults();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Verwijder alle auto's
        for (Car car : cars) {
            cars.remove(car);
        }

        // Verwijder alle lichten
        for (Light light : lights) {
            lights.remove(light);
        }

        // Verwijder alle sensoren
        for (CarSensor sensor : sensors) {
            sensors.remove(sensor);
        }

        // Vertel aan update thread dat er gestopt moet worden
        stopped = true;

        // Reset het scherm
        frame.remove(lblMap);
        frame.repaint();
        resetFrame();
    }

    /**
     * Klasse om alles op het beginscherm te zetten.
     */
    public void resetFrame() {
        // Uitleg tekst
        JTextArea txtTekst = new JTextArea();
        txtTekst.setLocation(10, 46);
        txtTekst.setSize(764, 151);
        txtTekst.setWrapStyleWord(true);
        txtTekst.setLineWrap(true);
        txtTekst.setEditable(false);
        txtTekst.setText(
                "Dit is een programma dat hoort bij een profielwerkstuk over de verkeerslichten op het kruispunt van de Hollandweg en de Burgemeester Matsersingel. Dit profielwerkstuk was in opdracht van het Lorentz Lyceum in Arnhem en werd begeleid door J. Anzion van het Candea College in Duiven.\r\nEr hoort nog een tekst bestand bij dit programma wat alle uitleg geeft bij het programma en het proces verduidelijkt.\r\nOm het programma te testen met softwarematige verbeteringen, druk dan op Software.\r\nOm het programma te testen met hardwarematige verbeteringen, druk dan op Hardware.\r\nDe resultaten van de test zijn te zien in het bestandje Verkeerslichten_Resultaten.txt wat op uw bureaublad verschijnt na het stopzetten van het programma.");
        frame.getContentPane().add(txtTekst, BorderLayout.CENTER);

        // Titel
        JLabel lblTitle = new JLabel("Verkeerslichten");
        lblTitle.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 25));
        lblTitle.setBounds(10, 11, 221, 32);
        frame.getContentPane().add(lblTitle);

        // Foto
        JLabel pnlFoto = new JLabel(new ImageIcon(getClass().getResource("/Sources/intersection_menu_image.png")));
        pnlFoto.setBounds(474, 250, 300, 300);
        frame.getContentPane().add(pnlFoto);

        // Schuiver voor ticks per seconde
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

        // Label voor schuiver voor ticks per seconde
        JLabel lblTPS = new JLabel("Snelheid (%):");
        lblTPS.setBounds(5, 250, 100, 50);
        frame.getContentPane().add(lblTPS);

        // Schuiver voor spawner multiplier
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
                JSlider sliderMuliplier = (JSlider) evt.getSource();
                if (!sliderMuliplier.getValueIsAdjusting()) {
                    multiplier = sliderMuliplier.getValue();
                }
            }
        });
        frame.getContentPane().add(sliderMuliplier);

        // Label voor schuiver voor spawner multiplier
        JLabel lblSpawn = new JLabel("Spawn Factor:");
        lblSpawn.setBounds(5, 200, 100, 50);
        frame.getContentPane().add(lblSpawn);

        // Knop voor hardware scenario
        JButton btnHardware = new JButton("Hardware");
        btnHardware.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runScenario(Scenario.HARDWARE);
            }
        });
        btnHardware.setFont(new Font("Monospaced", Font.PLAIN, 20));
        btnHardware.setBounds(88, 369, 275, 50);
        frame.getContentPane().add(btnHardware);

        // Knop om het programma te sluiten
        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnExit.setFont(new Font("Monospaced", Font.PLAIN, 20));
        btnExit.setBounds(88, 430, 275, 50);
        frame.getContentPane().add(btnExit);

        // Label voor copyright
        JLabel lblCopyright = new JLabel("\u00A9 Joeri Meijer 2018");
        lblCopyright.setBounds(10, 536, 140, 14);
        frame.getContentPane().add(lblCopyright);

        // Knop voor software scenario
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
     * Run een scenario.
     *
     * @param scenario:
     *            de naam van het scenario.
     */
    public void runScenario(Scenario scenario) {
        // Seintje aan update dat er niet gestopt hoeft et worden
        stopped = false;

        // Haal het scherm leeg
        frame.getContentPane().removeAll();
        frame.repaint();

        // Maak een nieuwe thread voor de update klasse zodat die op zichzelf draait
        render = new Render(this);
        Update update = new Update(this, scenario);
        Thread updateThread = new Thread(update, "Update");

        // Start het scenario
        updateThread.start();
    }
}
