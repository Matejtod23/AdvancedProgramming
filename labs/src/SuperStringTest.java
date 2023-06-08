import java.util.*;
import java.util.stream.Collectors;

class SuperString{
    LinkedList<String> list;
    Stack<String> lastAdded;
    SuperString(){
        list = new LinkedList<>();
        lastAdded = new Stack<>();
    }

    public void append(String s) {
        list.addLast(s);
        lastAdded.push(s);
    }
    public void insert(String s){
        list.addFirst(s);
        lastAdded.push(s);
    }
    public boolean contains(String s){
        return this.toString().contains(s);
    }
    public void reverse(){
        LinkedList<String> reversed = new LinkedList<>();
        String str = "";
        while (!list.isEmpty()){
            str = list.getLast();
            str = reverseString(str);
            reversed.addLast(str);
            list.removeLast();
        }
        for (String s: reversed){
            list.addLast(s);
        }
    }

    public String reverseString(String s){
        StringBuilder sb = new StringBuilder(s);
        return sb.reverse().toString();
    }

    public void removeLast(int k) {
        while (k > 0){
            String toRemove = lastAdded.pop();
            list.remove(toRemove);
            String toRemoveReversed = reverseString(toRemove);
            list.remove(toRemoveReversed);
            k--;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
        }
        return sb.toString();
    }
}

public class SuperStringTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (  k == 0 ) {
            SuperString s = new SuperString();
            while ( true ) {
                int command = jin.nextInt();
                if ( command == 0 ) {//append(String s)
                    s.append(jin.next());
                }
                if ( command == 1 ) {//insert(String s)
                    s.insert(jin.next());
                }
                if ( command == 2 ) {//contains(String s)
                    System.out.println(s.contains(jin.next()));
                }
                if ( command == 3 ) {//reverse()
                    s.reverse();
                }
                if ( command == 4 ) {//toString()
                    System.out.println(s);
                }
                if ( command == 5 ) {//removeLast(int k)
                    s.removeLast(jin.nextInt());
                }
                if ( command == 6 ) {//end
                    break;
                }
            }
        }
    }

}
