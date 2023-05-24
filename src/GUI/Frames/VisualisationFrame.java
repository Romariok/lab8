package GUI.Frames;


import Auth.Session;
import GUI.Panels.VisualisationPanel;
import client.Connection;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class VisualisationFrame extends ExtendableJFrame{
    private VisualisationPanel panel;
    public VisualisationFrame(Connection connection, Session session){
        this.connection = connection;
        this.session = session;
    private Locale currentLocale;
    public VisualisationFrame(){
        initUI();
    }
    @Override
    void initUI(){
        initializeMenuBar();
        statusbar.setBorder(BorderFactory.createEtchedBorder(
                EtchedBorder.RAISED));
        add(statusbar, BorderLayout.SOUTH);
        setJMenuBar(menuBar);
        panel = new VisualisationPanel(connection, session);
        getContentPane().add(panel);

        pack();
        setPreferredSize(new Dimension(513, 513));
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @Override
    void updateLanguage(Locale locale){
        currentLocale = locale;
        resourceBundle = ResourceBundle.getBundle("GUI.resources.Locale", locale);
        setTitle(resourceBundle.getString("graph_button"));
        rus_item.setText(resourceBundle.getString("ru_lang_name"));
        is_item.setText(resourceBundle.getString("is_lang_name"));
        es_cr_item.setText(resourceBundle.getString("es_cr_lang_name"));
        pl_item.setText(resourceBundle.getString("pl_lang_name"));
        languages_menu.setText(resourceBundle.getString("language"));
        help_menu.setText(resourceBundle.getString("help"));
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
