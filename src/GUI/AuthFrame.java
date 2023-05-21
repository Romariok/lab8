package GUI;

import Auth.AuthResponse;
import Auth.Session;
import Auth.User;
import client.Connection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import static Command.Serializer.serialize;

public class AuthFrame extends ExtendableJFrame {
    private Connection connection;
    private JTextField loginField;
    private String login = "";
    private String password = "";
    JPasswordField passwordField;
    private JButton auth_button = new JButton("Log in");
    private JButton register_button = new JButton("Registration");
    private JPanel panel;
    private JDialog d = new JDialog();

    private Session session;

    public AuthFrame(Connection connection, Session session) {
        this.connection = connection;
        this.session = session;
        initUI();
    }

    @Override
    void initUI() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(new Insets(40, 60, 40, 60)));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        auth_button.setAlignmentX(Component.CENTER_ALIGNMENT);
        auth_button.addActionListener((ActionEvent e) -> {
            JButton item = (JButton) e.getSource();
            String label = item.getText();
            statusbar.setText(" " + label + " button clicked");

            loginPasWindow();
//           Уже инцилизированы пароль и логин
//            #TODO Инцилизировать Dialog с полями login и пароль. Будет относиться и к register
            session.setUser(login);
            User user = new User();
            user.setPass(password);
            user.setUser(login);
            try {
                connection.send(serialize(new AuthResponse("auth", session.getUser(), session.isAuthorized(), "", user.toString())));
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(panel, resourceBundle.getString("failure"),//                        "Error", JOptionPane.ERROR_MESSAGE);)
            }
//        Возможно с connection нужно рпокидывать session
//         #TODO  Сделать проверку  успешного auth


//            if (...){
//                При успешной авторизации
//                JOptionPane.showMessageDialog(panel, resourceBundle.getString("success"),
//                        "Information", JOptionPane.INFORMATION_MESSAGE);
//                EventQueue.invokeLater(() -> {
//                    MainFrame ex = new MainFrame(connection);
//                    ex.setVisible(true);
//                    setVisible(false);
//                });
//
//            } else{
//                При неуспешной авторизации
//                JOptionPane.showMessageDialog(panel, resourceBundle.getString("failure"),
//                        "Error", JOptionPane.ERROR_MESSAGE);
//            }

        });
        register_button.setAlignmentX(Component.CENTER_ALIGNMENT);
        register_button.addActionListener((ActionEvent e) -> {
            JButton item = (JButton) e.getSource();
            String label = item.getText();
            statusbar.setText(" " + label + " button clicked");


            loginPasWindow();
//           Уже инцилизированы пароль и логин
//            connection.send(serialize(new AuthResponse("register", session.getUser(), session.isAuthorized(), "", "")));
//         #TODO  Сделать проверку  успешного register


//            if (...){
//                При успешной регистрации
//                JOptionPane.showMessageDialog(panel, resourceBundle.getString("success"),
//                        "Information", JOptionPane.INFORMATION_MESSAGE);
//
//            } else{
//                При неуспешной регистрации
//                JOptionPane.showMessageDialog(panel, resourceBundle.getString("failure"),
//                        "Error", JOptionPane.ERROR_MESSAGE);
//            }

        });
        panel.add(auth_button);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(register_button);
        add(panel);
        statusbar.setBorder(BorderFactory.createEtchedBorder(
                EtchedBorder.RAISED));
        add(statusbar, BorderLayout.SOUTH);
        initializeMenuBar();
        setJMenuBar(menuBar);
        updateLanguage(Locale.getDefault());
        pack();
        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    void updateLanguage(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("GUI.resources.Locale", locale);
        rus_item.setText(resourceBundle.getString("ru_lang_name"));
        is_item.setText(resourceBundle.getString("is_lang_name"));
        es_cr_item.setText(resourceBundle.getString("es_cr_lang_name"));
        pl_item.setText(resourceBundle.getString("pl_lang_name"));
        languages_menu.setText(resourceBundle.getString("language"));
        help_menu.setText(resourceBundle.getString("help"));
        auth_button.setText(resourceBundle.getString("auth_button"));
        register_button.setText(resourceBundle.getString("reg_button"));
        setTitle(resourceBundle.getString("auth_frame"));
    }

    private void loginPasWindow() {
        loginField = new JTextField(20);
        loginField.setToolTipText("LOGIN");
        loginField.setHorizontalAlignment(JTextField.LEFT);


        passwordField = new JPasswordField(20);
        passwordField.setToolTipText("PASSWORD");
        passwordField.setHorizontalAlignment(JTextField.LEFT);
        passwordField.setEchoChar('*');
        JButton b = new JButton("OK");
        b.setHorizontalAlignment(JTextField.RIGHT);
        b.addActionListener((ActionEvent e) -> {
            login = loginField.getText();
            password = passwordField.getText();

//      #TODO      Удалить потом
            JOptionPane.showMessageDialog(AuthFrame.this,
                    "Ваше слово: " + login + " " + password);
//            ------------------------------
            d.setVisible(false);
        });
        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p1.add(loginField);
        p1.add(passwordField);
        p1.add(Box.createRigidArea(new Dimension(30, 0)));
        p1.add(b);


        d.setContentPane(p1);
        d.setSize(400, 130);
        d.setVisible(true);
    }

}
