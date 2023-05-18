package GUI;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class TableFrame extends ExtendableJFrame{
    DefaultTableModel tableModel;

    private Object[][] data = new String[][]{{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "bbbb"},
            {"6", "22", "5152", "783", "30", "13", "131", "16", "153", "1", "13", "15" , "aaa"}};
    private Object[] columnsHeader = new String[] {"id", "name", "x", "y", "creationDate", "realHero", "hasToothpick",
    "ImpactSpeed", "SoundtrackName", "WeaponType", "Mood", "Car", "user"};
    private JTable table;
    public TableFrame(){
        initUI();
    }
    @Override
    void initUI(){
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




//     #TODO   обновлять данные таблицы с помощью метода setData



        setData(data);
        Box contents = new Box(BoxLayout.Y_AXIS);
        contents.add(new JScrollPane(table));
        getContentPane().add(contents);
        table.getSelectionModel().addListSelectionListener(
                event -> {
                    int viewRow = table.getSelectedRow();
                    if (viewRow < 0) {
                        //Selection got filtered away.
                        statusbar.setText("");
                    } else {
                        int modelRow =
                                table.convertRowIndexToModel(viewRow);
                        statusbar.setText(
                                String.format("Selected Row in view: %d. " +
                                                "Selected Row in model: %d.",
                                        viewRow, modelRow));
                    }
                }
        );
        TableRowSorter<TableModel> sorter
                = new TableRowSorter<TableModel>(table.getModel());
        table.setRowSorter(sorter);

        updateLanguage(Locale.getDefault());
        pack();
        setLocationRelativeTo(null);
        setSize(1200, 800);
        setLocation(0, 0);
    }


    @Override
    void updateLanguage(Locale locale){
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
        this.data = data;
        for (Object[] e: this.data){
            tableModel.addRow(e);
        }
        tableModel.fireTableDataChanged();
    }
}
