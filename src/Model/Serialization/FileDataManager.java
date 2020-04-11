package Model.Serialization;

import Model.Agent.Agent;

import java.io.*;
import java.util.LinkedList;

public class FileDataManager implements DataManager {

    public FileDataManager() {}

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
