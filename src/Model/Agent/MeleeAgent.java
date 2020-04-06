package Model.Agent;

import Model.Terrain.AbstractTerrain;

import java.util.List;
import java.util.Random;

/**
 * Created by yannis on 14/01/20.
 */
public abstract class MeleeAgent extends Agent{

    public MeleeAgent(int hp, int damageReduction, int speed, int strength, int range, String color) {
        super(hp, damageReduction, speed, strength, range, color);
    }

    public void move(AbstractTerrain terrain, List<Agent> enemyTeam){
        List<Agent> closestEnemies = findClosestEnemies(enemyTeam);

        Random r = new Random();
        int rand = r.nextInt(closestEnemies.size() - 1);

        Agent focused = closestEnemies.get(rand);
        boolean canAttack = false;

        switch (getDirection(focused)){
            case BOT:
                canAttack = moveTo(terrain, focused.getPosX(), focused.getPosY() - 1);
                break;
            case TOP:
                canAttack = moveTo(terrain, focused.getPosX(), focused.getPosY() + 1);
                break;
            case LEFT:
                canAttack = moveTo(terrain, focused.getPosX() - 1, focused.getPosY());
                break;
            case RIGHT:
                canAttack = moveTo(terrain, focused.getPosX() + 1, focused.getPosY());
                break;
        }

        if(canAttack){
            switch (attack(focused)){
                case WIN:
                    // Adversaire vaincu
                    break;
                case LOST:
                    // Agent mort au combat
                    break;
                case DRAW:
                    // Les deux combattants sont en vie
                    break;
            }
        }
    }

    public abstract FightStatus attack(Agent enemy);

    private Direction getDirection(Agent enemy){
        if(this.getPosY() == enemy.getPosY()){
            if(this.getPosX() > enemy.getPosX()){
                return Direction.RIGHT;
            }
            else {
                return Direction.LEFT;
            }
        }
        if(this.getPosY() > enemy.getPosX()){
            return Direction.TOP;
        }
        else {
            return Direction.BOT;
        }
    }

}
