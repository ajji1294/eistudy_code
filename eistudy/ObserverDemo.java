import java.util.*;

interface Observer {
    void update(String weather);
}

class WeatherStation {
    List<Observer> observers = new ArrayList<>();
    void addObserver(Observer o) { observers.add(o); }
    void setWeather(String weather) {
        for (Observer o : observers) o.update(weather);
    }
}

class MobileApp implements Observer {
    String name;
    MobileApp(String name) { this.name = name; }
    public void update(String weather) {
        System.out.println(name + " got update: " + weather);
    }
}

public class ObserverDemo {
    public static void main(String[] args) {
        WeatherStation station = new WeatherStation();
        station.addObserver(new MobileApp("App1"));
        station.addObserver(new MobileApp("App2"));

        station.setWeather("Sunny");
        station.setWeather("Rainy");
    }
}
