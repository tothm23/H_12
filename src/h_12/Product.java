package h_12;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Table;

/**
 *
 * @author Tóth Milán
 */
@Entity
@Table(name = "product")
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id = :id"),
    @NamedQuery(name = "Product.findByName", query = "SELECT p FROM Product p WHERE p.name = :name"),
    @NamedQuery(name = "Product.findByPrice", query = "SELECT p FROM Product p WHERE p.price = :price"),
    @NamedQuery(name = "Product.findByCategory", query = "SELECT p FROM Product p WHERE p.category = :category")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @Column(name = "Price")
    private int price;
    @Basic(optional = false)
    @Column(name = "Category")
    private String category;

    public Product() {
    }

    public Product(Integer id) {
        this.id = id;
    }

    public Product(Integer id, String name, int price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        } else {
        }
        Product other = (Product) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "h_12.Product[ id=" + id + " ]";
    }

    // Saját függvények
    /**
     * CREATE
     *
     * @param name
     * @param price
     * @param category
     * @param size
     * @param isPizza
     * @return
     */
    public static boolean createProduct(String name, String price, String category, String size, Boolean isPizza) {

        // Adatbázis kapcsolat pélfányosítása
        // Kerest a persistence.xml-t
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("H_12PU");
        EntityManager em = emf.createEntityManager();

        try {
            // Tárolt eljárás példányosítás
            // Átadjuk a tárolt eljárás nevét
            StoredProcedureQuery spq = em.createStoredProcedureQuery("createProduct");

            // Paraméterek regisztrációja
            spq.registerStoredProcedureParameter("productName", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("productPrice", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("productCategory", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("sizeOfType", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("isPizza", Boolean.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("result", String.class, ParameterMode.OUT);

            // Paraméter értékek megadása
            spq.setParameter("productName", name);
            spq.setParameter("productPrice", price);
            spq.setParameter("productCategory", category);
            spq.setParameter("sizeOfType", size);
            spq.setParameter("isPizza", isPizza);

            // Az OUT paramétert regisztráljuk, de nem adunk értéket
            // spq.setParameter("result", "");
            // Eljárás meghívása
            spq.execute();
            return true;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        } finally {
            // Adatbázis kapcsolat lezárása biztonsági okokból
            em.clear();
            em.close();
            emf.close();
        }
    }

    /**
     * READ
     *
     * @return
     */
    public static boolean selectWhereOrder() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("H_12PU");
        EntityManager em = emf.createEntityManager();

        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("selectWhereOrder");

            spq.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            em.clear();
            em.close();
            emf.close();
        }
    }

    /**
     * UPDATE
     *
     * @param id
     * @param name
     * @param price
     * @param category
     * @return
     */
    public static boolean updateProductByID(String name, Integer price, String category, Integer id) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("H_12PU");
        EntityManager em = emf.createEntityManager();

        try {
            StoredProcedureQuery spq = em.createStoredProcedureQuery("updateProductByID");

            spq.registerStoredProcedureParameter("name", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("price", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("category", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("id", Integer.class, ParameterMode.IN);

            spq.setParameter("name", name);
            spq.setParameter("price", price);
            spq.setParameter("category", category);
            spq.setParameter("id", id);

            spq.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            em.clear();
            em.close();
            emf.close();
        }
    }

    /**
     * DELETE
     *
     * @param id
     * @return
     */
    public static boolean deleteProductByID(Integer id) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("H_12PU");
        EntityManager em = emf.createEntityManager();

        try {

            StoredProcedureQuery spq = em.createStoredProcedureQuery("deleteProductByID");

            spq.registerStoredProcedureParameter("id", Integer.class, ParameterMode.IN);
            spq.setParameter("id", id);

            spq.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            em.clear();
            em.close();
            emf.close();
        }
    }

}
