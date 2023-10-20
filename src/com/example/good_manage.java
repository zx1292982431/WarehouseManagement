package com.example;

import db.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class good_manage {
    private JPanel tab_area;
    private JPanel out_ware_house_manage;
    private JPanel warehouse_manage_tab;
    private JPanel inventory_manage;
    private JPanel good_manage_tab;
    private JPanel in_warehouse_manage;
    private JPanel main_window;
    private JPanel main_body;
    private JButton add_Button;
    private JButton delete_Button;
    private JButton update_Button;
    private JTextField add_goods_singleValue;
    private JTable goods_table;
    private JTextField delete_goods_name;
    private JTextField update_goods_name;
    private JTextField add_goods_name;
    private JTextField update_goods_singleValue;
    private JTextField add_goods_capacity;
    private JTextField update_goods_capacity;
    private JButton refresh_button;

    public JPanel getMain_window() {
        return main_window;
    }

    public JPanel getMain_body() {
        return main_body;
    }

    //
    //当前选中行
    public int[] current_selected_row = {-1};
    //一共多少行
    public int row_number = 0;
    //
    public String[][] tableData;
    public good_manage() {

        JFrame frame = new JFrame("货品管理");
        frame.setContentPane(this.main_window);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(login.window_x,login.window_y,login.window_width,login.window_height);
        frame.setVisible(true);
        try
        {
            load_table();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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





        //新增货品
        add_Button.addMouseListener(new MouseAdapter() {
            @Override

            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String a_g_name = add_goods_name.getText();
                String a_g_singleValue = add_goods_singleValue.getText();
                String a_g_capacity = add_goods_capacity.getText();

                if(!a_g_name.trim().isEmpty() && !a_g_singleValue.trim().isEmpty() && !a_g_capacity.trim().isEmpty())
                {
                    Database db = new Database("manage_system");
                    Statement stat = db.getStatement();
                    //int goods_id;
                    //int goods_cap = Integer.parseInt(a_g_capacity);
                    //int goods_value = Integer.parseInt(a_g_singleValue);

                    double goods_cap = Double.parseDouble(a_g_capacity);
                    double goods_value = Double.parseDouble(a_g_singleValue);
                    String goods_name = a_g_name;
                    String add_goods = "insert into good values(NULL,'"+goods_cap+"','"+goods_value+"','"+goods_name+"');";

                    try {
                        int i = stat.executeUpdate(add_goods);
                        //System.out.println("成功添加"+i+"行");
                        JOptionPane.showMessageDialog(null,"成功添加"+i+"行");//弹窗

                        load_table();

                        stat.close();
                        //成功后清空
                        add_goods_name.setText("");
                        add_goods_singleValue.setText("");
                        add_goods_capacity.setText("");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else{
                    String m ="缺少必要的输入：";
                    if(a_g_name.trim().isEmpty())m+="货品名称 ";
                    if(a_g_singleValue.trim().isEmpty())m+="单个价值 ";
                    if(a_g_capacity.trim().isEmpty())m+="单个占用容量 ";
                    JOptionPane.showMessageDialog(null,m);
                }


                //System.out.println("ddd");
            }
        });
        //删除货品
        delete_Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String d_g_name = delete_goods_name.getText();
                if(!d_g_name.trim().isEmpty())
                {
                    Database db = new Database("manage_system");
                    Statement stat = db.getStatement();

                    String delete_goods = "delete from good where name = '"+d_g_name+"';";

                    try {
                        int i = stat.executeUpdate(delete_goods);
                        JOptionPane.showMessageDialog(null,"已删除"+i+"行");

                        load_table();
                        //成功后清空
                        delete_goods_name.setText("");

                        stat.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"缺少商品名称");
                }

            }
        });

        //更新货品
        update_Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                String u_g_name = update_goods_name.getText();
                String u_g_singleValue = update_goods_singleValue.getText();
                String u_g_capacity = update_goods_capacity.getText();

                //System.out.println(u_g_name);
                //if(u_g_name == "") {System.out.println("aaa");}
                //System.out.println(u_g_name+" "+u_g_singleValue+" "+u_g_capacity);
                if(!u_g_name.trim().isEmpty())//name不空
                {
                    if(!u_g_singleValue.trim().isEmpty())//value不空
                    {
                        double goods_value = Double.parseDouble(u_g_singleValue);
                        String update_goods = "update good set value = '"+u_g_singleValue+"' where name = '"+u_g_name+"';";

                        Database db = new Database("manage_system");
                        Statement stat = db.getStatement();

                        try {
                            int i = stat.executeUpdate(update_goods);

                            JOptionPane.showMessageDialog(null,"已更新"+i+"行(单个价值)");

                            load_table();
                            //清空
                            update_goods_capacity.setText("");
                            update_goods_name.setText("");
                            update_goods_singleValue.setText("");
                            stat.close();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    if(!u_g_capacity.trim().isEmpty())//capacity不空
                    {
                        Double goods_capacity = Double.parseDouble(u_g_capacity);
                        String update_goods = "update good set cap = '"+goods_capacity+"' where name = '"+u_g_name+"';";

                        Database db = new Database("manage_system");
                        Statement stat = db.getStatement();

                        try {
                            int i = stat.executeUpdate(update_goods);
                            JOptionPane.showMessageDialog(null,"已更新"+i+"行(容量)");

                            load_table();
                            //清空
                            update_goods_capacity.setText("");
                            update_goods_name.setText("");
                            update_goods_singleValue.setText("");
                            stat.close();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"缺少要更新的商品名称");
                }


            }
        });


        goods_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                current_selected_row[0] = goods_table.getSelectedRow();

                delete_goods_name.setText(tableData[current_selected_row[0]][1]);

                update_goods_name.setText(tableData[current_selected_row[0]][1]);
                update_goods_capacity.setText(tableData[current_selected_row[0]][3]);
                update_goods_singleValue.setText(tableData[current_selected_row[0]][2]);
            }
        });
        refresh_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    load_table();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public void load_table() throws SQLException {
        Database db = new Database("manage_system");
        Statement stat = db.getStatement();

        String get_rows = "select count(*) from good;";
        ResultSet rs1 = stat.executeQuery(get_rows);
        //row_number = 0;

        while (rs1.next())
        {
            row_number = rs1.getInt("count(*)");
            //System.out.println("伟大的"+row_number);
        }

        String get_goods_info = "select * from good;";
        ResultSet rs = stat.executeQuery(get_goods_info);


        //String[][] tableData = new String[row_number][4];
        tableData = new String[row_number][4];
        int current_load_row = 0;
        while (rs.next())
        {
            tableData[current_load_row][0] = rs.getString("good_id");
            tableData[current_load_row][1] = rs.getString("name");
            tableData[current_load_row][2] = rs.getString("value");
            tableData[current_load_row][3] = rs.getString("cap");

            current_load_row++;
        }
        for(int i = 0;i < row_number;i++)
        {
            for(int j = 0;j<4;j++)
            {
                System.out.print(tableData[i][j]+" ");

            }
            System.out.println("");
        }

        String[] tableHead = {"货品编号","货品名称","单个价值","单个占用容量"};
        DefaultTableModel table = new DefaultTableModel(tableData,tableHead){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        //goods_table =new JTable(table);
        goods_table.setModel(table);
        //goods_table.isCellEditable(2,2);
    }
}
