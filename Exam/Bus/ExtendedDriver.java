package Bus;

import java.io.Serializable;

public class ExtendedDriver implements Serializable {
    private Driver driver;
    private Integer countBus;
    private Integer lifetime;

    public ExtendedDriver(Driver driver, Integer countBus, Integer countSentLetters) {
        this.driver = driver;
        this.countBus = countBus;
        this.lifetime = lifetime;
    }

    public static Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Integer getCountBus() {
        return countBus;
    }

    public void setCountBus(Integer countBus) {
        this.countBus = countBus;
    }

    public Integer getLifetime() {
        return lifetime;
    }

    public void setLifetime(Integer lifetime) {
        this.lifetime = lifetime;
    }

    @Override
    public String toString() {
        return "ExtendedDriver{" +
                "driver=" + driver +
                ", countBus=" + countBus +
                ", lifetime=" + lifetime +
                '}';
    }
}
