package bd.table;

import javax.persistence.*;
import java.util.Date;

/**
 * Клиент
 */
@Entity
@Table(name = "user")
public class User
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id; // номер клиента в бд

    @Column(name = "name")
    private String name; // имя клиента

    @Column(name = "age")
    private int age; // возраст

    @Column(name = "isAdmin")
    private boolean admin; // админ права

    @Column(name = "createdDate")
    private Date date; // время создания в бд

    public User() {
    }

    public User(String name, int age, boolean admin) {
        this.name = name;
        this.age = age;
        this.admin = admin;
        date = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", admin=" + admin +
                ", date=" + date +
                '}';
    }
}
