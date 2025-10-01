interface Coffee {
    String getDescription();
    double getCost();
}
class SimpleCoffee implements Coffee {
    public String getDescription() { return "Simple Coffee"; }
    public double getCost() { return 2.0; }
}
class MilkDecorator implements Coffee {
    private Coffee coffee;
    public MilkDecorator(Coffee c) { this.coffee = c; }
    public String getDescription() { return coffee.getDescription() + ", Milk"; }
    public double getCost() { return coffee.getCost() + 0.5; }
}
class SugarDecorator implements Coffee {
    private Coffee coffee;
    public SugarDecorator(Coffee c) { this.coffee = c; }
    public String getDescription() { return coffee.getDescription() + ", Sugar"; }
    public double getCost() { return coffee.getCost() + 0.3; }
}
public class MainDecorator {
    public static void main(String[] args) {
        Coffee c = new SimpleCoffee();
        System.out.println(c.getDescription() + ": $" + c.getCost());
        c = new MilkDecorator(c);
        c = new SugarDecorator(c);
        System.out.println(c.getDescription() + ": $" + c.getCost());
    }
}
