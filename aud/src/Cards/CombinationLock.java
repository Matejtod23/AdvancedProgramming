package Cards;

public class CombinationLock {
    private int combination;
    private boolean isOpen;

    private static int DEFAULT_COMBINATION = 100;
    private static int MIN_COMBINATION = 100;
    private static int MAX_COMBINATION = 999;

    public CombinationLock(int combination) {
        if(isValid(combination))
            this.combination = combination;
        else
        this.isOpen = false;
    }

    private boolean isValid(int combination){
        return combination >= 100 && combination <= 999;
    }

    public boolean open(int combination){
        this.isOpen = (this.combination == combination);
        return this.isOpen;
    }

    public void lock(){
        this.isOpen = false;
    }

    public  boolean changeCombinaiton(int oldCombination,int newCombination){
        if(open(oldCombination) && isValid(newCombination)){
            this.combination = newCombination;
            this.lock();
            return true;
        }
            return false;
    }

    public static void main(String[] args) {
        CombinationLock validLock = new CombinationLock(234);

        System.out.println("Testing isOpen......");
        System.out.println(validLock.isOpen);
        System.out.println("Testing open......");
        System.out.println(validLock.open(234));
        System.out.println(validLock.open(999));
        System.out.println(validLock.open(123));

        System.out.println("Testing lock()......");
        validLock.lock();
        System.out.println(validLock.isOpen);

        System.out.println("Testing changeCombination......");
        System.out.println(validLock.changeCombinaiton(909, 999));
        System.out.println(validLock.changeCombinaiton(234, 911199));
        System.out.println(validLock.changeCombinaiton(100, 111));
        System.out.println(validLock.changeCombinaiton(111, 777));

        CombinationLock invalidLock = new CombinationLock(123141);
        System.out.println("Testing isOpen......");
        System.out.println(invalidLock.isOpen);

        System.out.println("Testing open......");
        System.out.println(invalidLock.open(123141));
        System.out.println(invalidLock.open(222));
        System.out.println(invalidLock.open(100));

        System.out.println("Testing lock()......");
        invalidLock.lock();
        System.out.println(invalidLock.isOpen);

        System.out.println("Testing changeCombination......");
        System.out.println(invalidLock.changeCombinaiton(123456, 111));
        System.out.println(invalidLock.changeCombinaiton(868, 111));
        System.out.println(invalidLock.changeCombinaiton(234, 343));
    }
}