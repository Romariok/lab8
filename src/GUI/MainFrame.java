package GUI;

import client.Connection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainFrame extends ExtendableJFrame {
    private JButton table_button;
    private JButton visualization_button;
    private JPanel main_panel;
    private CommandsFrame ex = new CommandsFrame(connection);
    private TableFrame tf = new TableFrame();

    private JButton list_of_commands;

    public MainFrame(Connection connection) {
        this.connection = connection;
        initUI();
    }

    @Override
    void initUI() {
        initializePanelOfMainFrame();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeMenuBar();

        statusbar.setBorder(BorderFactory.createEtchedBorder(
                EtchedBorder.RAISED));
        updateLanguage(Locale.getDefault());
        add(statusbar, BorderLayout.SOUTH);
        setJMenuBar(menuBar);
        add(main_panel);
        pack();
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    void updateLanguage(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("GUI.resources.Locale", locale);
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
            EventQueue.invokeLater(() -> {
                ex.setVisible(true);
            });
            statusbar.setText(" " + label + " button clicked");
        });
        list_of_commands.setAlignmentX(Component.CENTER_ALIGNMENT);
        table_button = new JButton("Table of Objects");
        table_button.addActionListener((ActionEvent e) -> {
            JButton item = (JButton) e.getSource();
            String label = item.getText();
            EventQueue.invokeLater(() -> {
                tf.setVisible(true);
            });
            statusbar.setText(" " + label + " button clicked");
        });
        table_button.setAlignmentX(Component.CENTER_ALIGNMENT);
        visualization_button = new JButton("Visualization of Objects");
        visualization_button.setAlignmentX(Component.CENTER_ALIGNMENT);
        main_panel = new JPanel();
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
        main_panel.setBorder(new EmptyBorder(new Insets(40, 60, 40, 60)));
        main_panel.add(list_of_commands);
        main_panel.add(Box.createRigidArea(new Dimension(0, 10)));
        main_panel.add(table_button);
        main_panel.add(Box.createRigidArea(new Dimension(0, 10)));
        main_panel.add(visualization_button);

    }

}
