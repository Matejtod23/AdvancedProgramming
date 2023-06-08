package aud3.zad2;

public class Alien {
    public static final int SNAKE_ALIEN = 0;
    public static final int ORGE_ALIEN = 1;
    public static final int MARSHMALLOW_MAN_ALIEN = 2;
    public int type;
    public int health;
    public String name;

    public Alien(int type, int health, String name){
        this.type = type;
        this.health = health;
        this.name = name;
    }
}
