package com.example;

import javax.swing.*;

import Varify.EmailUtil;
import com.sun.tools.javac.Main;
import db.Database;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//hvqwwrrtzrgnfeef
public class login {
    public static String admin_id;
    public static int window_x;
    public static int window_y;
    public static int dialog_x;
    public static int dialog_y;
    public static int dialog_weight=400;
    public static int dialog_height=200;
    public static int window_width=800;
    public static int window_height=400;
    private JTextField user_id_field;
    private JPasswordField password_field;
    private JButton admin_login_Button;

    private JButton super_admin_login_Button;
    private JPanel main_window;


    public login() {
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        window_x = (int) screensize.getWidth() / 2 - window_width /2;
        window_y = (int) screensize.getHeight() / 2 - window_height /2;
        dialog_x = (int) screensize.getWidth() / 2 - dialog_weight /2;
        dialog_y = (int) screensize.getHeight() / 2 - dialog_height /2;

        JFrame frame = new JFrame("仓库库存管理系统");
        frame.setContentPane(this.main_window);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(window_x,window_y,window_width,window_height);
        frame.setVisible(true);



        admin_login_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user=user_id_field.getText();
                String password=password_field.getText();
                System.out.println("user:"+user);
                System.out.println("password:"+password);
                Database db=new Database("manage_system");
                Statement statement=db.getStatement();
                String sql_1="select * from admin where admin_id="+user+" and password= AES_ENCRYPT('"+password+"', 'key');";
                System.out.println(sql_1);
                try {ResultSet
                     resultSet = statement.executeQuery(sql_1);
                    if(resultSet.next()) //不为空，即登录成功
                    {
                        System.out.println("仓库管理员："+resultSet.getString("name")+"登录成功");
                        admin_id=resultSet.getString("admin_id");
                        System.out.println("admin_id:"+admin_id);
                        frame.dispose();
                        warehouse_manage wm=new warehouse_manage();
                        wm.load_table();

                    }
                    else //为空，登录失败
                    {
                        System.out.println("仓库管理员登录失败");
                        JOptionPane.showConfirmDialog(frame, "账号或密码错误", "登录失败", JOptionPane.DEFAULT_OPTION);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        super_admin_login_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String user=user_id_field.getText();
                String password=password_field.getText();
                System.out.println("user:"+user);
                System.out.println("password:"+password);
                Database db=new Database("manage_system");
                Statement statement=db.getStatement();
                String sql_2="select email from super_admin where super_admin_id="+user+" and password= AES_ENCRYPT('"+password+"', 'key');";
                System.out.println(sql_2);
                try {
                    ResultSet resultSet = statement.executeQuery(sql_2);
                    if(resultSet.next()) //账号密码正确，发送验证码
                    {
                        String toEmail=resultSet.getString("email");
                        System.out.println("发送验证码至to email:"+toEmail);
                        String Vcode=null;
                        EmailUtil em=EmailUtil.instance;
                        try {
                            em.sendEmail(toEmail);
                            Vcode=em.getVCode();
                        } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        Vcode_dialog vd=new Vcode_dialog(Vcode);
                        vd.setVisible(true);
                        frame.dispose();
                        employee_manage emm=new employee_manage();
                        emm.load_table();
                        //System.exit(0);
                    }
                    else //账号密码错误
                    {
                        System.out.println("系统管理员登录失败");
                        JOptionPane.showConfirmDialog(frame, "账号或密码错误", "登录失败", JOptionPane.DEFAULT_OPTION);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
        
        
    }

    public JPanel getMain_window()
    {
        return this.main_window;
    }

}
