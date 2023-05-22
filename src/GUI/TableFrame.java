package GUI;

import Auth.AuthResponse;
import Auth.Session;
import client.Connection;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Timer;

import static Command.Serializer.serialize;

public class TableFrame extends ExtendableJFrame {
    DefaultTableModel tableModel;
    private Object[] columnsHeader = new String[]{"id", "name", "x", "y", "creationDate", "realHero", "hasToothpick",
            "ImpactSpeed", "SoundtrackName", "WeaponType", "Mood", "Car", "user"};
    private JTable table;

    public TableFrame(Connection connection, Session session) {
        this.connection = connection;
        this.session = session;
        initUI();
    }

    @Override
    void initUI() {
        statusbar.setBorder(BorderFactory.createEtchedBorder(
                EtchedBorder.RAISED));

        initializeMenuBar();
        add(statusbar, BorderLayout.SOUTH);
        setJMenuBar(menuBar);


        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnsHeader);


        table = new JTable(tableModel);
        DefaultTableModel contactTableModel = (DefaultTableModel) table
                .getModel();
        contactTableModel.setColumnIdentifiers(columnsHeader);

        TableRowSorter<TableModel> sorter
                = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);

//     #TODO   обновлять данные таблицы с помощью метода setData
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    connection.send(serialize(new AuthResponse("show", session.getUser(), session.isAuthorized(), "", "")));
                    String output = connection.recieve().getCommand();
                    setData(parseList(output));
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }, 2 * 1000, 20*1000);

        Box contents = new Box(BoxLayout.Y_AXIS);
        contents.add(new JScrollPane(table));
        getContentPane().add(contents);
        table.getSelectionModel().addListSelectionListener(
                event -> {
                    int viewRow = table.getSelectedRow();
                    if (viewRow < 0) {
                        statusbar.setText("");
                    } else {
                        int modelRow =
                                table.convertRowIndexToModel(viewRow);
                        statusbar.setText(""+
                                String.format("Selected Row in view: %d. " +
                                                "Selected Row in model: %d.",
                                        viewRow, modelRow));
                    }
                }
        );



        pack();
        setLocationRelativeTo(null);
        setSize(1200, 800);
        setLocation(0, 0);
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
        setTitle(resourceBundle.getString("table_button"));
    }

    public void setData(Object[][] data) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);
        for (Object[] e : data) {
            tableModel.addRow(e);
        }
        tableModel.fireTableDataChanged();
    }
    private String[][] parseList(String output){
        List<String[]> list = new ArrayList<>();
        if(!output.equals("Collection is empty\n")) {
            for (String args : output.split("\n\n")) {
                list.add(args.split("\n"));
            }
            return list.toArray(new String[0][]);
        }
        return list.toArray(new String[0][]);
    }
}
