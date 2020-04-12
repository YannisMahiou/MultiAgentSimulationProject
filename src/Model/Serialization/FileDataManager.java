package Model.Serialization;

import Model.Agent.Agent;

import java.io.*;
import java.util.LinkedList;

/**
 * DataManager used to serialize/deserialize in a file-way
 */
public class FileDataManager implements DataManager {

    public FileDataManager() {}

    /**
     * Serialize the agents
     * @param fileName file name where to save
     * @param agents agents to serialize
     */
    @Override
    public void saveAgents(String fileName, LinkedList<Agent> agents) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(agents);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserialize the agents
     * @param fileName file where to load
     * @return the agents deserialize
     */
    @Override
    public LinkedList<Agent> loadAgents(String fileName) {
        LinkedList<Agent> agents = null;

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            agents = (LinkedList<Agent>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return agents;
    }
}
