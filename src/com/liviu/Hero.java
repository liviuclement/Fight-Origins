package com.liviu;

import java.util.ArrayList;
import java.util.List;

public abstract class Hero implements Comparable<Hero> {
    int XP;
    int XPToGive;
    int pos;
    int level;
    int xpToNextLevel;
    Stats statPoints;
    Skills skillPoints;
    boolean toGiveOrNotToGive;
    boolean isAlive;
    String name;

    public abstract ArrayList<Skills> getList();

    public abstract int getXpToNextLevel();

    public abstract void setXpToNextLevel(int xpToNextLevel);


    public abstract String seePlayerStats();

    public abstract String seeEnemyStats();

    boolean isAlive() {
        if (statPoints.HP <= 0) {
            setAlive(false);
            return isAlive;
        }
        return isAlive;
    }

    void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    public abstract void levelUp();


    public abstract void attack(String s, Hero monster);

    public abstract void afisare(boolean bool);


    public abstract String getRandomAttack(Player player, Enemy enemy);


    @Override
    public int compareTo(Hero o) {
        return toString().compareTo(o.toString());
    }
}
