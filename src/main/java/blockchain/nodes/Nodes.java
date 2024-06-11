package blockchain.nodes;

import java.util.HashSet;
import java.util.Set;

public class Nodes {
    private static final Set<String> nodes = new HashSet<>();

    public static Set<String> getNodes() {
        return nodes;
    }

    public static void registerNode (String node) {
        nodes.add(node);
    }
}
