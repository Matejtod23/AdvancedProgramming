package kolok2.zad1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface Messurement{
    void updateMessurment(float temperature, float humidity, float pressure);
}

class WeatherDispatcher{
    List<Messurement> messurements;

    public WeatherDispatcher() {
        messurements = new ArrayList<>();
    }
    
    public void setMeasurements(float t, float h, float p) {
        for (Messurement m: messurements){
            m.updateMessurment(t,h,p);
        }
    }

    public void register(Messurement m){
        messurements.add(m);
    }

    public void remove(Messurement m) {
        messurements.remove(m);
    }
}

class CurrentConditionsDisplay implements Messurement{

    public CurrentConditionsDisplay(WeatherDispatcher weatherDispatcher) {
        weatherDispatcher.register(this);
    }

    @Override
    public void updateMessurment(float temperature, float humidity, float pressure) {
        System.out.printf("Temperature: %.1fF\nHumidity: %.1f%%\n",temperature, humidity);
    }
}

class ForecastDisplay implements Messurement{
    float prevPressure = 0.0F;
    public ForecastDisplay(WeatherDispatcher weatherDispatcher){
        weatherDispatcher.register(this);
    }
    @Override
    public void updateMessurment(float temperature, float humidity, float pressure) {
        if(pressure > prevPressure){
            System.out.println("Forecast: Improving\n");
        } else if (pressure == prevPressure) {
            System.out.println("Forecast: Same\n");
        }else {
            System.out.println("Forecast: Cooler\n");
        }
        prevPressure = pressure;
    }
}

public class WeatherApplication {

    public static void main(String[] args) {
        WeatherDispatcher weatherDispatcher = new WeatherDispatcher();

        CurrentConditionsDisplay currentConditions = new CurrentConditionsDisplay(weatherDispatcher);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherDispatcher);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            weatherDispatcher.setMeasurements(Float.parseFloat(parts[0]), Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
            if(parts.length > 3) {
                int operation = Integer.parseInt(parts[3]);
                if(operation==1) {
                    weatherDispatcher.remove(forecastDisplay);
                }
                if(operation==2) {
                    weatherDispatcher.remove(currentConditions);
                }
                if(operation==3) {
                    weatherDispatcher.register(forecastDisplay);
                }
                if(operation==4) {
                    weatherDispatcher.register(currentConditions);
                }

            }
        }
    }
}
