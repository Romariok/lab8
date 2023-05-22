package GUI;

import Auth.AuthResponse;
import Auth.Session;
import Data.HumanBeing;
import Data.setVariables.readHumanBeingFromConsole;
import client.Connection;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
        table.setAutoCreateRowSorter(true);
//        table.setRowSorter(sorter);

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
        }, 2 * 1000, 10*1000);

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

    public void setData(List<Object[]> data) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);
        for (Object[] e : data) {
            tableModel.addRow(e);
        }
        tableModel.fireTableDataChanged();
    }
    private List<Object[]> parseList(String output){
        List<Object[]> list = new ArrayList<Object[]>();
        if(!output.equals("Collection is empty\n")) {
            for (String arges : output.split("\n\n")) {
                String[] args = arges.split("\n");
                Object[] fields = new Object[13];
                HumanBeing hb = new HumanBeing();
                readHumanBeingFromConsole.initializeHumanBeing(hb,new Scanner(arges),true);
                hb.setId(Long.parseLong(args[0]));
                hb.setLogin(args[12]);
                String time = args[4].substring(0, 19);
                time = time.replace("T"," ");
                hb.setCreationDate(ZonedDateTime.ofInstant(Timestamp.valueOf(time).toInstant(), ZoneId.systemDefault()));
                fields[0] = hb.getId();
                fields[1] = hb.getName();
                fields[2] = hb.getCoordinates().getX();
                fields[3] = hb.getCoordinates().getY();
                fields[4] = hb.getCreationDate();
                fields[5] = hb.isRealHero();
                fields[6] = hb.getHasToothpick();
                fields[7] = hb.getImpactSpeed();
                fields[8] = hb.getSoundtrackName();
                fields[9] = hb.getWeaponType();
                fields[10] = hb.getMood();
                fields[11] = hb.getCar().getCool();
                fields[12] = hb.getLogin();
                list.add(fields);
            }
        }
        return list;
    }
}
