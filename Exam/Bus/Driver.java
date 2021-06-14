package Bus;

import java.io.Serializable;
import java.time.LocalDate;

public class Driver implements Serializable {
    private Long id;
    private String surname;
    private String name;


    public Driver() {
        this.id = id;
        this.surname = surname;
        this.name = name;

    }

    public Driver(int number, int route, String model, int mileage, LocalDate startUsing) {

    }

    public Driver(long id, String username, String name) {
        this.id = this.id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
