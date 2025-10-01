```java
package demo;

import behavioral.observer.*;
import behavioral.strategy.*;
import creational.singleton.*;
import creational.factory.*;
import structural.adapter.*;
import structural.decorator.*;

public class DesignPatternsDemo {
    public static void main(String[] args) {

        // BEHAVIORAL - OBSERVER
        System.out.println("\n=== Observer Pattern ===");
        ConcreteStock stock = new ConcreteStock("AAPL", 150);
        Investor inv1 = new ConcreteInvestor("Alice");
        Investor inv2 = new ConcreteInvestor("Bob");
        stock.register(inv1);
        stock.register(inv2);
        stock.setPrice(155);

        // BEHAVIORAL - STRATEGY
        System.out.println("\n=== Strategy Pattern ===");
        ShoppingCart cart = new ShoppingCart();
        cart.setPaymentStrategy(new CreditCardPayment());
        cart.checkout(1000);
        cart.setPaymentStrategy(new UpiPayment());
        cart.checkout(500);

        // CREATIONAL - SINGLETON
        System.out.println("\n=== Singleton Pattern ===");
        Logger logger = Logger.getInstance();
        logger.log("Application started");

        // CREATIONAL - FACTORY
        System.out.println("\n=== Factory Pattern ===");
        Shape shape1 = ShapeFactory.getShape("circle");
        shape1.draw();
        Shape shape2 = ShapeFactory.getShape("rectangle");
        shape2.draw();

        // STRUCTURAL - ADAPTER
        System.out.println("\n=== Adapter Pattern ===");
        MediaPlayer mp3 = new MP3Player();
        mp3.play("song.mp3");
        MediaPlayer adapter = new MediaAdapter();
        adapter.play("video.mp4");

        // STRUCTURAL - DECORATOR
        System.out.println("\n=== Decorator Pattern ===");
        Coffee coffee = new SimpleCoffee();
        coffee = new MilkDecorator(coffee);
        coffee = new SugarDecorator(coffee);
        System.out.println(coffee.getDescription() + " costs $" + coffee.getCost());
    }
}
```
