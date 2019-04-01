package models;

public abstract class Skill {
    public int level = 1;

    public abstract boolean use(int x, int y);
}
