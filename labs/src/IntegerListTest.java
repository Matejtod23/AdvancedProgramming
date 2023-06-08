import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

class ArrayIndexOutOfBoundsException extends Exception{
    public ArrayIndexOutOfBoundsException() {
    }
}

class IntegerList{
    LinkedList<Integer> nums;

    public IntegerList() {
        nums = new LinkedList<>();
    }

    public IntegerList(Integer[] a){
        nums = new LinkedList<>();
        for (Integer num: a){
            nums.addLast(num);
        }
    }

    public void add(int el, int idx) {
        if (idx < nums.size()){
            nums.add(idx, el);
        }else {
            for (int i = nums.size(); i < idx; i++){
                nums.addLast(0);
            }
            nums.addLast(el);
        }
    }

    public int remove(int idx) throws ArrayIndexOutOfBoundsException {
        if (idx < 0 || idx > nums.size()){
            throw new ArrayIndexOutOfBoundsException();
        }
        int toRemove = nums.get(idx);
        nums.remove(idx);
        return toRemove;
    }

    public int size() {
        return nums.size();
    }

    public int get(int idx) {
        return nums.get(idx);
    }

    public void set(int el, int idx) throws ArrayIndexOutOfBoundsException {
        if (idx < 0 || idx > nums.size()){
            throw new ArrayIndexOutOfBoundsException();
        }
        nums.set(idx, el);
    }

    public int count(int el) {
        return (int) nums.stream().filter(i -> i==el).count();
    }

    public void removeDuplicates() {
        for (int i = 0; i < nums.size(); i++) {
            //System.out.println(arrayList.toString());
            Integer integer = nums.get(i);
            for (int j = 0; j < i; j++) {
                if (integer.equals(nums.get(j))) {
                    nums.remove(j);
                    i--;
                    break;
                }
            }
        }
    }

    public IntegerList addValue(int k) {
        IntegerList o = new IntegerList();
        nums.stream().forEach(i->{
            o.nums.addLast(i+k);
        });

        return o;
    }

    public void shiftLeft(int idx, int mesta) throws ArrayIndexOutOfBoundsException {
        if (idx < 0 || idx > nums.size()){
            throw new ArrayIndexOutOfBoundsException();
        }
        int shiftIndex = (idx - mesta) % nums.size();
        if (shiftIndex < 0) {
            shiftIndex = nums.size() + shiftIndex;
        }
        Integer element = nums.remove(idx);
        nums.add(shiftIndex, element);
    }

    public void shiftRight(int idx, int mesta) throws ArrayIndexOutOfBoundsException {
        if (idx < 0 || idx > nums.size()){
            throw new ArrayIndexOutOfBoundsException();
        }
        int shiftIndex = (idx + mesta) % nums.size();
        Integer element = nums.remove(idx);
        nums.add(shiftIndex, element);
    }

    public int sumFirst(int k) {
        return nums.stream().limit(k).mapToInt(i -> i).sum();
    }

    public int sumLast(int k) {
        int last = nums.size() - k;
        return nums.stream().skip(last).mapToInt(i->i).sum();
    }
}

public class IntegerListTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if ( k == 0 ) { //test standard methods
            int subtest = jin.nextInt();
            if ( subtest == 0 ) {
                IntegerList list = new IntegerList();
                while ( true ) {
                    int num = jin.nextInt();
                    if ( num == 0 ) {
                        list.add(jin.nextInt(), jin.nextInt());
                    }
                    if ( num == 1 ) {
                        try {
                            list.remove(jin.nextInt());
                        } catch (ArrayIndexOutOfBoundsException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if ( num == 2 ) {
                        print(list);
                    }
                    if ( num == 3 ) {
                        break;
                    }
                }
            }
            if ( subtest == 1 ) {
                int n = jin.nextInt();
                Integer a[] = new Integer[n];
                for ( int i = 0 ; i < n ; ++i ) {
                    a[i] = jin.nextInt();
                }
                IntegerList list = new IntegerList(a);
                print(list);
            }
        }
        if ( k == 1 ) { //test count,remove duplicates, addValue
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for ( int i = 0 ; i < n ; ++i ) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while ( true ) {
                int num = jin.nextInt();
                if ( num == 0 ) { //count
                    System.out.println(list.count(jin.nextInt()));
                }
                if ( num == 1 ) {
                    list.removeDuplicates();
                }
                if ( num == 2 ) {
                    print(list.addValue(jin.nextInt()));
                }
                if ( num == 3 ) {
                    list.add(jin.nextInt(), jin.nextInt());
                }
                if ( num == 4 ) {
                    print(list);
                }
                if ( num == 5 ) {
                    break;
                }
            }
        }
        if ( k == 2 ) { //test shiftRight, shiftLeft, sumFirst , sumLast
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for ( int i = 0 ; i < n ; ++i ) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while ( true ) {
                int num = jin.nextInt();
                if ( num == 0 ) { //count
                    try {
                        list.shiftLeft(jin.nextInt(), jin.nextInt());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new RuntimeException(e);
                    }
                }
                if ( num == 1 ) {
                    try {
                        list.shiftRight(jin.nextInt(), jin.nextInt());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new RuntimeException(e);
                    }
                }
                if ( num == 2 ) {
                    System.out.println(list.sumFirst(jin.nextInt()));
                }
                if ( num == 3 ) {
                    System.out.println(list.sumLast(jin.nextInt()));
                }
                if ( num == 4 ) {
                    print(list);
                }
                if ( num == 5 ) {
                    break;
                }
            }
        }
    }

    public static void print(IntegerList il) {
        if ( il.size() == 0 ) System.out.print("EMPTY");
        for ( int i = 0 ; i < il.size() ; ++i ) {
            if ( i > 0 ) System.out.print(" ");
            System.out.print(il.get(i));
        }
        System.out.println();
    }

}
