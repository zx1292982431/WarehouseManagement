package db;

public class out_warehouse {
    private Integer out_warehouse_id;
    private String admin_id;
    private Integer warehouse_id;
    private String name;
    private Integer good_id;
    private Integer out_warehouse_num;
    private String out_warehouse_date;
    public String get_admin_id(){
        return admin_id;
    }
    public void set_admin_id(String admin_id){
        this.admin_id=admin_id;
    }
    public Integer get_out_warehouse_id(){
        return out_warehouse_id;
    }
    public void set_out_warehouse_id(int out_warehouse_id) {
        this.out_warehouse_id = out_warehouse_id;
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
    public Integer get_out_warehouse_num(){
        return out_warehouse_num;
    }
    public void set_out_warehouse_num(int out_warehouse_num){
        this.out_warehouse_num=out_warehouse_num;
    }
    public String get_out_warehouse_date(){
        return out_warehouse_date;
    }
    public void set_out_warehouse_date(String out_warehouse_date){
        this.out_warehouse_date=out_warehouse_date;
    }
    public String get_good_name(){
        return name;
    }
    public void set_good_name(String name){
        this.name=name;
    }
}
