```java
package behavioral.observer;

public class ConcreteInvestor implements Investor {
    private String name;

    public ConcreteInvestor(String name) {
        this.name = name;
    }

    @Override
    public void update(ConcreteStock stock) {
        System.out.println("Investor " + name + " notified: " + stock.getName() +
                " price changed to " + stock.getPrice());
    }
}
```
