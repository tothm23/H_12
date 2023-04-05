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
import org.eclipse.persistence.queries.StoredProcedureCall;

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
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "h_12.Product[ id=" + id + " ]";
    }

    // Saját függvények
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
            //ex.printStackTrace();
            System.out.println(ex.getMessage());
            return false;
        } finally {
            // Adatbázis kapcsolat lezárása biztonsági okokból
            em.clear();
            em.close();
            emf.close();
        }
    }

}
