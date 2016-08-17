package bd.table;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Александр on 13.08.2016.
 * класс дело
 */
@Entity
@Table(name = "job")
public class Job {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id; // номер клиента в бд

    @Column(name = "name")
    private String name; // название дела

    @Column(name = "made")
    private boolean made; // true если сделано

    @Column(name = "time")
    private String time; // срок выполнения

    public Job() {
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

    public boolean isMade() {
        return made;
    }

    public void setMade(boolean made) {
        this.made = made;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
