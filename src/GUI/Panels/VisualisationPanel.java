package GUI.Panels;

import Auth.AuthResponse;
import Auth.Session;
import Command.Serializer;
import Data.*;
import client.Connection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;


import static GUI.Frames.ExtendableJFrame.currentDateTimeFormatter;
import static GUI.Frames.TableFrame.currentUserList;


public class VisualisationPanel extends JPanel {
    private CopyOnWriteArrayList<HumanBeing> tempUserList = new CopyOnWriteArrayList<>();
    private Connection connection;
    private Session session;
    private javax.swing.Timer timer;
    private HashMap<String, Color> usersColor = new HashMap<>();
    private JTextField name_text = new JTextField(10);
    private JTextField coordinate_x_text = new JTextField(10);
    private JTextField coordinate_y_text = new JTextField(10);
    private JTextField realHero_text = new JTextField(10);
    private JTextField hasToothpick_text = new JTextField(10);
    private JTextField impactSpeed_text = new JTextField(10);
    private JTextField soundtrackName_text = new JTextField(10);
    private JTextField weaponType_text = new JTextField(10);
    private JTextField mood_text = new JTextField(10);
    private JTextField car_text = new JTextField(10);
    private JDialog d = new JDialog();
    CopyOnWriteArrayList<HumanBeing> createData;

    private Graphics2D g2d = null;


    private String name;
    private Integer x;
    private Long y;
    private Boolean realHero;
    private Long impactSpeed;
    private Boolean hasToothpick;
    private String soundtrackName;
    private WeaponType weaponType;
    private Mood mood;
    private boolean car;

