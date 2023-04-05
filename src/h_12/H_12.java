package h_12;

/**
 *
 * @author Tóth Milán
 */
public class H_12 {

    public static void main(String[] args) {
        if (Product.createProduct("Java pizza", "2490", "Java", "32 cm", Boolean.TRUE)) {
            System.out.println("Pizza Létrehozva");
        } else {
            System.out.println("Pizza nincs létrehozva");
        }
    }

}
