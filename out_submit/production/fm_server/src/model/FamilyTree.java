package model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * a data structure to hold all the nodes in the family tree.
 * this is a class to keep track of everything, not actually a "tree"
 */
@Data
public class FamilyTree {

    private Node root;
    // these arrays are kept for easy insertion into the database
    private List<Node> allNodes;
    private List<Person> allPeople;
    private List<Event> allEvents;

    public FamilyTree() {
        this.root = null;
        this.allNodes = new ArrayList<>();
        this.allPeople = new ArrayList<>();
        this.allEvents = new ArrayList<>();
    }

    public void addNode(Node n) {
        allNodes.add(n);
        allPeople.add(n.getPerson());
        for (Event e : n.getEvents().values()) {
            allEvents.add(e);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("root: ");
        sb.append(root.toString());
        sb.append("\n");
        sb.append("all nodes: \n");
        for (Node n : allNodes) {
            sb.append(n.toString());
            sb.append("\n");
        }
        sb.append("all people: \n");
        for (Person p : allPeople) {
            sb.append(p.toString());
            sb.append("\n");
        }
        sb.append("all events: \n");
        for (Event e : allEvents) {
            sb.append(e.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

}
