package com.liviu;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int pos;
        int level;
        String attack;
        Player player = new Player();
        Enemy enemy = new Enemy();
        player.intro();
        int[] lev = {1, 1, 1};
        int[] xp = {0, 0, 0};
        player.select(lev, xp);
        System.out.println("Choose monster level: ");
        level = scanner.nextInt();
        scanner.nextLine();
        enemy.addRandomCharacters(level);
        player.printMatrix(player, enemy);
        int x = 3, y = 3;
        boolean continueFight = true;
        boolean continueBattle = true;
        while (continueBattle) {
            //boolean continueFight = player.continueFight(enemy);
            while (continueFight) {
                int lmk = 0;
                System.out.println("Round " + player.getRound());
                player.setRound(player.getRound() + 1);

                for (int i = 0, j = 0; i < x && j < y; i++, j++) {
                    while (!player.heroes.get(i).isAlive && i < x - 1)
                        i++;
                    if (!player.heroes.get(i).isAlive) {
                        i = 0;
                        while (!player.heroes.get(i).isAlive && i < x - 1)
                            i++;
                    }
                    if (player.heroes.get(i).isAlive) {
                        player.heroes.get(i).afisare(true);
                        System.out.println("Choose the attack and the attack position: ");

                        attack = scanner.nextLine();
                        pos = scanner.nextInt();
                        scanner.nextLine();
                        boolean boolAttack = player.verifyAttack(i, attack);
                        while (!boolAttack) {
                            System.out.println("Your given attack name is not available. Choose one of the attacks below.");
                            player.heroes.get(i).afisare(false);
                            //System.out.println("Choose the attack and the attack position: ");
                            attack = scanner.nextLine();
                            boolAttack = player.verifyAttack(i, attack);
                        }
                        boolean boolPosition = player.verifyPosition(pos, enemy);
                        while (!boolPosition) {
                            System.out.println("The position you want to attack is vacant. Choose a valid position.");
                            pos = scanner.nextInt();
                            scanner.nextLine();
                            boolPosition = player.verifyPosition(pos, enemy);
                        }
                        int pos1 = getPosEnemy(pos, enemy);
                        boolean value = false;

                        if (attack.equals("shield") || attack.equals("heal")) {
                            pos = getPosPlayer(pos, player);
                            value = true;
                        }

                        if (value) {
                            player.heroes.get(i).attack(attack, player.heroes.get(pos));
                        } else {
                            player.heroes.get(i).attack(attack, enemy.enemies.get(pos1));
                        }
                    }
                    enemy.verifyAlive(enemy.enemies);

                    while (!enemy.enemies.get(j).isAlive && j < y - 1) {
                        j++;
                    }
                    if (enemy.enemies.get(j).isAlive) {
                        enemy.randomAttack(j, player);
                    }
                    player.verifyAlive(player.heroes);

                    player.removePlayer();
                    enemy.removeEnemy();


                    if (j == 2 && lmk == 0 && player.continueFight(enemy)) {
                        j = 0;
                        lmk++;
                    } else if (i == 2 && lmk == 0 && player.continueFight(enemy)) {
                        i = 0;
                        lmk++;
                    }
                    player.shareXP(enemy);
                    for (int k = 0; k < player.heroes.size(); k++) {
                        player.heroes.get(k).levelUp();
                    }
                    player.printMatrix(player, enemy);
                }
                if (!player.continueFight(enemy))
                    player.checkWinner(enemy);

                continueFight = player.continueFight(enemy);
            }
            String ans;
            System.out.println("Do you wanna continue playing? (y/n)");
            ans = scanner.nextLine();
            if(ans.charAt(0) == 'n') {
                continueBattle = false;
            }
            else if(ans.charAt(0) == 'y')
            {
                player.continueBattle();
                enemy = new Enemy();
                System.out.println("Choose monster level: ");
                level = scanner.nextInt();
                scanner.nextLine();
                enemy.addRandomCharacters(level);
                player.printMatrix(player,enemy);
                player.setRound(0);
                continueBattle = true;
                continueFight = true;
            }
        }
    }


    private static int getPosEnemy(int pos, Enemy enemy) {
        for (int i = 0; i < 3; i++)
            if (enemy.enemies.get(i).pos == pos)
                return i;
        return 0;
    }

    private static int getPosPlayer(int pos, Player player) {
        for (int i = 0; i < 3; i++)
            if (player.heroes.get(i).pos == pos)
                return i;
        return 0;
    }


}
