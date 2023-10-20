package com.example;

import javax.swing.*;
import java.awt.event.*;

public class Vcode_dialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField Vcodefield;

    public Vcode_dialog(String Vcode) {

        this.setBounds(login.dialog_x,login.dialog_y,login.dialog_weight,login.dialog_height);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("点击了ok按钮");
                String input_Vcode=Vcodefield.getText();
                if(Vcode.equals(input_Vcode)) //登录成功
                {
                    System.out.println("系统管理员登录成功");
                    dispose();
                }
                else
                {
                    System.out.println("验证码错误");
                    Vcode_error ve=new Vcode_error();
                    ve.setVisible(true);
                    //JOptionPane.showMessageDialog(null, "验证码错误，登录失败", "验证码错误",JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


}
