package com.example;

import db.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import db.out_warehouse;
import java.util.Vector;
import java.util.Date;
import java.text.SimpleDateFormat;

public class out_warehouse_manage {
    private JPanel main_window;
    private JPanel tab_area;
    private JPanel out_ware_house_manage;
    private JPanel warehouse_manage_tab;
    private JPanel inventory_manage;
    private JPanel good_manage_tab;
    private JPanel in_warehouse_manage;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton 出库Button;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JButton 查询Button;
    private JButton 查询全部订单Button;

    public static void main(String[] args) {
        JFrame frame = new JFrame("out_warehouse_manage");
        frame.setContentPane(new out_warehouse_manage().main_window);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public out_warehouse_manage() {

        JFrame frame = new JFrame("出库管理");
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
            }
        });
        出库Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Database database=new Database("manage_system");
                String good_name= textField1.getText();
                Integer good_id=0;
                Integer good_cap=0;
                try {
                    ResultSet rs1=database.getStatement().executeQuery("select * from good where name='"+good_name+"';");
                    if(rs1.next())
                    {
                        good_id=rs1.getInt("good_id");
                        good_cap=rs1.getInt("cap");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


                Integer warehouse_id=Integer.valueOf(textField2.getText());
                Integer out_warehouse_num=Integer.valueOf(textField3.getText());
                String admin_id=login.admin_id;
                Date now1 = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String out_warehouse_time=format.format(now1);
                int result=database.out_warehouse_add(admin_id,warehouse_id,good_id,out_warehouse_num,out_warehouse_time);


                load_table_all();
                if (result==1)
                {
                    JOptionPane.showMessageDialog(frame, "出库成功", "提示消息",JOptionPane.WARNING_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(frame, "出库失败", "提示消息",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        查询Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Integer warehouse_id;
                Integer out_warehouse_id;
                String good_name;
                Integer good_id=0;
                String admin_id;
                Database database=new Database("manage_system");
                if (textField4.getText().length()==0)
                {
                    out_warehouse_id=null;}
                else
                {out_warehouse_id= Integer.valueOf(textField4.getText());}
                if (textField5.getText().length()==0)
                {
                    warehouse_id=null;}
                else
                {warehouse_id=Integer.valueOf(textField5.getText());}
                if (textField6.getText().length()==0)
                {
                    good_id=null;}
                else
                {
                    good_name=textField6.getText();
                    try {
                        ResultSet rs1=database.getStatement().executeQuery("select * from good where name='"+good_name+"';");
                        if(rs1.next())
                        {
                            good_id=rs1.getInt("good_id");
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (textField7.getText().length()==0)
                {
                    admin_id=null;}
                else
                {admin_id=textField7.getText();}
                final List<out_warehouse> out_warehouses=database.out_select(out_warehouse_id,admin_id,warehouse_id,good_id);
                Object [][] data=new Object[out_warehouses.size()][6];
                int i=0;
                for(out_warehouse outwarehouse:out_warehouses)
                {
                    data[i][0]=outwarehouse.get_out_warehouse_id();
                    data[i][1]=outwarehouse.get_admin_id();
                    data[i][2]=outwarehouse.get_warehouse_id();
                    data[i][3]=outwarehouse.get_good_name();
                    data[i][4]=outwarehouse.get_out_warehouse_num();
                    data[i][5]=outwarehouse.get_out_warehouse_date();
                    i++;
                }
                String[] index={"出库单号","管理员编号","仓库编号","货品名称","出库数量","出库时间"};
                DefaultTableModel model=new DefaultTableModel(data,index);
                table1.setModel(model);
            }
        });
        查询全部订单Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                load_table_all();
            }
        });
    }
    public void load_table_all()
    {
        Database database=new Database("manage_system");
        final List<out_warehouse> out_warehouses=database.out_select_all();
        Object [][] data=new Object[out_warehouses.size()][6];
        int i=0;
        for(out_warehouse outwarehouse:out_warehouses)
        {
            data[i][0]=outwarehouse.get_out_warehouse_id();
            data[i][1]=outwarehouse.get_admin_id();
            data[i][2]=outwarehouse.get_warehouse_id();
            data[i][3]=outwarehouse.get_good_name();
            data[i][4]=outwarehouse.get_out_warehouse_num();
            data[i][5]=outwarehouse.get_out_warehouse_date();
            i++;
        }
        String[] index={"出库单号","管理员编号","仓库编号","货品名称","出库数量","出库时间"};
        DefaultTableModel model=new DefaultTableModel(data,index);
        table1.setModel(model);
    }

}
