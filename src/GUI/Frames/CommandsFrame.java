package GUI.Frames;

import Auth.AuthResponse;
import Auth.Session;
import Command.Serializer;
import Data.*;
import client.Connection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;


public class CommandsFrame extends ExtendableJFrame {
    private Locale currentLocale;
    private JPanel list_panel;
    private JButton add_button = new JButton("Add");
    private JButton clear_button = new JButton("Clear");
    private JButton help_button = new JButton("Help");
    private JButton info_button = new JButton("Info");
    private JButton count_greater_than_car_button = new JButton("Count greater than car");
    private JButton execute_script_button = new JButton("Execute script");
    private JButton filter_starts_with_soundtrack_name_button = new JButton("Filter starts with soundtrack name");
    private JButton insert_at_button = new JButton("Insert at");
    private JButton remove_by_id_button = new JButton("Remove by id");
    private JButton remove_greater_button = new JButton("Remove greater");
    private JButton remove_lower_button = new JButton("Remove lower");
    private JButton sum_of_impact_speed_button = new JButton("Sum of impact speed");
    private JButton update_button = new JButton("Update");
    private LinkedList<JButton> list_of_button = new LinkedList<>();

    private JTextField argument_text = new JTextField(10);
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
    private JTextField arg_text = new JTextField(10);
    private LinkedList<JPanel> panels = new LinkedList<>();
    private JDialog d = new JDialog(this);
    private JDialog d1 = new JDialog(this);

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
    private String arg;

    public CommandsFrame(Connection connection, Session session) {
        this.session = session;
        this.connection = connection;
        initUI();
    }

