package GUI.Frames;

import Auth.Session;
import client.Connection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class ExtendableJFrame extends JFrame{
    protected Connection connection;
    protected static ResourceBundle resourceBundle;
    protected  JLabel statusbar = new JLabel("Ready to fight!");
    protected  JMenuBar menuBar;
    protected JMenuItem help_menu;
    protected JMenu languages_menu;
    protected JMenuItem rus_item;
    protected JMenuItem is_item;
    protected JMenuItem pl_item;
    protected JMenuItem es_cr_item;
    protected Session session;


    abstract void initUI();
    protected void initializeMenuBar() {
        ImageIcon IconRus = new ImageIcon("src/GUI/Icons/Russia.svg.png");
        ImageIcon IconIs = new ImageIcon("src/GUI/Icons/Iceland.svg.png");
        ImageIcon IconPl = new ImageIcon("src/GUI/Icons/Polish.svg.png");
        ImageIcon IconCR = new ImageIcon("src/GUI/Icons/Costa_Rica.svg.png");
        Image hentai = new ImageIcon("src/GUI/Icons/artem.jpg").getImage();
        menuBar = new JMenuBar();
        languages_menu = new JMenu("Languages");
        help_menu = new JMenuItem("Help");
        help_menu.addActionListener((ActionEvent e) -> {
            JMenuItem item = (JMenuItem) e.getSource();
            String name = item.getText();
            statusbar.setText(" " + name + " item menu clicked");
            try{
                if(Desktop.isDesktopSupported()){
                    Desktop desktop = Desktop.getDesktop();
                    if(desktop.isSupported(Desktop.Action.BROWSE)){
                        desktop.browse(URI.create(getUrlFromLocale()));
                    }
                }
            }
            catch (Exception exception){
                exception.printStackTrace();
            }
        });
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
//    protected void showWindowText(String text){
//        w = new JWindow();
//        JLabel l = new JLabel(text);
//        l.setAlignmentX(Component.CENTER_ALIGNMENT);
//        JButton b = new JButton("OK");
//        b.setAlignmentX(Component.CENTER_ALIGNMENT);
//        b.addActionListener(this);
//        JPanel p = new JPanel();
//        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
//        p.add(Box.createRigidArea(new Dimension(0, 20)));
//        p.add(l);
//        p.add(Box.createRigidArea(new Dimension(0, 20)));
//        p.add(b);
//        w.add(p);
//
//        w.setSize(200, 100);
//        w.setLocation(300, 300);
//
//        w.setVisible(true);
//    }
//    public void actionPerformed(ActionEvent evt)
//    {
//        w.setVisible(false);
//    }
    abstract void updateLanguage(Locale locale);

    abstract String getUrlFromLocale();
}
