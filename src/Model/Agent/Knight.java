package Model.Agent;

public class Knight extends MeleeAgent {

    public Knight(int hp, int damageReduction, int speed, int strength, String color) {
        super(hp, damageReduction, speed, strength, 1, color);
    }

    @Override
    public FightStatus attack(Agent enemy) {

        int damage = this.getStrength() - enemy.getDamageReduction();
        int enemyCounterDamage = enemy.getStrength() - this.getDamageReduction();

        if (enemy instanceof AxeMan) {
            damage *= ADVANTAGE;
            enemyCounterDamage *= DISADVANTAGE;
        }
        if (enemy instanceof Lancer) {
            damage *= DISADVANTAGE;
            enemyCounterDamage *= ADVANTAGE;
        }

        enemy.takeDamage(damage);

        if (enemy.isAlive()) {
            if (enemy instanceof MeleeAgent) {
                this.takeDamage(enemyCounterDamage);
            }
            if (!this.isAlive()) {
                return FightStatus.LOST;
            }
        } else {
            return FightStatus.WIN;
        }

        if (this.getSpeed() - enemy.getSpeed() > 5) {
            enemy.takeDamage(damage);
            if (!enemy.isAlive()) {
                return FightStatus.WIN;
            }
        } else if (enemy instanceof MeleeAgent && enemy.getSpeed() - this.getSpeed() > 5) {
            this.takeDamage(enemyCounterDamage);
            if (!this.isAlive()) {
                return FightStatus.LOST;
            }
        }

        return FightStatus.DRAW;
    }

    @Override
    public String toString() {
        return this.getColor() + "T" + "\u001B[0m";
    }

}