    @Override
    void initUI() {

        list_of_button.add(add_button);
        list_of_button.add(clear_button);
        list_of_button.add(help_button);
        list_of_button.add(info_button);
        list_of_button.add(count_greater_than_car_button);
        list_of_button.add(execute_script_button);
        list_of_button.add(filter_starts_with_soundtrack_name_button);
        list_of_button.add(insert_at_button);
        list_of_button.add(remove_by_id_button);
        list_of_button.add(remove_greater_button);
        list_of_button.add(remove_lower_button);
        list_of_button.add(sum_of_impact_speed_button);
        list_of_button.add(update_button);
        add_button.addActionListener((ActionEvent e) -> {
            JButton item = (JButton) e.getSource();
            String label = item.getText();
            statusbar.setText(" " + label + " button clicked");
            objectInputWindow("add", false);
        });

        clear_button.addActionListener((ActionEvent e) -> {
            JButton item = (JButton) e.getSource();
            String label = item.getText();
            statusbar.setText(" " + label + " button clicked");
            try {
                connection.send(Serializer.serialize(new AuthResponse("clear", session.getUser(), session.isAuthorized(), "", "")));
                AuthResponse response = connection.recieve();
                JOptionPane.showMessageDialog(CommandsFrame.this, response.getCommand(),
                        "Information", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(CommandsFrame.this, resourceBundle.getString("failure"),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        help_button.addActionListener((ActionEvent e) -> {
            JButton item = (JButton) e.getSource();
            String label = item.getText();
            statusbar.setText(" " + label + " button clicked");
            try {
                connection.send(Serializer.serialize(new AuthResponse("help", session.getUser(), session.isAuthorized(), "", "")));
                AuthResponse response = connection.recieve();
                JOptionPane.showMessageDialog(CommandsFrame.this, response.getCommand(),
                        "Information", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(CommandsFrame.this, resourceBundle.getString("failure"),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        info_button.addActionListener((ActionEvent e) -> {
            JButton item = (JButton) e.getSource();
            String label = item.getText();
            statusbar.setText(" " + label + " button clicked");
            try {
                connection.send(Serializer.serialize(new AuthResponse("info", session.getUser(), session.isAuthorized(), "", "")));
                String[] test = connection.recieve().getCommand().split("\n");
                JWindow w = new JWindow();
                JLabel l1 = new JLabel("Type of collection: " + test[0]);
                JLabel l2 = new JLabel("Date of initializations: " + test[1]);
                JLabel l3 = new JLabel("Size of collection: " + test[2]);
                l1.setAlignmentX(Component.CENTER_ALIGNMENT);
                l2.setAlignmentX(Component.CENTER_ALIGNMENT);
                l3.setAlignmentX(Component.CENTER_ALIGNMENT);
                JButton b = new JButton("OK");
                b.setAlignmentX(Component.CENTER_ALIGNMENT);
                b.addActionListener((ActionEvent evt) -> {
                    w.setVisible(false);
                });
                JPanel p = new JPanel();
                p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
                p.add(Box.createRigidArea(new Dimension(0, 20)));
                p.add(l1);
                p.add(Box.createRigidArea(new Dimension(0, 20)));
                p.add(l2);
                p.add(Box.createRigidArea(new Dimension(0, 20)));
                p.add(l3);
                p.add(Box.createRigidArea(new Dimension(0, 10)));
                p.add(b);
                w.add(p);
                w.setSize(400, 500);
                w.setLocation(300, 300);
                w.setVisible(true);
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(CommandsFrame.this, resourceBundle.getString("failure"),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        count_greater_than_car_button.addActionListener((ActionEvent e) -> {
            JButton item = (JButton) e.getSource();
            String label = item.getText();
            statusbar.setText(" " + label + " button clicked");
            argumentWindow("count_greater_than_car");
        });
        execute_script_button.addActionListener((ActionEvent e) -> {
            JButton item = (JButton) e.getSource();
            String label = item.getText();
            statusbar.setText(" " + label + " button clicked");
            argumentWindow("execute_script");
        });
        filter_starts_with_soundtrack_name_button.addActionListener((ActionEvent e) -> {
            JButton item = (JButton) e.getSource();
            String label = item.getText();
            statusbar.setText(" " + label + " button clicked");
            argumentWindow("filter_starts_with_soundtrack");
        });

        insert_at_button.addActionListener((ActionEvent e) -> {
            JButton item = (JButton) e.getSource();
            String label = item.getText();
            statusbar.setText(" " + label + " button clicked");
            objectInputWindow("insert_at", true);
        });
        remove_by_id_button.addActionListener((ActionEvent e) -> {
            JButton item = (JButton) e.getSource();
            String label = item.getText();
            statusbar.setText(" " + label + " button clicked");
            argumentWindow("remove_by_id");
        });
        remove_greater_button.addActionListener((ActionEvent e) -> {
            JButton item = (JButton) e.getSource();
            String label = item.getText();
            statusbar.setText(" " + label + " button clicked");
            argumentWindow("remove_greater");
        });
        remove_lower_button.addActionListener((ActionEvent e) -> {
            JButton item = (JButton) e.getSource();
            String label = item.getText();
            statusbar.setText(" " + label + " button clicked");
            argumentWindow("remove_lower");
        });
        sum_of_impact_speed_button.addActionListener((ActionEvent e) -> {
            JButton item = (JButton) e.getSource();
            String label = item.getText();
            statusbar.setText(" " + label + " button clicked");
            try {
                connection.send(Serializer.serialize(new AuthResponse("sum_of_impact_speed",session.getUser(),session.isAuthorized(),"","")));
                AuthResponse response = connection.recieve();
                JOptionPane.showMessageDialog(CommandsFrame.this, response.getCommand(),
                        "Information", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(CommandsFrame.this, resourceBundle.getString("failure"),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        update_button.addActionListener((ActionEvent e) -> {
            JButton item = (JButton) e.getSource();
            String label = item.getText();
            statusbar.setText(" " + label + " button clicked");
            objectInputWindow("update", true);
        });

        list_panel = new JPanel();
        list_panel.setLayout(new BoxLayout(list_panel, BoxLayout.Y_AXIS));
        list_panel.setBorder(new EmptyBorder(new Insets(40, 60, 40, 60)));
        list_of_button.forEach(button -> {
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            list_panel.add(button);
            list_panel.add(Box.createRigidArea(new Dimension(0, 10)));
        });

        add(list_panel);
        statusbar.setBorder(BorderFactory.createEtchedBorder(
                EtchedBorder.RAISED));
        add(statusbar, BorderLayout.SOUTH);
        initializeMenuBar();
        setJMenuBar(menuBar);
        pack();
        setSize(500, 600);
        setMinimumSize(new Dimension(400, 600));
        setLocationRelativeTo(null);
    }

    @Override
    void updateLanguage(Locale locale) {
        currentLocale = locale;
        resourceBundle = ResourceBundle.getBundle("GUI.resources.Locale", locale);
        rus_item.setText(resourceBundle.getString("ru_lang_name"));
        is_item.setText(resourceBundle.getString("is_lang_name"));
        es_cr_item.setText(resourceBundle.getString("es_cr_lang_name"));
        pl_item.setText(resourceBundle.getString("pl_lang_name"));
        languages_menu.setText(resourceBundle.getString("language"));
        help_menu.setText(resourceBundle.getString("help"));
        setTitle(resourceBundle.getString("list_of_commands_button"));
        add_button.setText(resourceBundle.getString("add_button"));
        help_button.setText(resourceBundle.getString("help_button"));
        clear_button.setText(resourceBundle.getString("clear_button"));
        count_greater_than_car_button.setText(resourceBundle.getString("count_greater_than_car_button"));
        filter_starts_with_soundtrack_name_button.setText(resourceBundle.getString("filter_starts_with_soundtrack_name_button"));
        info_button.setText(resourceBundle.getString("info_button"));
        insert_at_button.setText(resourceBundle.getString("insert_at_button"));
        remove_by_id_button.setText(resourceBundle.getString("remove_by_id_button"));
        remove_lower_button.setText(resourceBundle.getString("remove_lower_button"));
        remove_greater_button.setText(resourceBundle.getString("remove_greater_button"));
        sum_of_impact_speed_button.setText(resourceBundle.getString("sum_of_impact_speed_button"));
        update_button.setText(resourceBundle.getString("update_button"));
        execute_script_button.setText(resourceBundle.getString("execute_script_button"));
    }


    private void objectInputWindow(String command, boolean arg_on) {
        JLabel name_label = new JLabel("Name: ");
        JPanel name_panel = new JPanel();
        name_text.setToolTipText("Name of human being");
        name_panel.add(name_label);
        name_panel.add(Box.createRigidArea(new Dimension(10, 0)));
        name_panel.add(name_text);
        panels.add(name_panel);


        JLabel coordinate_x_label = new JLabel("Coordinate X: ");
        JPanel coordinate_x_panel = new JPanel();
        coordinate_x_text.setToolTipText("Not null, must be a positive digit, must be lower 494");
        coordinate_x_panel.add(coordinate_x_label);
        coordinate_x_panel.add(Box.createRigidArea(new Dimension(10, 0)));
        coordinate_x_panel.add(coordinate_x_text);
        panels.add(coordinate_x_panel);

        JLabel coordinate_y_label = new JLabel("Coordinate Y: ");
        JPanel coordinate_y_panel = new JPanel();
        coordinate_y_panel.setLayout(new BoxLayout(coordinate_y_panel, BoxLayout.X_AXIS));
        coordinate_y_text.setToolTipText("Not null, must be a positive digit, must be lower 494");
        coordinate_y_panel.add(coordinate_y_label);
        coordinate_y_panel.add(Box.createRigidArea(new Dimension(10, 0)));
        coordinate_y_panel.add(coordinate_y_text);
        panels.add(coordinate_y_panel);

        JLabel realHero_label = new JLabel("Real Hero: ");
        JPanel real_hero_panel = new JPanel();
        realHero_text.setToolTipText("Да/Нет");
        real_hero_panel.add(realHero_label);
        real_hero_panel.add(Box.createRigidArea(new Dimension(10, 0)));
        real_hero_panel.add(realHero_text);
        panels.add(real_hero_panel);

        JLabel impact_speed_label = new JLabel("Impact speed: ");
        JPanel impact_speed_panel = new JPanel();
        impactSpeed_text.setToolTipText("Impact speed must be positive number and lower 573!");
        impact_speed_panel.add(impact_speed_label);
        impact_speed_panel.add(Box.createRigidArea(new Dimension(10, 0)));
        impact_speed_panel.add(impactSpeed_text);
        panels.add(impact_speed_panel);

        JLabel hasToothpick_label = new JLabel("Existence of toothpick: ");
        JPanel hasToothpick_panel = new JPanel();
        hasToothpick_text.setToolTipText("Да/Нет");
        hasToothpick_panel.add(hasToothpick_label);
        hasToothpick_panel.add(Box.createRigidArea(new Dimension(10, 0)));
        hasToothpick_panel.add(hasToothpick_text);
        panels.add(hasToothpick_panel);

        JLabel soundtrackName_label = new JLabel("Soundtrack name: ");
        JPanel soundtrackName_panel = new JPanel();
        soundtrackName_text.setToolTipText("Not null");
        soundtrackName_panel.add(soundtrackName_label);
        soundtrackName_panel.add(Box.createRigidArea(new Dimension(10, 0)));
        soundtrackName_panel.add(soundtrackName_text);
        panels.add(soundtrackName_panel);

        JLabel weaponType_label = new JLabel("Weapon type: ");
        JPanel weaponType_panel = new JPanel();
        weaponType_text.setToolTipText("Knife/Machine gun/Rifle/Shotgun");
        weaponType_panel.add(weaponType_label);
        weaponType_panel.add(Box.createRigidArea(new Dimension(10, 0)));
        weaponType_panel.add(weaponType_text);
        panels.add(weaponType_panel);

        JLabel mood_label = new JLabel("Mood: ");
        JPanel mood_panel = new JPanel();
        mood_text.setToolTipText("Longing/Gloom/Frenzy");
        mood_panel.add(mood_label);
        mood_panel.add(Box.createRigidArea(new Dimension(10, 0)));
        mood_panel.add(mood_text);
        panels.add(mood_panel);

        JLabel car_label = new JLabel("Car: ");
        JPanel car_panel = new JPanel();
        car_text.setToolTipText("Да/Нет");
        car_panel.add(car_label);
        car_panel.add(Box.createRigidArea(new Dimension(10, 0)));
        car_panel.add(car_text);
        panels.add(car_panel);

        if(arg_on){
            JLabel arg_label = new JLabel("Argument: ");
            JPanel arg_panel = new JPanel();
            arg_panel.add(arg_label);
            arg_panel.add(arg_text);
            panels.add(arg_panel);
        }

        JButton b = new JButton("OK");
        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));

        b.setHorizontalAlignment(JTextField.RIGHT);
        b.addActionListener((ActionEvent e) -> {
            String line;
            StringBuilder sb = new StringBuilder();

            line = name_text.getText();
            if (line != null && !line.equals("")) {
                name = line;
            } else {
                sb.append("Name is unacceptable!\n");
            }
            if (arg_on){
                line = arg_text.getText();
                if (line != null && !line.equals("")) {
                    arg = line;
                } else {
                    sb.append("Argument is unacceptable!\n");
                }
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
                case "Да" -> realHero = true;
                case "Нет" -> realHero = false;
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
                case "Да" -> hasToothpick = true;
                case "Нет" -> hasToothpick = false;
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
                case "Да" -> car = true;
                case "Нет" -> car = false;
                default -> sb.append("Car value is unacceptable!\n");
            }
            try {
                if (sb.toString().equals("")) {
                    HumanBeing h = new HumanBeing(name, new Coordinates(x, y), realHero, hasToothpick, impactSpeed, soundtrackName, weaponType, mood, new Car(car));
                    h.setLogin(session.getUser());
                    String args = "";
                    if(arg_on){
                        args = arg;
                    }
                    connection.send(Serializer.serialize(new AuthResponse(command, session.getUser(), session.isAuthorized(), args, h.toString())));
                    AuthResponse response = connection.recieve();
                    JOptionPane.showMessageDialog(CommandsFrame.this, response.getCommand(),
                            "Information", JOptionPane.INFORMATION_MESSAGE);
                    d.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(CommandsFrame.this, sb.toString(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(CommandsFrame.this, sb.toString(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        });
        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
        p1.setBorder(BorderFactory.createEmptyBorder(20,60,40,60));

        panels.forEach(e -> {
            e.setLayout(new BoxLayout(e, BoxLayout.X_AXIS));
            p1.add(e);
            p1.add(Box.createRigidArea(new Dimension(0, 10)));
        });
        panels.clear();


        p2.add(b);
        p1.add(p2);
        p1.add(Box.createRigidArea(new Dimension(0, 15)));
        d.setContentPane(p1);
        d.setSize(400, 500);
        d.setVisible(true);
    }

    private void argumentWindow(String command) {
        JLabel label = new JLabel("Argument: ");
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(argument_text);


        JButton b = new JButton("OK");
        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));

        b.setHorizontalAlignment(JTextField.RIGHT);
        b.addActionListener((ActionEvent e) -> {
            String line;
            StringBuilder sb = new StringBuilder();
            line = argument_text.getText();
            if (line != null && !line.equals("") && line != "\n") {
                arg = line;
            } else {
                sb.append("Argument is unacceptable!\n");
            }
            if (sb.toString().equals(""))
            {
                try {
                    connection.send(Serializer.serialize(new AuthResponse(command, session.getUser(),session.isAuthorized(),arg,"")));
                    AuthResponse response = connection.recieve();
                    JOptionPane.showMessageDialog(CommandsFrame.this, response.getCommand(),
                            "Information", JOptionPane.INFORMATION_MESSAGE);
                    d1.setVisible(false);
                }
                catch (Exception exception){
                    JOptionPane.showMessageDialog(CommandsFrame.this, sb.toString(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    d1.setVisible(false);
                }
            }
            else {
                JOptionPane.showMessageDialog(CommandsFrame.this, sb.toString(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                d1.setVisible(false);
            }
        });
        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
        p1.add(panel);
        p1.add(Box.createRigidArea(new Dimension(0, 10)));
        p2.add(b);
        p1.add(p2);
        p1.add(Box.createRigidArea(new Dimension(0, 15)));
        d1.setContentPane(p1);
        d1.setSize(200, 200);
        d1.setVisible(true);
    }
    public String getUrlFromLocale() {
        String t = this.currentLocale.getCountry();
        if (t.equals("RU")) {
            return "https://www.youtube.com/watch?v=KjBFS3886SQ&t=14s";
        }
        if (t.equals("PL")) {
            return "https://www.youtube.com/watch?v=B1AkqtFQYLE&t=21s";
        }
        if(t.equals("ES_CR")){
            return "https://www.youtube.com/watch?v=7yBxZC7oYRA";
        }
        return "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
    }
}
