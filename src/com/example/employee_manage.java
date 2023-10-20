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

public class employee_manage {
    private JPanel main_window;
    private JPanel main_body;
    private JTable table1;
    private JButton insertButton;
    private JTextField insert_name_textField;
    private JTextField insert_sex_textField;
    private JTextField insert_age_textField;
    private JTextField insert_salary_textField;
    private JPasswordField insert_password_passwordField;
    private JButton updateButton;
    private JTextField update_name_textField;
    private JTextField update_sex_textField;
    private JTextField update_age_textField;
    private JTextField update_salary_textField;
    private JTextField delete_admin_id_textField;
    private JButton deleteButton;
    private JTextField update_phone_textField;
    private JTextField insert_admin_id_textField;
    private JTextField insert_phone_textField;

    String final_admin_id="";

    public employee_manage() {

        JFrame frame = new JFrame("员工管理");
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
                final_admin_id=(String)table1.getValueAt(current_row,0);
                update_name_textField.setText((String)table1.getValueAt(current_row,1));
                update_sex_textField.setText((String)table1.getValueAt(current_row,2));
                update_age_textField.setText((String)table1.getValueAt(current_row,3));
                update_salary_textField.setText((String)table1.getValueAt(current_row,4));
                update_phone_textField.setText((String)table1.getValueAt(current_row,5));

                //System.out.println(current_row+","+current_col);

                //delete_goods_name.setText();
            }
        });

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String admin_id=insert_admin_id_textField.getText();
                String name=insert_name_textField.getText();
                String sex=insert_sex_textField.getText();
                String age=insert_age_textField.getText();
                String salary=insert_salary_textField.getText();
                String phone=insert_phone_textField.getText();
                String password=insert_password_passwordField.getText();
                String sql="insert into admin values('"+admin_id+"','"+name+"','"+sex+"',"+age+","+salary+","+phone+",AES_ENCRYPT('"+password+"','key'));";
                System.out.println(sql);
                Database db=new Database("manage_system");
                Statement statement = db.getStatement();
                try {
                    statement.executeUpdate(sql);
                    JOptionPane.showMessageDialog(frame, "添加成功", "提示消息",JOptionPane.WARNING_MESSAGE);
                    insert_admin_id_textField.setText("");
                    insert_name_textField.setText("");
                    insert_sex_textField.setText("");
                    insert_age_textField.setText("");
                    insert_salary_textField.setText("");
                    insert_phone_textField.setText("");
                    insert_password_passwordField.setText("");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "添加失败", "提示消息",JOptionPane.WARNING_MESSAGE);
                    throw new RuntimeException(ex);
                }
                try {
                    load_table();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }


        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String admin_id=final_admin_id;
                String name=update_name_textField.getText();
                String sex=update_sex_textField.getText();
                String age=update_age_textField.getText();
                String salary=update_salary_textField.getText();
                String phone=update_phone_textField.getText();
                Database db=new Database("manage_system");
                Statement statement=db.getStatement();
                ResultSet rs1= null;
                try {
                    rs1 = statement.executeQuery("select * from admin where name='"+name+"';");
                    rs1.next();
                    admin_id=rs1.getString("admin_id");
                } catch (SQLException ex) {
                    admin_id=final_admin_id;
                }
                String sql="update admin set name='"+name+"',sex='"+sex+"',age="+age+",salary="+salary+",phone='"+phone+"' where admin_id="+admin_id+";";
                System.out.println(sql);
                try {
                    statement.executeUpdate(sql);
                    load_table();
                    JOptionPane.showMessageDialog(frame, "修改成功", "提示消息",JOptionPane.WARNING_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "修改失败", "提示消息",JOptionPane.WARNING_MESSAGE);
                    throw new RuntimeException(ex);
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String admin_id=delete_admin_id_textField.getText();
                String sql="delete from admin where admin_id="+admin_id+";";
                Database db=new Database("manage_system");
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
    }

    public void load_table() throws SQLException {
        Database db=new Database("manage_system");
        Statement statement = db.getStatement();
        String sql="select * from admin;";
        String get_count="select count(*) from admin;";
        ResultSet rs1 = statement.executeQuery(get_count);
        int row_number = 0;
        while (rs1.next())
        {
            row_number = rs1.getInt("count(*)");
        }
        ResultSet resultset = statement.executeQuery(sql);

        String[][] tableData = new String[row_number][6];

        int current_load_row = 0;
        while (resultset.next())
        {
            System.out.println(current_load_row);
            tableData[current_load_row][0] = resultset.getString("admin_id");
            tableData[current_load_row][1] = resultset.getString("name");
            tableData[current_load_row][2] = resultset.getString("sex");
            tableData[current_load_row][3] = resultset.getString("age");
            tableData[current_load_row][4] = resultset.getString("salary");
            tableData[current_load_row][5] = resultset.getString("phone");

            current_load_row++;
        }

        String[] tableHead = {"员工编号","姓名","性别","年龄","工资","电话号码"};
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
