package db;

public class in_warehouse {
    private Integer in_warehouse_id;
    private String admin_id;
    private Integer warehouse_id;
    private Integer good_id;
    private String name;
    private Integer in_warehouse_num;
    private String in_warehouse_date;
    public String get_admin_id(){
        return admin_id;
    }
    public void set_admin_id(String admin_id){
        this.admin_id=admin_id;
    }
    public Integer get_in_warehouse_id(){
        return in_warehouse_id;
    }
    public void set_in_warehouse_id(int in_warehouse_id) {
        this.in_warehouse_id = in_warehouse_id;
    }
    public Integer get_good_id(){
        return good_id;
    }
    public void set_good_id(int good_id){
        this.good_id=good_id;
    }
    public Integer get_warehouse_id(){
        return warehouse_id;
    }
    public void set_warehouse_id(int warehouse_id){
        this.warehouse_id=warehouse_id;
    }
    public Integer get_in_warehouse_num(){
        return in_warehouse_num;
    }
    public void set_in_warehouse_num(int in_warehouse_num){
        this.in_warehouse_num=in_warehouse_num;
    }
    public String get_in_warehouse_date(){
        return in_warehouse_date;
    }
    public void set_in_warehouse_date(String in_warehouse_date){
        this.in_warehouse_date=in_warehouse_date;
    }
    public String get_good_name(){
        return name;
    }
    public void set_good_name(String name){
        this.name=name;
    }
}
