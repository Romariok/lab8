package GUI.Frames;

import Auth.AuthResponse;
import Auth.Session;
import Command.Serializer;
import Data.HumanBeing;
import Data.Mood;
import Data.WeaponType;
import Data.setVariables.readHumanBeingFromConsole;
import GUI.Panels.VisualisationPanel;
import client.Connection;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;

import static Command.Serializer.serialize;

public class TableFrame extends ExtendableJFrame {
    public static CopyOnWriteArrayList<HumanBeing> currentUserList = new CopyOnWriteArrayList<>();
    private JTextField filterText;
    private JPanel tablePanel = new JPanel();

    private JTextField selectedRow = new JTextField(1);
    DefaultTableModel tableModel;
    private Object[] columnsHeader = new String[]{"id", "name", "x", "y", "creationDate", "realHero", "hasToothpick",
            "ImpactSpeed", "SoundtrackName", "WeaponType", "Mood", "Car", "user"};
    private JTable table;
    private boolean[] readOnlyColumns = {true, false, false, false, true, false, false, false, false, false, false, false, true};
    private List<Object[]> data = new ArrayList<>();
    private TableRowSorter<DefaultTableModel> sorter;

    public TableFrame(Connection connection, Session session) {
        this.connection = connection;
        this.session = session;
        initUI();
    }

    @Override
    void initUI() {
        selectedRow.setText("1");
        statusbar.setBorder(BorderFactory.createEtchedBorder(
                EtchedBorder.RAISED));
        initializeMenuBar();
        add(statusbar, BorderLayout.SOUTH);
        setJMenuBar(menuBar);
        tablePanel.setOpaque(true);
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int col) {
                return !readOnlyColumns[col];
            }
        };

        sorter = new TableRowSorter<>(tableModel);

        tableModel.setColumnIdentifiers(columnsHeader);
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));

        table = new JTable(tableModel);
        table.setRowSorter(sorter);
        table.setPreferredScrollableViewportSize(new Dimension(1000, 500));
        table.setFillsViewportHeight(true);
        DefaultTableModel contactTableModel = (DefaultTableModel) table
                .getModel();
        contactTableModel.setColumnIdentifiers(columnsHeader);


        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    connection.send(serialize(new AuthResponse("show", session.getUser(), session.isAuthorized(), "", "")));
                    String output = connection.recieve().getCommand();
                    data = parseList(output);
                    setData(data);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }, 2 * 1000, 10 * 1000);
        TableCellEditor editor = table.getDefaultEditor(Object.class);

