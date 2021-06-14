package Bus;

import java.io.Serializable;
import java.time.LocalDate;

public class Bus implements Serializable {
    private int number;
    private int route;
    private String model;
    private int mileage;
    private LocalDate startUsing;

    public Bus(int number, int route, String model, int mileage, LocalDate startUsing) {
        this.number = number;
        this.route = route;
        this.model = model;
        this.mileage = mileage;
        this.startUsing = startUsing;
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public LocalDate getStartUsing() {
        return startUsing;
    }

    public void setStartUsing(LocalDate startUsing) {
        this.startUsing = startUsing;
    }
}
