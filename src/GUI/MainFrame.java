package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainFrame extends JFrame {
    public static JLabel statusbar;
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("GUI.resources.Locale", Locale.getDefault());
    private JMenuBar menuBar;
    private JMenu help_menu;
    private JMenu languages_menu;
    private JMenuItem rus_item;
    private JMenuItem is_item;
    private JMenuItem pl_item;
    private JMenuItem es_cr_item;
    private JButton add_button;
//    private JDialog table_frame;
    private JFrame list_of_commands_frame;
    private JButton table_button;
    private JButton visualization_button;
    private JPanel main_panel;
    private JPanel list_panel;
    private JButton list_of_commands;

    public MainFrame() {
        initUI();
    }

    private void initUI() {
        initializePanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeMenuBar();

        statusbar = new JLabel("Ready to fight!");
        statusbar.setBorder(BorderFactory.createEtchedBorder(
                EtchedBorder.RAISED));
        initialize_list_of_commands_frame();
        updateLanguage(Locale.getDefault());
        add(statusbar, BorderLayout.SOUTH);
        setJMenuBar(menuBar);
        add(main_panel);
        pack();
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateLanguage(Locale locale) {
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
        list_of_commands_frame.setTitle(resourceBundle.getString("list_of_commands_button"));
        add_button.setText(resourceBundle.getString("add_button"));
//        table_frame.setTitle(resourceBundle.getString("table_button"));
    }

    private void initialize_list_of_commands_frame(){
        list_of_commands_frame = new JFrame();
        add_button = new JButton("Add");
        add_button.setAlignmentX(Component.CENTER_ALIGNMENT);
        add_button.addActionListener((ActionEvent e) -> {
            JButton item = (JButton) e.getSource();
            String label = item.getText();
            statusbar.setText(" " + label + " button clicked");
        });
        list_panel = new JPanel();
        list_panel.setLayout(new BoxLayout(list_panel, BoxLayout.Y_AXIS));
        list_panel.setBorder(new EmptyBorder(new Insets(40, 60, 40, 60)));
        list_panel.add(add_button);
        list_of_commands_frame.add(list_panel);
        list_of_commands_frame.add(statusbar, BorderLayout.SOUTH);
        list_of_commands_frame.pack();
        list_of_commands_frame.setSize(500, 600);
        setLocationRelativeTo(null);
    }
    private void initializePanel(){
        list_of_commands = new JButton("List of commands");
        list_of_commands.addActionListener((ActionEvent e) -> {
            JButton item = (JButton) e.getSource();
            String label = item.getText();
            list_of_commands_frame.setVisible(true);
            statusbar.setText(" " + label + " button clicked");
        });
        list_of_commands.setAlignmentX(Component.CENTER_ALIGNMENT);
        table_button = new JButton("Table of Objects");
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
    private void initializeMenuBar() {
        ImageIcon IconRus = new ImageIcon("src/GUI/Icons/Russia.svg.png");
        ImageIcon IconIs = new ImageIcon("src/GUI/Icons/Iceland.svg.png");
        ImageIcon IconPl = new ImageIcon("src/GUI/Icons/Polish.svg.png");
        ImageIcon IconCR = new ImageIcon("src/GUI/Icons/Costa_Rica.svg.png");
        menuBar = new JMenuBar();
        languages_menu = new JMenu("Languages");
        help_menu = new JMenu("Help");
        menuBar.add(languages_menu);
        menuBar.add(help_menu);
        rus_item = new JMenuItem("Russian", IconRus);
        rus_item.addActionListener((ActionEvent e) -> {
            JMenuItem item = (JMenuItem) e.getSource();
            String label = item.getText();
            updateLanguage(Locale.getDefault());
            statusbar.setText(" " + label + " menu item clicked");
        });
        is_item = new JMenuItem("Icelandic", IconIs);
        is_item.addActionListener((ActionEvent e) -> {
            JMenuItem item = (JMenuItem) e.getSource();
            String label = item.getText();
            updateLanguage(new Locale("is", "IS"));
            statusbar.setText(" " + label + " menu item clicked");
        });
        pl_item = new JMenuItem("Polish", IconPl);
        pl_item.addActionListener((ActionEvent e) -> {
            JMenuItem item = (JMenuItem) e.getSource();
            String label = item.getText();
            updateLanguage(new Locale("pl", "PL"));
            statusbar.setText(" " + label + " menu item clicked");
        });
        es_cr_item = new JMenuItem("Spanish (Costa-Rica)", IconCR);
        es_cr_item.addActionListener((ActionEvent e) -> {
            JMenuItem item = (JMenuItem) e.getSource();
            String label = item.getText();
            updateLanguage(new Locale("es_CR", "ES_CR"));
            statusbar.setText(" " + label + " menu item clicked");
        });
        languages_menu.add(rus_item);
        languages_menu.add(is_item);
        languages_menu.add(pl_item);
        languages_menu.add(es_cr_item);
    }

}
