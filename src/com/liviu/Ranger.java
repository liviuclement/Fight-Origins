package com.liviu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Ranger extends Hero {

    private Skills arrowAttack = new Skills("arrow attack", 0, 200, 1, 0, 1, 0);
    private Skills daggerAttack = new Skills("dagger attack", 250, 0, 1, 0, 1, 0);
    private Skills goldArrowAttack = new Skills("gold arrow attack", 0, 400, 1, 0, 5, 0);
    private Skills fireArrowAttack = new Skills("fire arrow attack", 0, 800, 1, 0, 10, 0);

    private ArrayList<Skills> list = new ArrayList<>();

    Item weakHelmet = new Item(Item.Slot.HEAD, 150, 0, 0, 0);
    Item strongHelmet = new Item(Item.Slot.HEAD, 450, 0, 0, 0);
    Item woodBow = new Item(Item.Slot.HANDS, 0, 0, 100, 0);
    Item ironBow = new Item(Item.Slot.HANDS, 0, 0, 200, 0);
    Item goldBow = new Item(Item.Slot.HANDS, 0, 0, 400, 0);
    Item weakChestArmor = new Item(Item.Slot.CHEST, 100, 0, 0, 0);
    Item strongChestArmor = new Item(Item.Slot.CHEST, 300, 0, 0, 0);
    Item weakLegsArmor = new Item(Item.Slot.LEGS, 80, 0, 0, 0);
    Item strongLegsArmor = new Item(Item.Slot.LEGS, 200, 0, 0, 0);
    Item silverShoes = new Item(Item.Slot.FEET, 30, 0, 0, 0);
    Item goldShoes = new Item(Item.Slot.FEET, 75, 0, 0, 0);

    public Ranger(int pos) {
        name = "Ranger";
        super.isAlive = true;
        super.level = 1;
        super.xpToNextLevel = 800;
        super.XP = 0;
        super.XPToGive = 800;
        Random r = new Random();
        super.statPoints = new Stats(700, 0, r.nextInt(11), 30, 10);
        this.pos = pos;
        list.add(arrowAttack);
        list.add(daggerAttack);
        list.add(goldArrowAttack);
        list.add(fireArrowAttack);

    }

    Ranger(int pos, int level, int XP) {

        name = "Ranger";
        super.isAlive = true;
        super.level = 1;
        super.xpToNextLevel = 800 + (level - 1) * 1200;
        super.XP = XP;
        super.XPToGive = 800;
        super.toGiveOrNotToGive = true;
        Random r = new Random();
        super.statPoints = new Stats(700, 0, r.nextInt(11), 30, 10);
        this.pos = pos;
        list.add(arrowAttack);
        list.add(daggerAttack);
        list.add(goldArrowAttack);
        list.add(fireArrowAttack);

        for (int i = 1; i < level; i++) {
            super.level++;
            statPoints.HP += 150;
            statPoints.dodgeChance += 5;
            statPoints.hitChance += 5;
            super.XPToGive += 800;
            if (super.level % 3 == 0) {
                for (int j = 0; j < list.size(); j++) {
                    list.get(j).rangeDamage += 100;
                    list.get(j).meleDamage += 150;
                }
            }
        }
    }

    Ranger(int pos, int level, int HP, int XP) {

        name = "ranger";
        super.isAlive = true;
        super.level = 1;
        super.xpToNextLevel = 800;
        super.XP = XP;
        super.XPToGive = 800;
        super.toGiveOrNotToGive = true;
        Random r = new Random();
        super.statPoints = new Stats(700, 0, r.nextInt(11), 30, 10);
        this.pos = pos;
        list.add(arrowAttack);
        list.add(daggerAttack);
        list.add(goldArrowAttack);
        list.add(fireArrowAttack);
        statPoints.HP = HP;
        for (int i = 1; i < level; i++) {
            super.level++;
            statPoints.dodgeChance += 5;
            statPoints.hitChance += 5;
            super.XPToGive += 800;
            if (super.level % 3 == 0) {
                for (int j = 0; j < list.size(); j++) {
                    list.get(j).rangeDamage += 100;
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


    @Override
    public void levelUp() {
        if (this.isAlive) {
            if (XP >= xpToNextLevel) {

                super.level++;
                statPoints.HP = 700 + (level - 1) * 150;
                statPoints.dodgeChance += 5;
                statPoints.hitChance += 5;
                this.XP -= xpToNextLevel;
                xpToNextLevel += 1200;

                if (level % 3 == 0) {
                    for (int j = 0; j < list.size(); j++) {
                        list.get(j).meleDamage += 150;
                        list.get(j).rangeDamage += 100;
                    }
                }
                System.out.println("Ranger (" + this.pos + ") leveled up.");
            }
        }
    }


    @Override
    public void attack(String s, Hero monster) {
        Iterator<Skills> itr = list.iterator();
        while (itr.hasNext()) {
            Skills element = itr.next();
            if (element.name.equals(s)) {
                if (monster.statPoints.HP - element.rangeDamage > 0 && monster.statPoints.HP - element.meleDamage > 0) {
                    monster.statPoints.HP -= element.rangeDamage;
                    monster.statPoints.HP -= element.meleDamage;
                } else {
                    monster.statPoints.HP = 0;
                }
                System.out.println("Ranger (" + this.pos + ") attacked " + monster.pos);
                return;
            }

        }
        System.out.println("The attack doesn't belong to the Ranger ");

    }

    @Override
    public void afisare(boolean bool) {
        if (bool)
            System.out.println("The Ranger attacks ");
        for (int i = 0; i < list.size(); i++) {
            int x;
            if (list.get(i).meleDamage > 0)
                x = list.get(i).meleDamage;
            else
                x = list.get(i).rangeDamage;
            if (i == 0 && list.get(i).levelRequirement <= level) {
                System.out.println("Attacks available: " + list.get(i).name + " (" + x + ")");
            } else if (i != 0 && list.get(i).levelRequirement <= level) {
                System.out.println("                   " + list.get(i).name + " (" + x + ")");
            }
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
        return "Ranger " + " Pos " + pos + " HP " + statPoints.HP + " level " + level + " XP " + XP + " XPToNextLevel " + xpToNextLevel;
    }

    public String seeEnemyStats() {
        return "Ranger " + " Pos " + pos + " HP " + statPoints.HP + " level " + level;
    }

    public ArrayList<Skills> getList() {
        return list;
    }
}
