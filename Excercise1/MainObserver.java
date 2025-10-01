import java.util.*;

interface Observer { void update(int temp); }
class CurrentConditionsDisplay implements Observer {
    public void update(int temp) { System.out.println("Current temp: " + temp); }
}
class ForecastDisplay implements Observer {
    public void update(int temp) { System.out.println("Forecast temp: " + (temp + 2)); }
}
class WeatherStation {
    private List<Observer> observers = new ArrayList<>();
    private int temperature;
    public void addObserver(Observer o) { observers.add(o); }
    public void setTemperature(int t) {
        this.temperature = t;
        observers.forEach(o -> o.update(t));
    }
}
public class MainObserver {
    public static void main(String[] args) {
        WeatherStation ws = new WeatherStation();
        ws.addObserver(new CurrentConditionsDisplay());
        ws.addObserver(new ForecastDisplay());
        ws.setTemperature(26);
        ws.setTemperature(31);
    }
}
