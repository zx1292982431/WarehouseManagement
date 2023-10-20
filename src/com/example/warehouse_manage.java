package com.example;

import db.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class warehouse_manage {
    private JPanel main_window;
    private JPanel warehouse_manage_tab;
    private JPanel good_manage_tab;
    private JPanel inventory_manage;
    private JPanel in_warehouse_manage;
    private JPanel out_ware_house_manage;
    private JPanel tab_area;
    private JTable table1;
    private JTextField insert_max_cap_textField;
    private JButton insert_Button;
    private JTextField del_warehouse_id_textField;
    private JButton del_Button;
    private JTextField update_warehouse_id_textField;
    private JTextField update_max_cap_textField;
    private JButton update_Button;

    public warehouse_manage() {

        JFrame frame = new JFrame("仓库管理");
        frame.setContentPane(this.main_window);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(login.window_x,login.window_y,login.window_width,login.window_height);
        frame.setVisible(true);

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int current_row = table1.getSelectedRow();
                int current_col = table1.getSelectedColumn();
                del_warehouse_id_textField.setText((String)table1.getValueAt(current_row,0));
                update_warehouse_id_textField.setText((String)table1.getValueAt(current_row,0));
                update_max_cap_textField.setText((String)table1.getValueAt(current_row,1));
            }
        });

        warehouse_manage_tab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("点击了仓库管理");
            }
        });
        good_manage_tab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("点击了货品管理");
                frame.dispose();
                good_manage gm=new good_manage();
                try {
                    gm.load_table();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        inventory_manage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("点击了库存管理");
                frame.dispose();
                inventory_manage im=new inventory_manage();
                try {
                    im.load_table_all();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        in_warehouse_manage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("点击了入库管理");
                frame.dispose();
                in_warehouse_manage iwm=new in_warehouse_manage();
                iwm.load_table_all();
            }
        });
        out_ware_house_manage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("点击了出库管理");
                frame.dispose();
                out_warehouse_manage owm=new out_warehouse_manage();
                owm.load_table_all();
            }
        });
        insert_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String max_cap=insert_max_cap_textField.getText();
                String sql="insert into warehouse (max_cap,used_cap) values("+max_cap+",0);";
                System.out.println(sql);
                Database db = new Database("manage_system");
                Statement statement = db.getStatement();
                try {
                    statement.executeUpdate(sql);
                    load_table();
                    JOptionPane.showMessageDialog(frame, "添加成功", "提示消息",JOptionPane.WARNING_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "添加失败", "提示消息",JOptionPane.WARNING_MESSAGE);
                    throw new RuntimeException(ex);
                }
            }
        });
        del_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String warehouse_id=del_warehouse_id_textField.getText();
                String sql="delete from warehouse where warehouse_id="+warehouse_id+";";
                System.out.println(sql);
                Database db = new Database("manage_system");
                Statement statement = db.getStatement();
                try {
                    statement.executeUpdate(sql);
                    load_table();
                    JOptionPane.showMessageDialog(frame, "删除成功", "提示消息",JOptionPane.WARNING_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "删除失败", "提示消息",JOptionPane.WARNING_MESSAGE);
                    throw new RuntimeException(ex);
                }
            }
        });
        update_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String warehouse_id=update_warehouse_id_textField.getText();
                String max_cap=update_max_cap_textField.getText();
                String sql="update warehouse set max_cap="+max_cap+" where warehouse_id="+warehouse_id+";";
                System.out.println(sql);
                Database db = new Database("manage_system");
                Statement statement = db.getStatement();
                try {
                    statement.executeUpdate(sql);
                    load_table();
                    JOptionPane.showMessageDialog(frame, "更新成功", "提示消息",JOptionPane.WARNING_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "更新失败", "提示消息",JOptionPane.WARNING_MESSAGE);
                    throw new RuntimeException(ex);
                }
            }
        });
    }


    public void load_table() throws SQLException {
        Database db=new Database("manage_system");
        Statement statement = db.getStatement();
        String sql="select * from warehouse;";
        String get_count="select count(*) from warehouse;";
        ResultSet rs1 = statement.executeQuery(get_count);
        int row_number = 0;
        while (rs1.next())
        {
            row_number = rs1.getInt("count(*)");
        }
        ResultSet resultset = statement.executeQuery(sql);

        String[][] tableData = new String[row_number][3];

        int current_load_row = 0;
        while (resultset.next())
        {
            System.out.println(current_load_row);
            tableData[current_load_row][0] = resultset.getString("warehouse_id");
            tableData[current_load_row][1] = resultset.getString("max_cap");
            tableData[current_load_row][2] = resultset.getString("used_cap");


            current_load_row++;
        }

        String[] tableHead = {"仓库编号","最大容量","已用容量"};
        DefaultTableModel table = new DefaultTableModel(tableData,tableHead){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        //goods_table =new JTable(table);
        table1.setModel(table);

    }


}
