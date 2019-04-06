package models;

public abstract class Skill {
    public int level = 1;
    public int cooldown = 0;
    public int turnsSinceUsed = 0;

    public abstract boolean canUse(int x, int y);
    public abstract void use(int x, int y);
}
