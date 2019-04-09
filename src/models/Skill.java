package models;

public abstract class Skill {
    public static int level = 1;
    public static int cooldown = 0;
    public static int turnsSinceUsed = 0;

    public abstract boolean canUse(int x, int y);
    public abstract void use(int x, int y);
}
