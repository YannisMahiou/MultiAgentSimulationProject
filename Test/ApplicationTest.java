import Model.Agent.Agent;
import Model.Agent.AgentType;
import Model.Factory.AgentFactory;
import Model.Factory.IFactory;
import Model.Game.Game;
import org.junit.Assert;
import org.junit.Test;

public class ApplicationTest {

    IFactory factory = new AgentFactory();

    @Test
    public void testSmtg()
    {
        try{
            Agent agent = factory.create(AgentType.AXEMAN, null, 0);
            Assert.assertEquals(agent, factory.create(AgentType.AXEMAN, null, 0));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test2mgl()
    {
        // pour executer les tests, tu fait click droit sur la classe et tu lance tout
    }
}
