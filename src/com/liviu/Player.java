package com.liviu;

import java.util.*;

public class Player {
    private int gold;
    private int round;
    private char[][] matrix;
    List<Hero> heroes = new ArrayList<>();

    Player() {
        gold = 1000;
        round = 1;
        matrix = new char[3][3];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = 'O';
            }
    }

//    Player(int[] level, int[] XP) {
//        gold = 1000;
//        round = 1;
//        matrix = new char[3][3];
//        for (int i = 0; i < matrix.length; i++)
//            for (int j = 0; j < matrix[0].length; j++) {
//                matrix[i][j] = 'O';
//            }
//        select(level, XP);
//    }

    void setRound(int round) {
        this.round = round;
    }

    int getRound() {
        return round;
    }

    char[][] getMatrix() {
        return matrix;
    }

    private void setMatrix(char[][] matrix) {
        this.matrix = matrix;
    }

    private void setNewMatrix() {
        setMatrix(new char[3][3]);
        for (int i = 0; i < getMatrix().length; i++)
            for (int j = 0; j < getMatrix()[0].length; j++) {
                matrix[i][j] = 'O';
            }
    }

    private List<Integer> numbers = new ArrayList<Integer>() {
        {
            add(11);
            add(12);
            add(13);
            add(21);
            add(22);
            add(23);
            add(31);
            add(32);
            add(33);
        }
    };

    void intro() {
        System.out.println("Welcome, you have " + gold + " gold.");
        System.out.println("Your heroes are: Fighter, Ranger, Mage. The battlefield is a 3x3 matrix.");
    }

    void select(int[] level, int[] XP) {
        Scanner scanner = new Scanner(System.in);
        String answer;
        int pos;
        System.out.println("How many characters do you wanna play with?");
        int number = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < number; i++) {
            System.out.println("Choose character #" + (i + 1));
            answer = scanner.next();

            if (answer.charAt(0) != 'f' && answer.charAt(0) != 'r' && answer.charAt(0) != 'm') {
                while (answer.charAt(0) != 'f' && answer.charAt(0) != 'r' && answer.charAt(0) != 'm') {
                    System.out.println("Make a valid choice");
                    System.out.println("Choose character #" + (i + 1));
                    answer = scanner.next();
                }
            }
            System.out.println("Choose the position of character #" + (i + 1));
            pos = scanner.nextInt();
            boolean value = verifyVacancy(pos);
            if (value) {
                while (value) {
                    System.out.println("The chosen position is not vacant.");
                    System.out.println("Choose the position of character #" + (i + 1));
                    pos = scanner.nextInt();
                    value = verifyVacancy(pos);
                }
            }
            if (!numbers.contains(pos)) {
                while (!numbers.contains(pos)) {
                    System.out.println("Type a valid position.");
                    System.out.println("Choose the position of character #" + (i + 1));
                    pos = scanner.nextInt();
                }
            }
            check(answer, pos, level[i], XP[i]);
        }
    }

    void continueBattle() {
        setNewMatrix();
        List<Hero> copyOfHeroes = new ArrayList<>(heroes);
        heroes = new ArrayList<>();
        System.out.println("Your characters are: ");
        for (int i = 0; i < copyOfHeroes.size(); i++) {
            System.out.println(copyOfHeroes.get(i).name);
        }
        Scanner scanner = new Scanner(System.in);
        int pos;
        for (int i = 0; i < copyOfHeroes.size(); i++) {
            System.out.println("Where do you wanna place the " + copyOfHeroes.get(i).name + "?");
            pos = scanner.nextInt();
            boolean value = verifyVacancy(pos);
            while (value) {
                System.out.println("The chosen position is not vacant.");
                System.out.println("Choose the position of character #" + (i + 1));
                pos = scanner.nextInt();
                value = verifyVacancy(pos);
            }

            while (!numbers.contains(pos)) {
                System.out.println("Type a valid position.");
                System.out.println("Choose the position of character #" + (i + 1));
                pos = scanner.nextInt();
            }

            check(copyOfHeroes.get(i).name, pos, copyOfHeroes.get(i).level, copyOfHeroes.get(i).XP);
        }
    }

    private boolean verifyVacancy(int pos) {
        boolean value = false;
        for (int j = 0; j < heroes.size(); j++) {
            if (heroes.get(j).pos == pos) {
                value = true;
                break;
            }
        }
        return value;
    }

    void printMatrix(Player player, Enemy enemy) {
        for (int i = 0; i < player.matrix.length; i++) {
            int length = 0;
            for (int j = 0; j < player.matrix.length; j++) {
                if (heroes.get(j).seePlayerStats().length() > length)
                    length = heroes.get(j).seePlayerStats().length();
            }
            for (int j = 0; j < player.matrix[0].length; j++) {
                System.out.print(player.matrix[i][j] + " ");
            }

            System.out.print("  ");

            for (int j = 0; j < player.matrix[0].length; j++) {
                System.out.print(enemy.getMatrix()[i][j] + " ");
            }

            System.out.print("  ");

            if (i == 0) {
                System.out.print("Heroes: " + heroes.get(i).seePlayerStats());
            } else {
                System.out.print("        " + heroes.get(i).seePlayerStats());
            }

            System.out.print("    ");

            for (int j = 0; j < length - heroes.get(i).seePlayerStats().length(); j++) {
                System.out.print(' ');
            }

            List<Hero> copyOfEnemies = new ArrayList<>();
            for (int j = 0; j < enemy.enemies.size(); j++) {
                int thisPos = enemy.enemies.get(j).pos;
                int thisHp = enemy.enemies.get(j).statPoints.HP;
                int thisLevel = enemy.enemies.get(j).level;
                int thisXP = enemy.enemies.get(j).XP;
                if (enemy.enemies.get(j) instanceof Fighter) {
                    copyOfEnemies.add(new Fighter(thisPos, thisLevel, thisHp, thisXP));
                } else if (enemy.enemies.get(j) instanceof Mage)
                    copyOfEnemies.add(new Mage(thisPos, thisLevel, thisHp, thisXP));
                else if (enemy.enemies.get(j) instanceof Ranger)
                    copyOfEnemies.add(new Ranger(thisPos, thisLevel, thisHp, thisXP));
            }

            for (int it = 0; it < enemy.enemies.size() - 1; it++) {
                for (int itt = it + 1; itt < enemy.enemies.size(); itt++) {
                    if (copyOfEnemies.get(itt).pos < copyOfEnemies.get(it).pos) {
                        Hero aux = copyOfEnemies.get(itt);
                        copyOfEnemies.set(itt, copyOfEnemies.get(it));
                        copyOfEnemies.set(it, aux);
                    }
                }
            }

            if (i == 0) {
                System.out.print("Enemies: " + copyOfEnemies.get(i).seeEnemyStats());
            } else {
                System.out.print("         " + copyOfEnemies.get(i).seeEnemyStats());
            }
            System.out.println();
        }

    }

    void addPlayer(char[][] matrix, char hero, int pos) {
        int x = (pos / 10) - 1;
        int y = (pos % 10) - 1;
        String player = "";
        if (matrix[x][y] != 'O') {
            switch (matrix[x][y]) {
                case 'M':
                    player = "Mage";
                    break;
                case 'R':
                    player = "Ranger";
                    break;
                case 'F':
                    player = "Fighter";
                    break;
            }
            System.out.println("On the selected field there is already a" + player);
        } else {
            matrix[x][y] = hero;
        }
    }

    private void check(String answer, int pos, int level, int XP) {
        if (answer.toLowerCase().indexOf("f") == 0) {
            //verifyCharacter(answer, level, XP, character);
            heroes.add(new Fighter(pos, level, XP));
            addPlayer(matrix, 'F', pos);
        } else if (answer.toLowerCase().indexOf("r") == 0) {
            // verifyCharacter(answer, level, XP, character);
            heroes.add(new Ranger(pos, level, XP));
            addPlayer(matrix, 'R', pos);
        } else if (answer.toLowerCase().indexOf("m") == 0) {
            //verifyCharacter(answer, level, XP, character);
            heroes.add(new Mage(pos, level, XP));
            addPlayer(matrix, 'M', pos);
        }
    }

    boolean continueFight(Enemy enemy) {
        return ((heroes.get(0).isAlive || heroes.get(1).isAlive || heroes.get(2).isAlive) && (enemy.enemies.get(0).isAlive || enemy.enemies.get(1).isAlive || enemy.enemies.get(2).isAlive));
    }

    void verifyAlive(List<Hero> list) {
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).isAlive()) {
                list.get(i).setAlive(false);
            }
        }
    }

    void removePlayer() {
        int mat_i, mat_j;
        for (Hero hero : heroes) {
            if (!hero.isAlive) {
                mat_i = hero.pos / 10 - 1;
                mat_j = hero.pos % 10 - 1;
                matrix[mat_i][mat_j] = 'O';
            }
        }
    }

    boolean verifyAttack(int i, String attack) {
        boolean bool = true;
        for (int m = 0; m < heroes.get(i).getList().size(); m++) {
            if (heroes.get(i).getList().get(m).name.equals(attack)) {
                bool = true;
                break;
            } else {
                bool = false;
            }
        }
        return bool;
    }

    boolean verifyPosition(int pos, Enemy enemy) {
        boolean bool = false;
        if (pos == 11 || pos == 12 || pos == 13 || pos == 21 || pos == 22 || pos == 23 || pos == 31 || pos == 32 || pos == 33) {
            bool = true;
        }
        if (bool) {
            for (int m = 0; m < enemy.enemies.size(); m++) {
                if (enemy.enemies.get(m).pos == pos) {
                    bool = true;
                    break;
                } else {
                    bool = false;
                }
            }
        }
        return bool;
    }

    void checkWinner(Enemy enemy) {
        if (!continueFight(enemy)) {
            for (int i = 0; i < heroes.size(); i++) {
                if (heroes.get(i).isAlive) {
                    System.out.println("You win!");
                    break;
                }
            }

            for (int i = 0; i < enemy.enemies.size(); i++) {
                if (enemy.enemies.get(i).isAlive) {
                    System.out.println("You lost!");
                    break;
                }
            }
        }
    }

    void shareXP(Enemy enemy) {
        int xp = 0;
        for (int i = 0; i < enemy.enemies.size(); i++) {
            if (!enemy.enemies.get(i).isAlive && enemy.enemies.get(i).toGiveOrNotToGive) {
                enemy.enemies.get(i).toGiveOrNotToGive = false;
                xp += enemy.enemies.get(i).XPToGive;
            }
        }
        int count = 0;
        for (int i = 0; i < heroes.size(); i++) {
            if (heroes.get(i).isAlive) {
                count++;
            }
        }
        if (count > 0) {
            int xpToGive = xp / count;
            for (int i = 0; i < heroes.size(); i++) {
                if (heroes.get(i).isAlive) {
                    heroes.get(i).XP += xpToGive;
                }
            }
        }
    }
}
