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
import db.in_warehouse;
import java.util.Vector;
import java.util.Date;
import java.text.SimpleDateFormat;

public class in_warehouse_manage {

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
    private JButton 入库Button;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JButton 查询Button;
    private JButton 查询全部入库订单Button;
    private JTextField textField7;

    public static void main(String[] args) {
        JFrame frame = new JFrame("in_warehouse_manage");
        frame.setContentPane(new in_warehouse_manage().main_window);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public in_warehouse_manage() {

        JFrame frame = new JFrame("入库管理");
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
        入库Button.addActionListener(new ActionListener() {
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
                Integer in_warehouse_num=Integer.valueOf(textField3.getText());
                String admin_id=login.admin_id;
                Integer in_warehouse_id=null;
                Date now1 = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String in_warehouse_time=format.format(now1);
                int result=database.in_warehouse_add(in_warehouse_id,admin_id,warehouse_id,good_id,in_warehouse_num,in_warehouse_time);


                load_table_all();
                if(result==1)
                {
                    JOptionPane.showMessageDialog(frame, "入库成功", "提示消息",JOptionPane.WARNING_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(frame, "入库失败", "提示消息",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        查询Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Integer warehouse_id;
                Integer in_warehouse_id;
                Integer good_id=0;
                String admin_id;
                String good_name;
                Database database=new Database("manage_system");
                if (textField4.getText().length()==0)
                {
                    in_warehouse_id=null;
                }
                else{
                in_warehouse_id= Integer.valueOf(textField4.getText());}
                if (textField5.getText().length()==0)
                {
                    warehouse_id=null;
                }
                else{
                warehouse_id=Integer.valueOf(textField5.getText());}
                if (textField6.getText().length()==0)
                {
                    good_id=null;
                }
                else{
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
                    admin_id=null;
                }
                else{
                admin_id=textField7.getText();}
                database.in_select(in_warehouse_id,admin_id,warehouse_id,good_id);
                final List<in_warehouse> In_warehouses=database.in_select(in_warehouse_id,admin_id,warehouse_id,good_id);
                Object [][] data=new Object[In_warehouses.size()][6];
                int i=0;
                for(in_warehouse Inwarehouse:In_warehouses) {
                    data[i][0] = Inwarehouse.get_in_warehouse_id();
                    data[i][1] = Inwarehouse.get_admin_id();
                    data[i][2] = Inwarehouse.get_warehouse_id();
                    data[i][3] = Inwarehouse.get_good_name();
                    data[i][4] = Inwarehouse.get_in_warehouse_num();
                    data[i][5] = Inwarehouse.get_in_warehouse_date();
                    i++;
                }
                String[] index={"入库单号","管理员编号","仓库编号","货品名称","入库数量","入库时间"};
                DefaultTableModel model=new DefaultTableModel(data,index);
                table1.setModel(model);
            }
        });
        查询全部入库订单Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                load_table_all();
;            }
        });
    }
    public void load_table_all()
    {
        Database database=new Database("manage_system");
        final List<in_warehouse> in_warehouses=database.in_select_all();
        Object [][] data=new Object[in_warehouses.size()][6];
        int i=0;
        for(in_warehouse inwarehouse:in_warehouses)
        {
            data[i][0]=inwarehouse.get_in_warehouse_id();
            data[i][1]=inwarehouse.get_admin_id();
            data[i][2]=inwarehouse.get_warehouse_id();
            data[i][3]=inwarehouse.get_good_name();
            data[i][4]=inwarehouse.get_in_warehouse_num();
            data[i][5]=inwarehouse.get_in_warehouse_date();
            i++;
        }
        String[] index={"入库单号","管理员编号","仓库编号","货品名称","入库数量","入库时间"};
        DefaultTableModel model=new DefaultTableModel(data,index);
        table1.setModel(model);
    }
}