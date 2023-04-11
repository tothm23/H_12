package h_12;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tóth Milán
 */
public class H_12 {

    private static final Logger LOGGER = Logger.getLogger(H_12.class.getName());

    public static void main(String[] args) {

        if (Product.createProduct("Java pizza", "2490", "Java", "32 cm", Boolean.TRUE)) {
            LOGGER.log(Level.INFO, "Pizza Létrehozva");
        } else {
            LOGGER.log(Level.WARNING, "Pizza nincs létrehozva");
        }

        if (Product.selectWhereOrder()) {
            LOGGER.log(Level.INFO, "Pizzák lekérdezve");
        } else {
            LOGGER.log(Level.WARNING, "Pizzák nincsenek lekérdezve");
        }

        if (Product.updateProductByID("Java 45cm", 2000, "Pizza", 10)) {
            LOGGER.log(Level.INFO, "Pizza frissítve");
        } else {
            LOGGER.log(Level.WARNING, "Pizza nincs frissítve");
        }

        if (Product.deleteProductByID(11)) {
            LOGGER.log(Level.INFO, "Pizza törölve");
        } else {
            LOGGER.log(Level.WARNING, "Pizza nincs törölve");
        }
    }

}
