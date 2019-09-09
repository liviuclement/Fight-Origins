package com.liviu;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Fighter extends Hero implements Comparable<Hero> {

    private Skills swordHit = new Skills("sword hit", 300, 0, 1, 0, 1, 0);
    private Skills jumpHit = new Skills("jump hit", 500, 0, 1, 0, 5, 0);
    private Skills savageSlash = new Skills("savage slash", 700, 0, 1, 0, 10, 0);

    private ArrayList<Skills> list = new ArrayList<>();

    Item weakHelmet = new Item(Item.Slot.HEAD, 150, 0, 0, 0);
    Item strongHelmet = new Item(Item.Slot.HEAD, 450, 0, 0, 0);
    Item woodSword = new Item(Item.Slot.HANDS, 0, 100, 0, 0);
    Item ironSword = new Item(Item.Slot.HANDS, 0, 400, 0, 0);
    Item weakChestArmor = new Item(Item.Slot.CHEST, 100, 0, 0, 0);
    Item strongChestArmor = new Item(Item.Slot.CHEST, 300, 0, 0, 0);
    Item weakLegsArmor = new Item(Item.Slot.LEGS, 80, 0, 0, 0);
    Item strongLegsArmor = new Item(Item.Slot.LEGS, 200, 0, 0, 0);
    Item silverShoes = new Item(Item.Slot.FEET, 30, 0, 0, 0);
    Item goldShoes = new Item(Item.Slot.FEET, 75, 0, 0, 0);


    Fighter(int pos, int level, int XP) {

        name = "Fighter";
        super.isAlive = true;
        super.level = 1;
        super.xpToNextLevel = 800 + (level - 1) * 1200;
        super.XP = XP;
        super.XPToGive = 800;
        super.toGiveOrNotToGive = true;
        Random r = new Random();
        super.statPoints = new Stats(1000, 0, r.nextInt(11), 30, 80);
        this.pos = pos;
        list.add(swordHit);
        list.add(jumpHit);
        list.add(savageSlash);

        for (int i = 1; i < level; i++) {
            super.level++;
            statPoints.HP += 200;
            statPoints.dodgeChance += 3;
            statPoints.hitChance += 3;
            super.XPToGive += 800;
            if (super.level % 3 == 0) {
                for (int j = 0; j < list.size(); j++) {
                    list.get(j).meleDamage += 200;
                    //skillPoints.meleDamage += 100;
                }

            }
        }
    }

    Fighter(int pos, int level, int HP, int XP) {

        name = "Fighter";
        super.isAlive = true;
        super.level = 1;
        super.xpToNextLevel = 800;
        super.XP = XP;
        super.XPToGive = 800;
        super.toGiveOrNotToGive = true;
        Random r = new Random();
        super.statPoints = new Stats(1000, 0, r.nextInt(11), 30, 80);
        this.pos = pos;
        list.add(swordHit);
        list.add(jumpHit);
        list.add(savageSlash);
        statPoints.HP = HP;
        for (int i = 1; i < level; i++) {
            super.level++;
            statPoints.dodgeChance += 3;
            statPoints.hitChance += 3;
            super.XPToGive += 800;
            if (super.level % 3 == 0) {
                for (int j = 0; j < list.size(); j++) {
                    list.get(j).meleDamage += 100;
                    //skillPoints.meleDamage += 100;
                }

            }
        }
    }

    @Override
    public int getXpToNextLevel() {
        return xpToNextLevel;
    }

    @Override
    public void setXpToNextLevel(int xpToNextLevel) {
        this.xpToNextLevel = xpToNextLevel;
    }


    public int compareTo(Player o) {
        return toString().compareTo(o.toString());
    }


    @Override
    public void levelUp() {
        if (this.isAlive) {
            if (XP >= xpToNextLevel) {

                super.level++;
                statPoints.HP = 1000 + (level - 1) * 200;
                statPoints.dodgeChance += 5;
                statPoints.hitChance += 5;
                this.XP -= xpToNextLevel;
                xpToNextLevel += 1200;

                if (level % 3 == 0) {
                    for (int j = 0; j < list.size(); j++) {
                        list.get(j).meleDamage += 250;
                    }
                }
                System.out.println("Fighter (" + this.pos + ") leveled up.");
            }
        }
    }

    @Override
    public void attack(String s, Hero monster) {
        Iterator<Skills> itr = list.iterator();
        while (itr.hasNext()) {
            Skills element = itr.next();
            if (element.name.equals(s)) {

                if (monster.statPoints.HP - element.meleDamage > 0)
                    monster.statPoints.HP -= element.meleDamage;
                else
                    monster.statPoints.HP = 0;
                System.out.println("Fighter (" + this.pos + ") attacked " + monster.pos);
                return;
            }
        }
        System.out.println("The attack doesn't belong to the fighter");

    }

    @Override
    public void afisare(boolean bool) {
        if (bool)
            System.out.println("The fighter attacks ");
        for (int i = 0; i < list.size(); i++) {
            if (i == 0 && list.get(i).levelRequirement <= level)
                System.out.println("Attacks available: " + list.get(i).name + " (" + list.get(i).meleDamage + ")");
            else if (i != 0 && list.get(i).levelRequirement <= level)
                System.out.println("                   " + list.get(i).name + " (" + list.get(i).meleDamage + ")");
        }
    }

    public String getRandomAttack(Player player, Enemy enemy) {
        Random random = new Random();

        int rand = random.nextInt(list.size() - 1);
        while (list.get(rand).levelRequirement > this.level) {
            rand = random.nextInt(list.size() - 1);
        }
        return list.get(rand).name;
    }

    public String seePlayerStats() {
        return "Fighter " + " Pos " + pos + " HP " + statPoints.HP + " level " + level + " XP " + XP + " XPToNextLevel " + xpToNextLevel;
    }

    public String seeEnemyStats() {
        return "Fighter " + " Pos " + pos + " HP " + statPoints.HP + " level " + level;
    }

    public ArrayList<Skills> getList() {
        return list;
    }
}
