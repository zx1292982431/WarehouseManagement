package db;

import db.in_warehouse;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
public class Database {
    private Connection connection;
    private PreparedStatement statement;
    private Statement statement1;

    public Database(String database) {
        System.out.println("连接成功！");
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306" + "/" + database;
            String username = "root";
            String password = "sr20020917";
            Class.forName(driver);
            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("连接成功！");
            this.statement1 = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public int in_warehouse_add(Integer in_warehouse_id, String admin_id, Integer warehouse_id, Integer good_id, Integer in_warehouse_num, String in_warehouse_time) {

        String sql = "insert into in_warehouse (in_warehouse_id,admin_id,warehouse_id,good_id,in_warehouse_num,in_warehouse_time) values(NULL,?,?,?,?,?)";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, admin_id);
            statement.setString(2, String.valueOf(warehouse_id));
            statement.setString(3, String.valueOf(good_id));
            statement.setString(4, String.valueOf(in_warehouse_num));
            statement.setString(5, String.valueOf(in_warehouse_time));
            statement.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public int out_warehouse_add(String admin_id, Integer warehouse_id, Integer good_id, Integer out_warehouse_num, String out_warehouse_time) {

        String sql = "insert into out_warehouse (out_warehouse_id,admin_id,warehouse_id,good_id,out_warehouse_num,out_warehouse_time) values(NULL,?,?,?,?,?)";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, admin_id);
            statement.setString(2, String.valueOf(warehouse_id));
            statement.setString(3, String.valueOf(good_id));
            statement.setString(4, String.valueOf(out_warehouse_num));
            statement.setString(5, String.valueOf(out_warehouse_time));
            statement.executeUpdate();
            return 1;
        } catch (SQLException e) {
            System.out.println("asasasasasasasasas");
            e.printStackTrace();
            return 0;
        }
    }
    public List<out_warehouse> out_select_all() {
        String sql = "select out_warehouse_id,admin_id,out_warehouse.warehouse_id,name,out_warehouse_num,out_warehouse_time from out_warehouse,good where out_warehouse.good_id=good.good_id;";
        List<out_warehouse> out_warehouses = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                out_warehouse outwarehouse = new out_warehouse();
                outwarehouse.set_out_warehouse_id(resultSet.getInt("out_warehouse_id"));
                outwarehouse.set_admin_id(resultSet.getString("admin_id"));
                outwarehouse.set_warehouse_id(resultSet.getInt("warehouse_id"));
                outwarehouse.set_good_name(resultSet.getString("name"));
                outwarehouse.set_out_warehouse_num(resultSet.getInt("out_warehouse_num"));
                outwarehouse.set_out_warehouse_date(resultSet.getString("out_warehouse_time"));
                out_warehouses.add(outwarehouse);
            }
            return out_warehouses;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<in_warehouse> in_select_all() {
        String sql = "select in_warehouse_id,admin_id,in_warehouse.warehouse_id,name,in_warehouse_num,in_warehouse_time from in_warehouse,good where in_warehouse.good_id=good.good_id;";

        List<in_warehouse> in_warehouses = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                in_warehouse inwarehouse = new in_warehouse();
                inwarehouse.set_in_warehouse_id(resultSet.getInt("in_warehouse_id"));
                inwarehouse.set_admin_id(resultSet.getString("admin_id"));
                inwarehouse.set_warehouse_id(resultSet.getInt("warehouse_id"));
                inwarehouse.set_good_name(resultSet.getString("name"));
                inwarehouse.set_in_warehouse_num(resultSet.getInt("in_warehouse_num"));
                inwarehouse.set_in_warehouse_date(resultSet.getString("in_warehouse_time"));
                in_warehouses.add(inwarehouse);
            }
            return in_warehouses;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Statement getStatement() {
        return this.statement1;
    }
    public List<in_warehouse> in_select(Integer In_warehouse_id,String Admin_id,Integer Warehouse_id,Integer Good_id) {
        String sql = null,Obj1,Obj2,Obj3,Obj4;
        if(In_warehouse_id==null) {
            Obj1="in_warehouse_id!=0 and ";
        }
        else{
            Obj1="in_warehouse_id=? and ";
        }
        if(Admin_id==null){
            Obj2="admin_id!='null' and ";
        }
        else{
            Obj2="admin_id=? and ";
        }
        if(Warehouse_id==null){
            Obj3="warehouse_id!=0 and ";
        }
        else{
            Obj3="warehouse_id=? and ";
        }
        if(Good_id==null){
            Obj4="in_warehouse.good_id!=0;";
        }
        else{
            Obj4="in_warehouse.good_id=?;";
        }
        sql = "select in_warehouse_id,admin_id,in_warehouse.warehouse_id,name,in_warehouse_num,in_warehouse_time from in_warehouse,good where good.good_id=in_warehouse.good_id and "+Obj1+Obj2+Obj3+Obj4;
        List<in_warehouse> In_warehouses = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            Integer num=1;
            if(In_warehouse_id!=null)
            {
                statement.setString(num, String.valueOf(In_warehouse_id));
                num=num+1;}
            if(Admin_id!=null)
            {statement.setString(num, Admin_id);num=num+1;}
            if(Warehouse_id!=null)
            {statement.setString(num, String.valueOf(Warehouse_id));num=num+1;}
            if(Good_id!=null)
            {statement.setString(num, String.valueOf(Good_id));}
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                in_warehouse Inwarehouse = new in_warehouse();
                Inwarehouse.set_in_warehouse_id(resultSet.getInt("in_warehouse_id"));
                Inwarehouse.set_admin_id(resultSet.getString("admin_id"));
                Inwarehouse.set_warehouse_id(resultSet.getInt("warehouse_id"));
                Inwarehouse.set_good_name(resultSet.getString("name"));
                Inwarehouse.set_in_warehouse_num(resultSet.getInt("in_warehouse_num"));
                Inwarehouse.set_in_warehouse_date(resultSet.getString("in_warehouse_time"));
                In_warehouses.add(Inwarehouse);
            }
            return In_warehouses;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<out_warehouse> out_select(Integer out_warehouse_id,String admin_id,Integer warehouse_id,Integer good_id) {
        String sql = null,Obj1,Obj2,Obj3,Obj4;
        if(out_warehouse_id==null) {
            Obj1="out_warehouse_id!=0 and ";
        }
            else{
                Obj1="out_warehouse_id=? and ";
        }
          if(admin_id==null){
              Obj2="admin_id!='null' and ";
          }
          else{
              Obj2="admin_id=? and ";
          }
        if(warehouse_id==null){
            Obj3="warehouse_id!=0 and ";
        }
        else{
            Obj3="warehouse_id=? and ";
        }
        if(good_id==null){
            Obj4="out_warehouse.good_id!=0;";
        }
        else{
            Obj4="out_warehouse.good_id=?;";
        }
        sql = "select out_warehouse_id,admin_id,out_warehouse.warehouse_id,name,out_warehouse_num,out_warehouse_time from out_warehouse,good where good.good_id=out_warehouse.good_id and "+Obj1+Obj2+Obj3+Obj4;
        List<out_warehouse> out_warehouses = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            Integer num=1;
            if(out_warehouse_id!=null)
            {
            statement.setString(num, String.valueOf(out_warehouse_id));
            num=num+1;}
            if(admin_id!=null)
            {statement.setString(num, admin_id);num=num+1;}
            if(warehouse_id!=null)
            {statement.setString(num, String.valueOf(warehouse_id));num=num+1;}
            if(good_id!=null)
            {statement.setString(num, String.valueOf(good_id));}
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                out_warehouse outwarehouse = new out_warehouse();
                outwarehouse.set_out_warehouse_id(resultSet.getInt("out_warehouse_id"));
                outwarehouse.set_admin_id(resultSet.getString("admin_id"));
                outwarehouse.set_warehouse_id(resultSet.getInt("warehouse_id"));
                outwarehouse.set_good_name(resultSet.getString("name"));
                outwarehouse.set_out_warehouse_num(resultSet.getInt("out_warehouse_num"));
                outwarehouse.set_out_warehouse_date(resultSet.getString("out_warehouse_time"));
                out_warehouses.add(outwarehouse);
            }
            return out_warehouses;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
