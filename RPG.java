import java.util.Scanner;
import java.util.Random;

public class RPG {
  	static int score;
  	static int[] upgrades = new int[5];
  
    public static class Entity {
    	String name;
        int max_hp, hp, eva, crit, dmg, heals_left, heal_hp;
    }

    public static void main(String[] args) {
    	Scanner input = new Scanner (System.in);
      
        System.out.print("Enter player name: ");
      
        // initialize player
        Entity player = new Entity();
        player.name = input.nextLine() + " (You)";
      	player.max_hp = 50;
        player.hp = 50;
        player.eva = 10;
        player.crit = 20;
        player.dmg = 10;
      
      	Entity enemy = new Entity();
      	enemy.max_hp = 50;
      	enemy.eva = 0;
        enemy.crit = 0;
        enemy.dmg = 10;
      
      	score = 0;
      
      	for (int i = 1; i <= 10; i++) {
          	// initialize player
          	player.max_hp = 50 + upgrades[0] * 2;
          	player.hp = player.max_hp;
          	player.dmg = 10 + upgrades[1];
        	player.eva = 10 + upgrades[2] * 2;
        	player.crit = 20 + upgrades[3] * 4;
          	player.heal_hp = 4 + upgrades[4] * 2;
      		player.heals_left = 5;
          
            // initialize enemy
            enemy.name = "Bob " + i;
            enemy.max_hp = (int) (enemy.max_hp * 1.1);
            enemy.heal_hp = (int) (enemy.heal_hp * 1.15);
            enemy.crit = (int) (enemy.crit * 1.1);
          	enemy.crit += 3;
          	enemy.dmg = (int) (enemy.dmg * 1.15);
            enemy.hp = enemy.max_hp;

            int move = 1;
            while(player.hp > 0 && enemy.hp > 0) {
                printUI(player, enemy, move);
                move(player, enemy);
                move++;
            }
            printUI(player, enemy, 0);
            System.out.println();
          
          	score += i * 100 / (move - 1);

            if(player.hp <= 0) {
                System.out.println("Oops! You lost...");
              	System.out.println("You died on round " + i + ".");
              	break;
            } else if(enemy.hp <= 0) {
                System.out.println("Hooray! You beat " + enemy.name + " 👷!");
              	score += 100;
              	enemy.max_hp += 10;
              	
              	if (i == 10) {
              	    score += 200;
              	    break;
              	}
              	
              	printShop(player);
            }
        }
      	System.out.println("Your final score was " + score + "!");
      	
      	if (score <= 70) {
      	    System.out.println("Can you beat Gokul's score of 70?");
      	} else {
      	    System.out.println("You beat Gokul's score of 70!");
      	}
    }
  
  	public static void printShop(Entity p1) {
      	Scanner input = new Scanner(System.in);
      	System.out.println("\nWhat do you want to upgrade?");
        int health_points = upgrades[0] * 5 + 10, dmg_points = upgrades[1] * 5 + 10, eva_chance_points = upgrades[2] * 5 + 10, crit_chance_points = upgrades[3] * 5 + 10, heal_points = upgrades[4] * 5 + 10;
        System.out.println("1. Upgrade Max Health Cost: " + health_points);
        System.out.println("2. Upgrade Damage Cost: " + dmg_points);
        System.out.println("3. Upgrade Evade Chance Cost: " + eva_chance_points);
        System.out.println("4. Upgrade Crit Chance Cost: " + crit_chance_points);
        System.out.println("5. Upgrade Health Healed Cost: " + heal_points);
        System.out.println("Use your score points (" + score + ") or enter 0 to exit: ");

        int upgrade = 1;
        try {
            upgrade = input.nextInt();
        } catch (Exception e) {
            System.out.println("Please enter an integer.");
            printShop(p1);
          	return;
        }
      	
		if (upgrade >= 1 && upgrade <= 5) {
          	if (upgrades[upgrade - 1] * 5 + 10 > score) {
              	System.out.println("Insufficient points.");
              	printShop(p1);
              	return;
            } else {
          		score -= upgrades[upgrade - 1] * 5 + 10;
           		upgrades[upgrade - 1]++;
          		printShop(p1);
          		return;
        	}
        } else if (upgrade == 0) {
          	return;
        } else {
           	System.out.println("Please enter a valid integer.");
           	printShop(p1);
      		return;
        }
    }

    public static void printUI(Entity p1, Entity p2, int move) {
        System.out.println(String.format("%50s", "").replace(' ','-'));

        if (move != 0) {
            System.out.println("MOVE " + move);
        } else {
            System.out.println("COMBAT OVER");
        }

        System.out.printf("%-25s", p1.name);
        System.out.printf("%25s%n", p2.name);

        System.out.printf("%-25s", "Health: " + p1.hp);
        System.out.printf("%25s%n", "Health: " + p2.hp);

        System.out.printf("%-25s", "Dodge Chance: " + p1.eva);
        System.out.printf("%25s%n", "Dodge Chance: " + p2.eva);

        System.out.printf("%-25s", "Crit Chance: " + p1.crit);
        System.out.printf("%25s%n", "Crit Chance: " + p2.crit);

        System.out.printf("%-25s", "Damage: " + p1.dmg);
        System.out.printf("%25s%n", "Damage: " + p2.dmg);
    }

    public static void move(Entity p1, Entity p2) {
        // p1 is performing move

        Scanner input = new Scanner (System.in);
        Random rand = new Random();
      	double heal_chance_enemy = (double) p2.hp / p2.max_hp * 200;

        System.out.println("\nDo you want to attack or heal?");
        System.out.println("1. Attack");
        System.out.println("2. Heal " + p1.heal_hp + "hp (" + p1.heals_left + " heals left)");
        System.out.print("Enter move number: ");
      
      	int eva_chance = rand.nextInt(100) + 1;
      	int crit_chance = rand.nextInt(100) + 1;
		
      	int move = 1;
      
      	try {
        	move = input.nextInt();
        } catch (Exception e) {
          	System.out.println("Please enter an integer.");
          	move(p1, p2);
          	return;
        }
      
        if(move == 1 && eva_chance > p2.eva) {
          	if (crit_chance < p1.crit) {
            	p2.hp -= p1.dmg;
            }
            p2.hp -= p1.dmg;
        } else if(move == 2) {
          	if (p1.heals_left > 0) {
            	p1.hp = Math.min(p1.hp + p1.heal_hp, p1.max_hp);
          		p1.heals_left--;
            } else {
              	System.out.println("You have no heals left!");
          		move(p1, p2);
          		return;
            }
        } else {
          	System.out.println("Invalid move.");
          	move(p1, p2);
          	return;
        }
      
       if (p2.hp <= 0) {
         return;
       }
      
      	eva_chance = rand.nextInt(100) + 1;
      
       int enemy_move = rand.nextInt(100) + 1;
       
       if(enemy_move <= heal_chance_enemy) {
         	if (eva_chance > p1.eva) {
        		crit_chance = rand.nextInt(100) + 1;
         		if (crit_chance < p2.crit) {
            		p1.hp -= p2.dmg;
            	}
         		p1.hp -= p2.dmg;
            }
       } else {
         	p2.hp = Math.min(p2.hp + p2.heal_hp, p2.max_hp);
       }
  	}
}