    public VisualisationPanel(Connection connection, Session session) {
        this.connection = connection;
        this.session = session;
        setPreferredSize(new Dimension(513, 513));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                for (HumanBeing hb : currentUserList) {
                    int orgX = hb.getCoordinates().getX();
                    Long orgY = hb.getCoordinates().getY();
                    if (x >= orgX && x <= orgX + 20 && y >= orgY && y <= orgY + 20) {
                        showHumanBeing(getById(hb.getId()));
                    }
                }
            }
        });

        timer = new javax.swing.Timer(10, (ActionEvent e) -> {
            if (g2d == null) return;
            repaint();
        });
        timer.start();

    }
    boolean drawSpark = false;
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g2d = (Graphics2D) g;
        if (currentUserList.size() > tempUserList.size()) {
            createData = (CopyOnWriteArrayList<HumanBeing>) currentUserList.clone();
            for (HumanBeing hb : currentUserList) {
                if (tempUserList.contains(hb)) {
                    createData.remove(hb);
                }
            }
            drawElements();
            drawSpark = true;


        }
        else{
            if(drawSpark) {
                drawCreation(createData);
                int delayInMilliseconds = 1000*1;
                ActionListener taskPerformer = e -> {
                    drawSpark = false;
                    ((Timer) e.getSource()).stop();
                };

                Timer t = new Timer(delayInMilliseconds, taskPerformer);
                t.setRepeats(false); // ensure it's a one-time execution
                t.start();
            }

            drawElements();
        }
        tempUserList = (CopyOnWriteArrayList<HumanBeing>) currentUserList.clone();
    }

    public javax.swing.Timer getTimer() {
        return this.timer;
    }

    public HumanBeing getById(Long id) {
        return currentUserList.stream().filter(o -> o.getId() == id).findFirst().get();
    }

    public void showHumanBeing(HumanBeing hb) {


        LinkedList<JPanel> panels = new LinkedList<>();

        JLabel id_label = new JLabel("ID: " + hb.getId());
        JPanel id_panel = new JPanel();
        id_panel.add(id_label);
        panels.add(id_panel);


        JLabel name_label = new JLabel("Name: ");
        JPanel name_panel = new JPanel();
        name_text.setToolTipText("Name of human being");
        name_panel.add(name_label);
        name_panel.add(Box.createRigidArea(new Dimension(10, 0)));
        name_panel.add(name_text);
        panels.add(name_panel);
        name_text.setText(hb.getName());

        String time = hb.getCreationDate().toString().substring(0, 19);
        time = time.replace("T", " ");
        LocalDateTime datetime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        JLabel date_label = new JLabel("Creation date: " + datetime.format(currentDateTimeFormatter));
        JPanel date_panel = new JPanel();
        date_panel.add(date_label);
        panels.add(date_panel);


        JLabel coordinate_x_label = new JLabel("Coordinate X: ");
        JPanel coordinate_x_panel = new JPanel();
        coordinate_x_text.setToolTipText("Not null, must be a positive digit, must be lower 494");
        coordinate_x_panel.add(coordinate_x_label);
        coordinate_x_panel.add(Box.createRigidArea(new Dimension(10, 0)));
        coordinate_x_panel.add(coordinate_x_text);
        panels.add(coordinate_x_panel);
        coordinate_x_text.setText(String.valueOf(hb.getCoordinates().getX()));

        JLabel coordinate_y_label = new JLabel("Coordinate Y: ");
        JPanel coordinate_y_panel = new JPanel();
        coordinate_y_panel.setLayout(new BoxLayout(coordinate_y_panel, BoxLayout.X_AXIS));
        coordinate_y_text.setToolTipText("Not null, must be a positive digit, must be lower 494");
        coordinate_y_panel.add(coordinate_y_label);
        coordinate_y_panel.add(Box.createRigidArea(new Dimension(10, 0)));
        coordinate_y_panel.add(coordinate_y_text);
        panels.add(coordinate_y_panel);
        coordinate_y_text.setText(String.valueOf(hb.getCoordinates().getY()));


        JLabel realHero_label = new JLabel("Real Hero: ");
        JPanel real_hero_panel = new JPanel();
        realHero_text.setToolTipText("Да/Нет");
        real_hero_panel.add(realHero_label);
        real_hero_panel.add(Box.createRigidArea(new Dimension(10, 0)));
        real_hero_panel.add(realHero_text);
        panels.add(real_hero_panel);
        realHero_text.setText(String.valueOf(hb.isRealHero()));

        JLabel impact_speed_label = new JLabel("Impact speed: ");
        JPanel impact_speed_panel = new JPanel();
        impactSpeed_text.setToolTipText("Impact speed must be positive number and lower 573!");
        impact_speed_panel.add(impact_speed_label);
        impact_speed_panel.add(Box.createRigidArea(new Dimension(10, 0)));
        impact_speed_panel.add(impactSpeed_text);
        panels.add(impact_speed_panel);
        impactSpeed_text.setText(String.valueOf(hb.getImpactSpeed()));

        JLabel hasToothpick_label = new JLabel("Existence of toothpick: ");
        JPanel hasToothpick_panel = new JPanel();
        hasToothpick_text.setToolTipText("Да/Нет");
        hasToothpick_panel.add(hasToothpick_label);
        hasToothpick_panel.add(Box.createRigidArea(new Dimension(10, 0)));
        hasToothpick_panel.add(hasToothpick_text);
        panels.add(hasToothpick_panel);
        hasToothpick_text.setText(String.valueOf(hb.getHasToothpick()));

        JLabel soundtrackName_label = new JLabel("Soundtrack name: ");
        JPanel soundtrackName_panel = new JPanel();
        soundtrackName_text.setToolTipText("Not null");
        soundtrackName_panel.add(soundtrackName_label);
        soundtrackName_panel.add(Box.createRigidArea(new Dimension(10, 0)));
        soundtrackName_panel.add(soundtrackName_text);
        panels.add(soundtrackName_panel);
        soundtrackName_text.setText(hb.getSoundtrackName());

        JLabel weaponType_label = new JLabel("Weapon type: ");
        JPanel weaponType_panel = new JPanel();
        weaponType_text.setToolTipText("Knife/Machine gun/Rifle/Shotgun");
        weaponType_panel.add(weaponType_label);
        weaponType_panel.add(Box.createRigidArea(new Dimension(10, 0)));
        weaponType_panel.add(weaponType_text);
        panels.add(weaponType_panel);
        weaponType_text.setText(hb.getWeaponType().toString());

        JLabel mood_label = new JLabel("Mood: ");
        JPanel mood_panel = new JPanel();
        mood_text.setToolTipText("Longing/Gloom/Frenzy");
        mood_panel.add(mood_label);
        mood_panel.add(Box.createRigidArea(new Dimension(10, 0)));
        mood_panel.add(mood_text);
        panels.add(mood_panel);
        mood_text.setText(hb.getMood().toString());

        JLabel car_label = new JLabel("Car: ");
        JPanel car_panel = new JPanel();
        car_text.setToolTipText("Да/Нет");
        car_panel.add(car_label);
        car_panel.add(Box.createRigidArea(new Dimension(10, 0)));
        car_panel.add(car_text);
        panels.add(car_panel);
        car_text.setText(String.valueOf(hb.getCar().getCool()));

        JLabel user_label = new JLabel("User: " + hb.getLogin());
        JPanel user_panel = new JPanel();
        user_panel.add(user_label);
        panels.add(user_panel);


        JButton b = new JButton("OK");
        JButton b1 = new JButton("CHANGE");
        JButton b2 = new JButton("DELETE");
        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));

        b.setHorizontalAlignment(JTextField.RIGHT);
        b.addActionListener((ActionEvent e) -> {
            d.setVisible(false);
        });
        b2.addActionListener((ActionEvent e) -> {
            try {
                connection.send(Serializer.serialize(new AuthResponse("remove_by_id", session.getUser(), session.isAuthorized(), String.valueOf(hb.getId()), "")));
                AuthResponse response = connection.recieve();
                JOptionPane.showMessageDialog(VisualisationPanel.this, response.getCommand(),
                        "Information", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(VisualisationPanel.this, exception,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            d.setVisible(false);
        });
        b1.addActionListener((ActionEvent e) -> {
            String line;
            StringBuilder sb = new StringBuilder();

            line = name_text.getText();
            if (line != null && !line.equals("")) {
                name = line;
            } else {
                sb.append("Name is unacceptable!\n");
            }

            line = coordinate_x_text.getText();
            if ((!Objects.equals(line, "\n")) && !line.equals("") && (line != null)) {
                try {
                    if (Integer.parseInt(line) <= 493) {
                        x = Integer.parseInt(line);
                    } else {
                        sb.append("Coordinate X must be lower than 494!\n");
                    }
                } catch (NumberFormatException ex) {
                    sb.append("Coordinate X must be a digit!\n");
                }
            } else {
                sb.append("Coordinate X must be a digit!\n");
            }

            line = coordinate_y_text.getText();
            if ((!Objects.equals(line, "\n")) && !line.equals("") && (line != null)) {
                try {
                    if (Long.parseLong(line) <= 493) {
                        y = Long.parseLong(line);
                    } else {
                        sb.append("Coordinate Y must be lower than 494!\n");
                    }
                } catch (NumberFormatException ex) {
                    sb.append("Coordinate Y must be a digit!\n");
                }
            } else {
                sb.append("Coordinate Y must be a digit!\n");
            }

            line = realHero_text.getText();
            switch (line) {
                case "Да", "true" -> realHero = true;
                case "Нет", "false" -> realHero = false;
                default -> sb.append("RealHero value is unacceptable!\n");
            }

            line = impactSpeed_text.getText();
            try {
                if (line != null && Long.parseLong(line) <= 572 && Long.parseLong(line) >= 0 && !line.equals("")) {
                    impactSpeed = Long.parseLong(line);
                } else {
                    sb.append("Impact speed must be positive number and lower 573!\n");
                }
            } catch (NumberFormatException ex) {
                sb.append("Impact speed must be a digit!\n");
            }

            line = hasToothpick_text.getText();
            switch (line) {
                case "Да", "true" -> hasToothpick = true;
                case "Нет", "false" -> hasToothpick = false;
                default -> sb.append("Toothpick value is unacceptable!\n");
            }

            line = soundtrackName_text.getText();
            if (line != null && !line.equals("")) {
                soundtrackName = line;
            } else {
                sb.append("Soundtrack name is unacceptable!\n");
            }

            line = weaponType_text.getText();
            switch (line) {
                case "Shotgun" -> weaponType = WeaponType.SHOTGUN;
                case "Knife" -> weaponType = WeaponType.KNIFE;
                case "Machine gun" -> weaponType = WeaponType.MACHINE_GUN;
                case "Rifle" -> weaponType = WeaponType.RIFLE;
                default -> sb.append("Weapon type is unacceptable!\n");
            }

            line = mood_text.getText();
            switch (line) {
                case "Longing" -> mood = Mood.LONGING;
                case "Gloom" -> mood = Mood.GLOOM;
                case "Frenzy" -> mood = Mood.FRENZY;
                default -> sb.append("Mood is unacceptable!\n");
            }

            line = car_text.getText();
            switch (line) {
                case "Да", "true" -> car = true;
                case "Нет", "false" -> car = false;
                default -> sb.append("Car value is unacceptable!\n");
            }
            try {
                if (hb.getLogin().equals(session.getUser())) {
                    if (sb.toString().equals("")) {
                        HumanBeing h = new HumanBeing(name, new Coordinates(x, y), realHero, hasToothpick, impactSpeed, soundtrackName, weaponType, mood, new Car(car));
                        h.setLogin(session.getUser());
                        h.setId(hb.getId());
                        h.setCreationDate(hb.getCreationDate());
                        // #TODO Тут ошибку выводит при обновлении >:(
                        connection.send(Serializer.serialize(new AuthResponse("update", session.getUser(), session.isAuthorized(), String.valueOf(h.getId()), h.toString())));
                        AuthResponse response = connection.recieve();
                        JOptionPane.showMessageDialog(VisualisationPanel.this, response.getCommand(),
                                "Information", JOptionPane.INFORMATION_MESSAGE);
                        d.setVisible(false);

                    } else {
                        JOptionPane.showMessageDialog(VisualisationPanel.this, sb.toString(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(VisualisationPanel.this, "You can't change date of not your object!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception exception) {
                JOptionPane.showMessageDialog(VisualisationPanel.this, sb.toString(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        });
        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
        p1.setBorder(BorderFactory.createEmptyBorder(20, 60, 40, 60));

        panels.forEach(e -> {
            e.setLayout(new BoxLayout(e, BoxLayout.X_AXIS));
            p1.add(e);
            p1.add(Box.createRigidArea(new Dimension(0, 10)));
        });
        panels.clear();


        p2.add(b);
        p2.add(b1);
        p2.add(b2);
        p1.add(p2);
        p1.add(Box.createRigidArea(new Dimension(0, 15)));
        d.setContentPane(p1);
        d.setSize(400, 500);
        d.setVisible(true);
    }

    public void drawCreation(CopyOnWriteArrayList<HumanBeing> humanBeings) {
        try {
            humanBeings.forEach((HumanBeing e) ->{
                int x = e.getCoordinates().getX();
                int y = Integer.parseInt(e.getCoordinates().getY().toString());
                drawSparkles(x, y);
            });

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    public void drawSparkles(int x, int y){
        g2d.setColor(Color.YELLOW);
        g2d.drawLine(x+10, y+15, x+20, y+20);
        g2d.drawLine(x+10, y+15, x+20, y);
        g2d.drawLine(x+10, y+15, x+20, y-420);
        g2d.drawLine(x+10, y+15, x, y+20);
        g2d.drawLine(x+10, y+15, x, y-20);
        g2d.drawLine(x+10, y+15, x-20, y);
        g2d.drawLine(x+10, y+15, x-20, y-20);
        g2d.drawLine(x+10, y+15, x-20, y+20);
    }
    public void drawElements() {
        try {
            currentUserList.forEach((HumanBeing e) -> {
                int x = e.getCoordinates().getX();
                int y = Integer.parseInt(e.getCoordinates().getY().toString());
                if (!usersColor.containsKey(e.getLogin())) {
                    Random random = new Random(e.getId());
                    Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
                    usersColor.put(e.getLogin(), color);
                }
                drawMan(x, y, usersColor.get(e.getLogin()));

            });
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    public void drawMan(Integer x, int y, Color color) {
        int width = 20;
        int height = 20;
        int headRad = 10;
        int headX = x + (width / 2) - headRad / 2;
        int headY = y + (height / 2) - headRad - 2;
        int rectX = x;
        int rectY = y + (height / 2);

        int rectHeight = height - rectY + y;
        g2d.setColor(color);

        g2d.fillRect(rectX, rectY, width, rectHeight);
        g2d.fillOval(headX, headY, headRad, headRad);
    }
}
