```java
package behavioral.observer;

import java.util.ArrayList;
import java.util.List;

public class ConcreteStock implements Stock {
    private String name;
    private double price;
    private List<Investor> investors = new ArrayList<>();

    public ConcreteStock(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public void setPrice(double price) {
        this.price = price;
        notifyInvestors();
    }

    public double getPrice() { return price; }
    public String getName() { return name; }

    @Override
    public void register(Investor investor) {
        investors.add(investor);
    }

    @Override
    public void unregister(Investor investor) {
        investors.remove(investor);
    }

    @Override
    public void notifyInvestors() {
        for (Investor investor : investors) {
            investor.update(this);
        }
    }
}
```
