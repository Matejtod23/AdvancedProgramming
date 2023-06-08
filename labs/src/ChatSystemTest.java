import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.TreeSet;

class NoSuchRoomException extends Exception{
    public NoSuchRoomException(String roomName) {
        super("No such room " + roomName);
    }
}

class NoSuchUserException extends Exception{
    public NoSuchUserException(String userName) {
        super("No such user " + userName);
    }
}

class ChatRoom{
    String name;
    Set<String> users;

    public ChatRoom(String name) {
        this.name = name;
        users = new TreeSet<>();
    }

    public void addUser(String name){
        users.add(name);
    }

    public void removeUser(String name){
        users.remove(name);
    }

    public boolean hasUser(String username){
        return users.contains(username);
    }

    public int numUsers(){
        return users.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name+"\n");
        if (users.isEmpty()){
            sb.append("EMPTY\n");
        }else {
            users.forEach(u->{
                sb.append(u+"\n");
            });
        }

        return sb.toString();
    }
}

class ChatSystem{
    TreeMap<String, ChatRoom> listOfRooms;

    public ChatSystem() {
        listOfRooms = new TreeMap<>();
    }
    public void addRoom(String roomName){
        listOfRooms.put(roomName, new ChatRoom(roomName));
    }
    public void removeRoom(String roomName){
        listOfRooms.remove(roomName);
    }
    public ChatRoom getRoom(String name) throws NoSuchRoomException {
        if (listOfRooms.containsKey(name)){
            return listOfRooms.get(name);
        }else {
            throw new NoSuchRoomException(name);
        }
    }

    public void register(String userName){
        Comparator comparator = Comparator.comparing(ChatRoom::numUsers);
        if (!listOfRooms.isEmpty()){
            ChatRoom room = (ChatRoom) listOfRooms.values().stream().sorted(comparator).findFirst().get();
            room.users.add(userName);
        }
    }
    public void registerAndJoin(String userName, String roomName){
        listOfRooms.get(roomName).users.add(userName);
        //register(userName);
    }
    public void joinRoom(String userName, String roomName) throws NoSuchRoomException {
        if (listOfRooms.containsKey(roomName)){
            listOfRooms.get(roomName).users.add(userName);
        }else {
            throw new NoSuchRoomException(roomName);
        }
    }
    public void leaveRoom(String userName, String roomName) throws NoSuchRoomException{
        if (listOfRooms.containsKey(roomName)){
            if (listOfRooms.get(roomName).users.contains(userName)){
                listOfRooms.get(roomName).users.remove(userName);
            }else {
                try {
                    throw new NoSuchUserException(userName);
                } catch (NoSuchUserException e) {
                    return;
                }
            }
        }else {
            throw new NoSuchRoomException(roomName);
        }
    }

    public void followFriend(String username, String firend_name){
        listOfRooms.values().forEach(room -> {
            if (room.users.contains(firend_name)){
                room.users.add(username);
            }else {
                try {
                    throw new NoSuchUserException(firend_name);
                } catch (NoSuchUserException e) {
                    return;
                }
            }
        });
    }
}

public class ChatSystemTest {

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchRoomException {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if ( k == 0 ) {
            ChatRoom cr = new ChatRoom(jin.next());
            int n = jin.nextInt();
            for ( int i = 0 ; i < n ; ++i ) {
                k = jin.nextInt();
                if ( k == 0 ) cr.addUser(jin.next());
                if ( k == 1 ) cr.removeUser(jin.next());
                if ( k == 2 ) System.out.println(cr.hasUser(jin.next()));
            }
            System.out.println("");
            System.out.println(cr.toString());
            n = jin.nextInt();
            if ( n == 0 ) return;
            ChatRoom cr2 = new ChatRoom(jin.next());
            for ( int i = 0 ; i < n ; ++i ) {
                k = jin.nextInt();
                if ( k == 0 ) cr2.addUser(jin.next());
                if ( k == 1 ) cr2.removeUser(jin.next());
                if ( k == 2 ) cr2.hasUser(jin.next());
            }
            System.out.println(cr2.toString());
        }
        if ( k == 1 ) {
            ChatSystem cs = new ChatSystem();
            Method mts[] = cs.getClass().getMethods();
            while ( true ) {
                String cmd = jin.next();
                if ( cmd.equals("stop") ) break;
                if ( cmd.equals("print") ) {
                    System.out.println(cs.getRoom(jin.next())+"\n");continue;
                }
                for ( Method m : mts ) {
                    if ( m.getName().equals(cmd) ) {
                        String params[] = new String[m.getParameterTypes().length];
                        for ( int i = 0 ; i < params.length ; ++i ) params[i] = jin.next();
                        m.invoke(cs,params);
                    }
                }
            }
        }
    }

}
