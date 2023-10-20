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

public class inventory_manage {
    private JPanel main_window;
    private JPanel tab_area;
    private JPanel out_ware_house_manage;
    private JPanel warehouse_manage_tab;
    private JPanel inventory_manage;
    private JPanel good_manage_tab;
    private JPanel in_warehouse_manage;
    private JPanel main_body;
    private JTable table1;
    private JButton al_select_Button;
    private JTextField textField1;
    private JButton good_select_Button;
    private JTextField textField2;
    private JButton warehouse_select_Button;

    public inventory_manage() {
        JFrame frame = new JFrame("库存管理");
        frame.setContentPane(this.main_window);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(login.window_x,login.window_y,login.window_width,login.window_height);
        frame.setVisible(true);

        warehouse_manage_tab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("点击了仓库管理");
                frame.dispose();
                warehouse_manage wm=new warehouse_manage();
                try {
                    wm.load_table();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
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
        good_select_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    load_table_good();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "该商品不存在", "提示消息",JOptionPane.WARNING_MESSAGE);
                    throw new RuntimeException(ex);
                }
            }
        });
        warehouse_select_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    load_table_warehouse();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "该仓库不存在", "提示消息",JOptionPane.WARNING_MESSAGE);
                    throw new RuntimeException(ex);
                }
            }
        });
        al_select_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    load_table_all();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void load_table_all() throws SQLException {
        Database db=new Database("manage_system");
        Statement statement = db.getStatement();
        String sql="select name,warehouse_id,good_num from good,deposit where good.good_id=deposit.good_id;";
        String get_count="select count(*) from deposit;";
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
            tableData[current_load_row][0] = resultset.getString("name");
            tableData[current_load_row][1] = resultset.getString("warehouse_id");
            tableData[current_load_row][2] = resultset.getString("good_num");

            current_load_row++;
        }

        String[] tableHead = {"货品名称","所在仓库","库存量"};
        DefaultTableModel table = new DefaultTableModel(tableData,tableHead){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        //goods_table =new JTable(table);
        table1.setModel(table);

    }

    public void load_table_good() throws SQLException {
        String good_name=textField1.getText();
        String good_id="";
        Database db=new Database("manage_system");
        Statement statement = db.getStatement();
        String get_good_id="select * from good where name='"+good_name+"';";
        ResultSet rs2=statement.executeQuery(get_good_id);
        if(rs2.next())
        {
            good_id=rs2.getString("good_id");
            System.out.println("good_id:"+good_id);
        }
        else
        {
            System.out.println("该商品不存在");
            //JOptionPane.showMessageDialog(null, "该商品不存在", "提示消息",JOptionPane.WARNING_MESSAGE);
        }


        String sql="select name,warehouse_id,good_num from good,deposit where good.good_id=deposit.good_id and deposit.good_id="+good_id+";";
        String get_count="select count(*) from good,deposit where good.good_id=deposit.good_id and deposit.good_id="+good_id+";";
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
            tableData[current_load_row][0] = resultset.getString("name");
            tableData[current_load_row][1] = resultset.getString("warehouse_id");
            tableData[current_load_row][2] = resultset.getString("good_num");

            current_load_row++;
        }

        String[] tableHead = {"货品名称","所在仓库","库存量"};
        DefaultTableModel table = new DefaultTableModel(tableData,tableHead){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        //goods_table =new JTable(table);
        table1.setModel(table);

    }

    public void load_table_warehouse() throws SQLException {
        String warehouse_id=textField2.getText();
        Database db=new Database("manage_system");
        Statement statement = db.getStatement();
        String sql="select name,warehouse_id,good_num from good,deposit where good.good_id=deposit.good_id and warehouse_id="+warehouse_id+";";
        String get_count="select count(*) from good,deposit where good.good_id=deposit.good_id and warehouse_id="+warehouse_id+";";
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
            tableData[current_load_row][0] = resultset.getString("name");
            tableData[current_load_row][1] = resultset.getString("warehouse_id");
            tableData[current_load_row][2] = resultset.getString("good_num");

            current_load_row++;
        }

        String[] tableHead = {"货品名称","所在仓库","库存量"};
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
