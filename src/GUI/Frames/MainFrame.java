package GUI.Frames;

import Auth.Session;
import client.Connection;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;



public class MainFrame extends ExtendableJFrame {
    private JButton table_button;
    private JButton visualization_button;
    private JPanel button_panel;
    private JLabel login_label;
    private JPanel main_panel;
    private CommandsFrame cf;
    private TableFrame tf;
    private VisualisationFrame vf;

    private JButton list_of_commands;

    public MainFrame(Connection connection, Session session) {
        this.connection = connection;
        this.session = session;
        cf = new CommandsFrame(connection,session);
        tf = new TableFrame(connection,session);
        vf = new VisualisationFrame(connection, session);
        initUI();
    }

    @Override
    void initUI() {
        initializePanelOfMainFrame();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeMenuBar();
        statusbar.setBorder(BorderFactory.createEtchedBorder(
                EtchedBorder.RAISED));
        add(statusbar, BorderLayout.SOUTH);
        setJMenuBar(menuBar);
        main_panel.add(login_label);
        main_panel.add(Box.createRigidArea(new Dimension(0, 20)));
        main_panel.add(button_panel);
        add(main_panel);
        pack();
        setSize(500, 500);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(400, 300));
        setVisible(true);
    }

    @Override
    void updateLanguage(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("GUI.resources.Locale", locale);
        tf.updateLanguage(locale);
        cf.updateLanguage(locale);
        vf.updateLanguage(locale);
        setTitle(resourceBundle.getString("title_name"));
        rus_item.setText(resourceBundle.getString("ru_lang_name"));
        is_item.setText(resourceBundle.getString("is_lang_name"));
        es_cr_item.setText(resourceBundle.getString("es_cr_lang_name"));
        pl_item.setText(resourceBundle.getString("pl_lang_name"));
        languages_menu.setText(resourceBundle.getString("language"));
        help_menu.setText(resourceBundle.getString("help"));
        table_button.setText(resourceBundle.getString("table_button"));
        visualization_button.setText(resourceBundle.getString("graph_button"));
        list_of_commands.setText(resourceBundle.getString("list_of_commands_button"));

    }


    private void initializePanelOfMainFrame() {
        list_of_commands = new JButton("List of commands");
        list_of_commands.addActionListener((ActionEvent e) -> {
            JButton item = (JButton) e.getSource();
            String label = item.getText();
            cf.updateLanguage(resourceBundle.getLocale());
            EventQueue.invokeLater(() -> {
                cf.setVisible(true);
            });
            statusbar.setText(" " + label + " button clicked");
        });
        list_of_commands.setAlignmentX(Component.CENTER_ALIGNMENT);
        table_button = new JButton("Table of Objects");
        table_button.addActionListener((ActionEvent e) -> {
            JButton item = (JButton) e.getSource();
            String label = item.getText();
            tf.updateLanguage(resourceBundle.getLocale());
            EventQueue.invokeLater(() -> {
                tf.setVisible(true);
            });
            statusbar.setText(" " + label + " button clicked");
        });
        table_button.setAlignmentX(Component.CENTER_ALIGNMENT);
        visualization_button = new JButton("Visualization of Objects");
        visualization_button.addActionListener((ActionEvent e) -> {
            JButton item = (JButton) e.getSource();
            String label = item.getText();
            vf.updateLanguage(resourceBundle.getLocale());
            EventQueue.invokeLater(() -> {
                vf.setVisible(true);
            });
            statusbar.setText(" " + label + " button clicked");
        });
        visualization_button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button_panel = new JPanel();
        button_panel.setLayout(new BoxLayout(button_panel, BoxLayout.Y_AXIS));
        button_panel.add(list_of_commands);
        button_panel.add(Box.createRigidArea(new Dimension(0, 10)));
        button_panel.add(table_button);
        button_panel.add(Box.createRigidArea(new Dimension(0, 10)));
        button_panel.add(visualization_button);
        main_panel = new JPanel();
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
        main_panel.setBorder(BorderFactory.createEmptyBorder(40,60,40,60));
        login_label = new JLabel("Login: "+session.getUser(), SwingConstants.CENTER);
        login_label.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

}
