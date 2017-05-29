package kr.ac.hansung.whefe.model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class Order {
    private String menu_name;
    private String customer_name;
    private String option_info;
    private String menu_size;
    private String hotIceNone;
    private int menu_quantity;
    private boolean menu_completed;
}