// Add a cell editor listener
        editor.addCellEditorListener(new CellEditorListener() {
            public void editingStopped(ChangeEvent e) {
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();
                String login = (String) table.getValueAt(row, 12);
                Long id = (Long) table.getValueAt(row, 0);


                if (session.getUser().equals(login)) {
                    HumanBeing hb = getById(id);
                    switch (col) {
                        case 1: {
                            Object line = table.getValueAt(row, 0);
                            if (line != null && !line.equals("")) {
                                hb.setName((String) line);
                            } else {
                                JOptionPane.showMessageDialog(TableFrame.this, "Name is unacceptable!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        }
                        case 2: {
                            Object line = table.getValueAt(row, 1);
                            if ((!Objects.equals(line, "\n")) && !line.equals("") && (line != null)) {
                                try {
                                    if ((Integer) line <= 493) {
                                        hb.getCoordinates().setX((Integer) line);
                                    } else {
                                        JOptionPane.showMessageDialog(TableFrame.this, "Coordinate X must be lower than 494!",
                                                "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(TableFrame.this, "Coordinate X must be a digit!",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(TableFrame.this, "Coordinate X must be a digit!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        }

                        case 3: {
                            Object line = table.getValueAt(row, 3);
                            if ((!Objects.equals(line, "\n")) && !line.equals("") && (line != null)) {
                                try {
                                    if ((Integer) line <= 493) {
                                        hb.getCoordinates().setY((Long) line);
                                    } else {
                                        JOptionPane.showMessageDialog(TableFrame.this, "Coordinate Y must be lower than 494!",
                                                "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(TableFrame.this, "Coordinate Y must be a digit!",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(TableFrame.this, "Coordinate Y must be a digit!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        }

                        case 5: {
                            Object line = table.getValueAt(row, 5);
                            switch ((String) line) {
                                case "Да", "true" -> hb.setRealHero(true);
                                case "Нет", "false" -> hb.setRealHero(false);
                                default -> JOptionPane.showMessageDialog(TableFrame.this, "RealHero value is unacceptable!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        }
                        case 6: {
                            Object line = table.getValueAt(row, 6);
                            switch ((String) line) {
                                case "Да", "true" -> hb.setHasToothpick(true);
                                case "Нет", "false" -> hb.setHasToothpick(false);
                                default ->
                                        JOptionPane.showMessageDialog(TableFrame.this, "Toothpick value is unacceptable!",
                                                "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        }
                        case 7: {
                            Object line = table.getValueAt(row, 7);
                            try {
                                if (line != null && (Long) line <= 572 && (Long) line >= 0 && !line.equals("")) {
                                    hb.setImpactSpeed((Long) line);
                                } else {
                                    JOptionPane.showMessageDialog(TableFrame.this, "Impact speed must be positive number and lower 573!",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(TableFrame.this, "Impact speed must be a digit!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        }
                        case 8: {
                            String line = (String) table.getValueAt(row, 8);
                            if (line != null && !line.equals("")) {
                                hb.setSoundtrackName(line);
                            } else {
                                JOptionPane.showMessageDialog(TableFrame.this, "Soundtrack name is unacceptable!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        }
                        case 9: {
                            String line = (String) table.getValueAt(row, 9);
                            switch (line) {
                                case "Shotgun" -> hb.setWeaponType(WeaponType.SHOTGUN);
                                case "Knife" -> hb.setWeaponType(WeaponType.KNIFE);
                                case "Machine gun" -> hb.setWeaponType(WeaponType.MACHINE_GUN);
                                case "Rifle" -> hb.setWeaponType(WeaponType.RIFLE);
                                default -> JOptionPane.showMessageDialog(TableFrame.this, "Weapon type is unacceptable!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        }
                        case 10: {
                            String line = (String) table.getValueAt(row, 10);
                            switch (line) {
                                case "Longing" -> hb.setMood(Mood.LONGING);
                                case "Gloom" -> hb.setMood(Mood.GLOOM);
                                case "Frenzy" -> hb.setMood(Mood.FRENZY);
                                default -> JOptionPane.showMessageDialog(TableFrame.this, "Mood is unacceptable!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        }
                        case 11: {
                            String line = (String) table.getValueAt(row, 12);
                            switch (line) {
                                case "Да", "true" -> hb.getCar().setCool(true);
                                case "Нет", "false" -> hb.getCar().setCool(false);
                                default -> JOptionPane.showMessageDialog(TableFrame.this, "Car value is unacceptable!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        }
                    }
                    try{
//                        #TODO тут ошибка вылазит
                        connection.send(serialize(new AuthResponse("update", session.getUser(), session.isAuthorized(), String.valueOf(hb.getId()), hb.toString())));
                        AuthResponse response = connection.recieve();
                        JOptionPane.showMessageDialog(TableFrame.this, response.getCommand(),
                                "Information", JOptionPane.INFORMATION_MESSAGE);
                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(TableFrame.this, ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(TableFrame.this, "You can't change date of not your object!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            public void editingCanceled(ChangeEvent e) {}
        });

        table.getSelectionModel().addListSelectionListener(
                event -> {
                    int viewRow = table.getSelectedRow();
                    if (viewRow < 0) {
                        statusbar.setText("");
                    } else {
                        int modelRow =
                                table.convertRowIndexToModel(viewRow);
                        statusbar.setText("" +
                                String.format("Selected Row in view: %d. " +
                                                "Selected Row in model: %d.",
                                        viewRow, modelRow));
                    }

                }
        );

        JScrollPane scrollPane = new JScrollPane(table);

        tablePanel.add(scrollPane);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.X_AXIS));
        JLabel l1 = new JLabel("Filter Text:");
        l1.setAlignmentY(Component.CENTER_ALIGNMENT);
        l1.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel selRow = new JPanel();
        JLabel l2 = new JLabel("Selected Row №");
        l1.setSize(new Dimension(50, 50));
        l2.setSize(new Dimension(50, 50));
        l2.setLabelFor(selectedRow);
        l2.setAlignmentX(Component.LEFT_ALIGNMENT);
        selRow.setLayout(new BoxLayout(selRow, BoxLayout.X_AXIS));
        selRow.add(l2);
        selRow.add(selectedRow);
        JPanel labels = new JPanel();
        labels.setLayout(new BoxLayout(labels, BoxLayout.Y_AXIS));
        labels.add(l1);
        labels.add(Box.createRigidArea(new Dimension(0, 30)));
        labels.add(selRow);
        form.add(labels);
        filterText = new JTextField();
        filterText.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        newFilter();
                    }

                    public void insertUpdate(DocumentEvent e) {
                        newFilter();
                    }

                    public void removeUpdate(DocumentEvent e) {
                        newFilter();
                    }
                });

        form.add(filterText);
        tablePanel.add(form);
        setContentPane(tablePanel);
        pack();
        setLocationRelativeTo(null);
        setSize(1200, 800);
        setLocation(0, 0);
    }

    private void newFilter() {
        RowFilter<DefaultTableModel, Object> rf = null;
        try {
            rf = RowFilter.regexFilter(filterText.getText(), Integer.parseInt(selectedRow.getText()) - 1);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }

    @Override
    void updateLanguage(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("GUI.resources.Locale", locale);
        System.out.println(resourceBundle.getLocale().getCountry());
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

    private List<Object[]> parseList(String output) {
        List<Object[]> list = new ArrayList<Object[]>();
        currentUserList.clear();
        if (!output.equals("Collection is empty\n")) {
            for (String arges : output.split("\n\n")) {
                String[] args = arges.split("\n");
                Object[] fields = new Object[13];
                HumanBeing hb = new HumanBeing();
                readHumanBeingFromConsole.initializeHumanBeing(hb, new Scanner(arges), true);
                hb.setId(Long.parseLong(args[0]));
                hb.setLogin(args[12]);
                String time = args[4].substring(0, 19);
                time = time.replace("T", " ");
                LocalDateTime datetime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                fields[0] = hb.getId();
                fields[1] = hb.getName();
                fields[2] = hb.getCoordinates().getX();
                fields[3] = hb.getCoordinates().getY();
                fields[4] = datetime.format(currentDateTimeFormatter);
                fields[5] = hb.isRealHero();
                fields[6] = hb.getHasToothpick();
                fields[7] = hb.getImpactSpeed();
                fields[8] = hb.getSoundtrackName();
                fields[9] = hb.getWeaponType();
                fields[10] = hb.getMood();
                fields[11] = hb.getCar().getCool();
                fields[12] = hb.getLogin();
                list.add(fields);
                currentUserList.add(hb);
            }
        }
        return list;
    }

    public HumanBeing getById(Long id) {
        return currentUserList.stream().filter(o -> o.getId() == id).findFirst().get();
    }
}
