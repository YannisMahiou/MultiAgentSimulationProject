import Model.Agent.*;
import Model.Factory.AgentFactory;
import Model.Factory.IFactory;
import Model.Game.Game;
import Model.Terrain.AbstractTerrain;
import Model.Terrain.Terrain;
import org.junit.Assert;
import org.junit.Test;

public class ApplicationTest {

    IFactory factory = new AgentFactory();

    @Test
    public void triangleDamageTest()
    {
        try{
            Agent axeMan = new AxeMan(25, 5, 5, 15, "");
            Agent axeMan2 = new AxeMan(25, 5, 5, 15, "");
            Agent knight = new Knight(25, 5, 5, 15, "");
            Agent knight2 = new Knight(25, 5, 5, 15, "");
            Agent lancer = new Lancer(25, 5, 5, 15, "");
            Agent lancer2 = new Lancer(25, 5, 5, 15, "");

            // Damage test axeman vs knight
            Assert.assertEquals(FightStatus.DRAW, axeMan.attack(knight));
            Assert.assertEquals(17, knight.getHp());
            Assert.assertEquals(13, axeMan.getHp());

            // Damage test axeman vs lancer
            Assert.assertEquals(FightStatus.DRAW, axeMan.attack(lancer));
            Assert.assertEquals(13, lancer.getHp());
            Assert.assertEquals(5, axeMan.getHp());

            // Damage test axeman vs axeman
            Assert.assertEquals(FightStatus.LOST, axeMan.attack(axeMan2));
            Assert.assertEquals(15, axeMan2.getHp());
            Assert.assertEquals(-5, axeMan.getHp());

            // Damage test lancer vs knight
            Assert.assertEquals(FightStatus.DRAW, lancer.attack(knight));
            Assert.assertEquals(5, lancer.getHp());
            Assert.assertEquals(5, knight.getHp());

            // Damage test lancer vs lancer
            Assert.assertEquals(FightStatus.WIN, lancer2.attack(lancer));
            Assert.assertEquals(-5, lancer.getHp());
            Assert.assertEquals(25, lancer2.getHp());

            // Damage test knight vs knight
            Assert.assertEquals(FightStatus.LOST, knight.attack(knight2));
            Assert.assertEquals(15, knight2.getHp());
            Assert.assertEquals(-5, knight.getHp());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void counterAttackTest(){
        try{
            Agent axeMan = new AxeMan(25, 5, 5, 15, "");
            Agent knight = new Knight(25, 5, 5, 15, "");
            Agent lancer = new Lancer(25, 5, 5, 15, "");
            Agent bowman = new BowMan(35, 5, 5, 15, "");
            Agent bowman2 = new BowMan(25, 5, 5, 15, "");

            // Counter test bowman vs all 3 melee agents
            Assert.assertEquals(FightStatus.DRAW, bowman.attack(knight));
            Assert.assertEquals(FightStatus.DRAW, bowman.attack(lancer));
            Assert.assertEquals(FightStatus.DRAW, bowman.attack(axeMan));

            // Bowman should be full hp
            Assert.assertEquals(35, bowman.getHp());
            Assert.assertEquals(15, knight.getHp());
            Assert.assertEquals(15, axeMan.getHp());
            Assert.assertEquals(15, lancer.getHp());

            // The 2nd bowman should counter attack
            Assert.assertEquals(FightStatus.DRAW, bowman.attack(bowman2));
            Assert.assertEquals(25, bowman.getHp());
            Assert.assertEquals(15, bowman2.getHp());

            // Some other counter attack test
            Assert.assertEquals(FightStatus.DRAW, axeMan.attack(bowman));
            Assert.assertEquals(FightStatus.DRAW, lancer.attack(bowman));
            Assert.assertEquals(FightStatus.WIN, knight.attack(bowman));
        }
        catch (IllegalStateException e){
            System.out.println("Caught Illegal State Exception, test failed");
            Assert.assertTrue(true);
        }
    }

    @Test
    public void doubleAttackTest(){
        try{
            Agent axeMan = new AxeMan(25, 5, 10, 15, "");
            Agent knight = new Knight(25, 5, 5, 15, "");
            Agent lancer = new Lancer(25, 5, 5, 15, "");

            Agent bowman = new BowMan(25, 5, 5, 15, "");
            Agent bowman2 = new BowMan(25, 5, 10, 15, "");

            // Counter test bowman vs all 3 melee agents
            Assert.assertEquals(FightStatus.DRAW, bowman2.attack(knight));
            Assert.assertEquals(FightStatus.DRAW, bowman2.attack(lancer));
            Assert.assertEquals(FightStatus.DRAW, bowman2.attack(axeMan));
            Assert.assertEquals(FightStatus.DRAW, bowman.attack(bowman2));

            Assert.assertEquals(15, bowman2.getHp());

            // 1 attack
            Assert.assertEquals(15, axeMan.getHp());

            // 2 attacks
            Assert.assertEquals(5, knight.getHp());
            Assert.assertEquals(5, lancer.getHp());

            // 2 counter attacks
            Assert.assertEquals(5, bowman.getHp());
        }
        catch (IllegalStateException e){
            System.out.println("Caught Illegal State Exception, test failed");
            Assert.fail();
        }
    }

    @Test
    public void attackDeadAgentTest(){
        try{
            Agent axeMan = new AxeMan(25, 5, 10, 15, "");
            Agent knight = new Knight(0, 5, 5, 15, "");

            axeMan.attack(knight);

            System.out.println("Uncaught Illegal State Exception, test failed");
            Assert.fail();
        }
        catch (IllegalStateException e){
            System.out.println("Caught Illegal State Exception, test passed");
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testTerrain(){
        try{
            Agent axeMan = new AxeMan(25, 5, 10, 15, "");
            Agent knight = new Knight(25, 5, 5, 15, "");
            AbstractTerrain terrain = new Terrain(20, 20);

            terrain.agents[0][0] = axeMan;
            terrain.agents[19][19] = knight;

            // Test is free
            Assert.assertFalse(terrain.isFree(0, 0));
            Assert.assertFalse(terrain.isFree(19, 19));
            Assert.assertTrue(terrain.isFree(1, 1));

            // Test is out of bounds
            Assert.assertTrue(terrain.isOutOfBounds(-1, 0));
            Assert.assertTrue(terrain.isOutOfBounds(0, -1));
            Assert.assertTrue(terrain.isOutOfBounds(20, 0));
            Assert.assertTrue(terrain.isOutOfBounds(0, 20));

            // Test move to same place
            Assert.assertTrue(terrain.moveToSamePlace(axeMan, 0, 0));
            Assert.assertFalse(terrain.moveToSamePlace(axeMan, 0, 1));
            Assert.assertFalse(terrain.moveToSamePlace(axeMan, 1, 1));

            terrain.updateAgentCoordinates(axeMan, 18,19);

            Assert.assertEquals(18, axeMan.getPosX());
            Assert.assertEquals(19, axeMan.getPosY());

            terrain.agents[18][19] = null;

            System.out.println("Test Illegal state");
            terrain.updateAgentCoordinates(axeMan, 0, 0);
            System.out.println("Uncaught Illegal State Exception, test failed");
            Assert.fail();
        }
        catch (IllegalStateException e){
            System.out.println("Caught Illegal State Exception, test passed");
            Assert.assertTrue(true);
        }
    }
}
