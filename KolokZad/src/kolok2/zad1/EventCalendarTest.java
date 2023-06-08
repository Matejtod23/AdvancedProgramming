package kolok2.zad1;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class WrongDateException extends Exception{
    public WrongDateException(String date) {
        super(String.format("Wrong date: %s", date));
    }
}
class Event implements Comparable<Event>{
    String name;
    String location;
    Date date;

    public Event(String name, String location, Date date) {
        this.name = name;
        this.location = location;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int compareTo(Event o) {
        if (date.compareTo(o.date) == 0){
            return name.compareTo(o.name);
        }
        else {
            return date.compareTo(o.date);
        }
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyy HH:mm");
        String dateT = formatter.format(date);
        return String.format("%s at %s, %s",dateT, location, name);
    }
}

class EventCalendar {
    int year;
    TreeSet<Event> events;

    public EventCalendar(int year) {
        events = new TreeSet<>();
        this.year = year;
    }


    public void addEvent(String name, String location, Date date) throws WrongDateException {
        if ((date.getYear() + 1900) == year) {
            Event e = new Event(name, location, date);
            events.add(e);
        } else {
            String dateToPrint = String.valueOf(date);
            dateToPrint = dateToPrint.replace("GMT", "UTC").replace("CET", "UTC");
            throw new WrongDateException(dateToPrint);
        }
    }

    public void listEvents(Date dateE) {
        List<Event> e = events.stream().filter(event -> {
            if (event.date.getYear() + 1900 == dateE.getYear() + 1900
                    && event.date.getMonth() + 1 == dateE.getMonth() + 1
                    && event.date.getDay() + 15 == dateE.getDay() + 15) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        if (e.size() == 0){
            System.out.println("No events on this day!");
        }else {
            e.stream().forEach(System.out::println);
        }
    }

    public void listByMonth() {
        IntStream.range(1, 13).forEach(i -> {
            int numE = events.stream().filter(event -> event.date.getMonth() + 1 == i).collect(Collectors.toList()).size();
            System.out.println(i + " : " + numE);
        });

    }
}

public class EventCalendarTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int year = scanner.nextInt();
        scanner.nextLine();
        EventCalendar eventCalendar = new EventCalendar(year);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            String name = parts[0];
            String location = parts[1];
            Date date = df.parse(parts[2]);
            try {
                eventCalendar.addEvent(name, location, date);
            } catch (WrongDateException e) {
                System.out.println(e.getMessage());
            }
        }
        Date date = df.parse(scanner.nextLine());
        eventCalendar.listEvents(date);
        eventCalendar.listByMonth();
    }
}

// vashiot kod ovde
