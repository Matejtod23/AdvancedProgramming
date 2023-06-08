package vleznazad5;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class NonExistingItemException extends Exception{
    public NonExistingItemException(String message) {
        super(message);
    }
}

public class ArchiveStoreTest {
    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        Date date = new Date(113, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();
            Date dateToOpen = new Date(date.getTime() + (days * 24 * 60
                    * 60 * 1000));
            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
            store.archiveItem(lockedArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            int maxOpen = scanner.nextInt();
            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        while(scanner.hasNext()) {
            int open = scanner.nextInt();
            try {
                store.openItem(open, date);
            } catch(NonExistingItemException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }
}

abstract class Archive{
    int id;
    Date dateArchived;

    int counter = 0;

    public int getCounter() {
        return counter;
    }

    public void setCounter() {
        this.counter++;
    }

    public Archive(int id, Date dateArchived) {
        this.id = id;
        this.dateArchived = dateArchived;
    }

    public Archive(int id) {
        this.id = id;
    }
}

class LockedArchive extends Archive{

    Date dateToOpen;
    public LockedArchive(int id, Date dateToOpen) {
        super(id);
        this.dateToOpen = dateToOpen;
    }

    public Date getDateToOpen() {
        return dateToOpen;
    }

    public void setDateToOpen(Date dateToOpen) {
        this.dateToOpen = dateToOpen;
    }
}

class SpecialArchive extends Archive{

    int maxOpen;
    public SpecialArchive(int id, int maxOpen) {
        super(id);
        this.maxOpen = maxOpen;
    }
}
class ArchiveStore{

    List<Archive> archives;
    String message="";

    public ArchiveStore(List<Archive> archives) {
        this.archives = archives;
    }

    public ArchiveStore() {
        this.archives = new ArrayList<>();
    }

    public void archiveItem(Archive item, Date date) {
//        if (item.getClass().equals(LockedArchive.class)){
//           ((LockedArchive) item).dateToOpen = item.dateArchived;
//        }
        archives.add(item);
        int i = archives.indexOf(item);
        archives.get(i).dateArchived = date;
        message += String.format("Item %d archived at %s\n", item.id, item.dateArchived);
    }

    public void openItem(int id, Date date) throws NonExistingItemException {
        List<Archive> tmp = new ArrayList<>();
        archives.stream().forEach(archive -> {
            if (archive.id == id){
                tmp.add(archive);
                if (archive.getClass().equals(LockedArchive.class)){
                    if (date.after(((LockedArchive) archive).dateToOpen)) {
                        message += String.format("Item %d opened at %s\n", id, date);
                    } else {
                        message += String.format("Item %d cannot be opened before %s\n", id, ((LockedArchive) archive).dateToOpen);
                    }
                }else if (archive.getClass().equals(SpecialArchive.class)){
                    if (archive.counter == ((SpecialArchive) archive).maxOpen) {
                        message += String.format("Item %d cannot be opened more than %d times\n", id, ((SpecialArchive) archive).maxOpen);
                    } else {
                        message += String.format("Item %d opened at %s\n", id, date);
                        archive.setCounter();
                    }
                }
            }
        });

        if (tmp.isEmpty()){
            throw new NonExistingItemException(String.format("Item with id %d doesn't exist", id));
        }
    }

    public String getLog() {
        return message.replaceAll("GMT", "UTC");
    }
}
// вашиот код овде



