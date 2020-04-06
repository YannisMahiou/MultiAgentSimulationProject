package Model.Agent;

public class BowMan extends RangedAgent{

    public BowMan(int hp, int damageReduction, int speed, int strength, String color) {
        super(hp, damageReduction, speed, strength, 2, color);
    }

    @Override
    public FightStatus attack(Agent enemy) {
        int damage = this.getStrength() - enemy.getDamageReduction();
        int enemyCounterDamage = enemy.getStrength() - this.getDamageReduction();

        enemy.takeDamage(damage);

        if (enemy.isAlive()){
            if (enemy instanceof RangedAgent) {
                this.takeDamage(enemyCounterDamage);
            }
            if(!this.isAlive()){
                return FightStatus.LOST;
            }
        }
        else{
            return FightStatus.WIN;
        }

        if(this.getSpeed() - enemy.getSpeed() > 5){
            enemy.takeDamage(damage);
            if(!enemy.isAlive()){
                return FightStatus.WIN;
            }
        }
        else if (enemy instanceof RangedAgent && enemy.getSpeed() - this.getSpeed() > 5){
            this.takeDamage(enemyCounterDamage);
            if(!this.isAlive()){
                return FightStatus.LOST;
            }
        }

        return FightStatus.DRAW;
    }

    @Override
    public String toString(){
        return this.getColor() + "D" + "\u001B[0m";
    }

}
