import java.util.*;
import java.util.stream.IntStream;

class SeatTakenException extends Exception{
    public SeatTakenException() {
    }
}

class SeatNotAllowedException extends Exception{
    public SeatNotAllowedException() {
    }
}
class Sector{
    String sectorName;
    TreeMap<Integer, Boolean> seats;
    int numSeats;
    int type;

    public Sector(String name, int numSeats) {
        this.sectorName = name;
        seats = new TreeMap<>();
        this.numSeats = numSeats;
        this.type = 0;
        fillInSeats();
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void fillInSeats(){
        IntStream.range(0, numSeats-1).forEach(i -> {
            seats.put(i, false);
        });
    }

    public String getSectorName() {
        return sectorName;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double calculate(){
        return 1 - ((float)numSeats / (seats.size()+1));
    }

    @Override
    public String toString() {
        return String.format("%s\t%d/%d\t%.1f%%", sectorName, numSeats, seats.size()+1, calculate() * 100);
    }
}

class Stadium {
    String nameOfStadium;
    HashMap<String, Sector> sectors;

    public Stadium(String nameOfStadium) {
        this.nameOfStadium = nameOfStadium;
        this.sectors = new HashMap<>();
    }

    public void createSectors(String[] sectorNames, int[] sectorSizes) {
        for (int i = 0; i < sectorNames.length; i++) {
            Sector s = new Sector(sectorNames[i], sectorSizes[i]);
            sectors.put(sectorNames[i], s);
        }
    }

    public boolean chekIfSeatTake(String name, int seat) {
        if (sectors.get(name).seats.get(seat-1) == false) {
            return false;
        } else {
            return true;
        }
    }


    public void buyTicket(String sectorName, int seat, int type) throws SeatTakenException, SeatNotAllowedException {
        if (seat == sectors.get(sectorName).seats.size()+1){
            seat-=1;
        }
        if (!chekIfSeatTake(sectorName, seat)) {
            if (type == 0) {
                sectors.get(sectorName).numSeats -= 1;
                sectors.get(sectorName).seats.computeIfPresent(seat-1, (k, v) -> v = true);
            } else {
                if (sectors.get(sectorName).type != 0 && type != sectors.get(sectorName).type) {
                    throw new SeatNotAllowedException();
                } else if (sectors.get(sectorName).type == 0) {
                    sectors.get(sectorName).numSeats -= 1;
                    sectors.get(sectorName).type = type;
                    sectors.get(sectorName).seats.computeIfPresent(seat - 1, (k, v) -> v = true);
                } else {
                    sectors.get(sectorName).numSeats -= 1;
                    sectors.get(sectorName).seats.computeIfPresent(seat - 1, (k, v) -> v = true);
                }
            }
        } else {
            throw new SeatTakenException();
        }
    }

    public void showSectors() {
        Comparator comparator = Comparator.comparing(Sector::getNumSeats).reversed().thenComparing(Sector::getSectorName);
        sectors.values().stream().sorted(comparator).forEach(System.out::println);
    }
}
public class StaduimTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] sectorNames = new String[n];
        int[] sectorSizes = new int[n];
        String name = scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            sectorNames[i] = parts[0];
            sectorSizes[i] = Integer.parseInt(parts[1]);
        }
        Stadium stadium = new Stadium(name);
        stadium.createSectors(sectorNames, sectorSizes);
        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            try {
                stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]));
            } catch (SeatNotAllowedException e) {
                System.out.println("SeatNotAllowedException");
            } catch (SeatTakenException e) {
                System.out.println("SeatTakenException");
            }
        }
        stadium.showSectors();
    }
}

