package com.liviu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Enemy extends Player {
    Enemy() {
        super();
    }

    List<Hero> enemies = new ArrayList<>();
    private Random random = new Random();

    void addRandomCharacters(int level) {
        switch (random.nextInt(4)) {
            case 0: {
                List<Integer> list = new ArrayList<>();
                list.add(11);
                list.add(21);
                list.add(31);
                add(list, level);

                break;
            }
            case 1: {
                List<Integer> list = new ArrayList<>();
                list.add(11);
                list.add(22);
                list.add(31);
                add(list, level);

                break;
            }

            case 2: {
                List<Integer> list = new ArrayList<>();
                list.add(12);
                list.add(21);
                list.add(32);
                add(list, level);

                break;
            }

            case 3: {
                List<Integer> list = new ArrayList<>();
                list.add(21);
                list.add(22);
                list.add(23);
                add(list, level);

                break;
            }
            default:
                break;
        }

    }

    private void add(List<Integer> list, int level) {
        int rand = list.get(random.nextInt(list.size()));
        enemies.add(new Fighter(rand, getRandomBetweenRange(level - 1, level + 1), 0));
        addPlayer(getMatrix(), 'F', rand);
        list.remove(list.indexOf(rand));

        rand = list.get(random.nextInt(list.size()));
        enemies.add(new Ranger(rand, getRandomBetweenRange(level - 1, level + 1), 0));
        addPlayer(getMatrix(), 'R', rand);
        list.remove(list.indexOf(rand));

        rand = list.get(random.nextInt(list.size()));
        enemies.add(new Mage(rand, getRandomBetweenRange(level - 1, level + 1), 0));
        addPlayer(getMatrix(), 'M', rand);
        list.remove(list.indexOf(rand));
    }

    void randomAttack(int i, Player player) {
        Random random = new Random();
        int rand = random.nextInt(3);

        String attack = enemies.get(i).getRandomAttack(player, this);
        boolean protect = attack.equals("shield") || attack.equals("heal");

        verifyIfDeadAndAtack((protect) ? enemies : player.heroes, i, rand, attack);
    }

    private void verifyIfDeadAndAtack(List<Hero> listOfHeroes, int i, int rand, String attack) {
        while (!listOfHeroes.get(rand).isAlive) {
            rand = random.nextInt(3);
        }
        enemies.get(i).attack(attack, listOfHeroes.get(rand));
    }

    private static int getRandomBetweenRange(int min, int max) {

        Random rand = new Random();
        int randomNum;
        if (min != 0)
            randomNum = rand.nextInt((max - min) + 1) + min;
        else {
            min++;
            randomNum = rand.nextInt((max - min) + 1) + min;
        }
        return randomNum;
    }

    void removeEnemy() {
        int mat_i, mat_j;
        for (Hero enemy : enemies) {
            if (!enemy.isAlive) {
                mat_i = enemy.pos / 10 - 1;
                mat_j = enemy.pos % 10 - 1;
                getMatrix()[mat_i][mat_j] = 'O';
            }
        }
    }
}
