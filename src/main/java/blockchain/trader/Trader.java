package blockchain.trader;

import blockchain.core.Blockchain;
import blockchain.core.Message;
import blockchain.security.KeyGen;

import java.security.KeyPair;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Trader {
    protected final Blockchain blockchain;
    protected final String name;
    private final KeyPair keys;

    public Trader (Blockchain blockchain, String name) {
        this.blockchain = blockchain;
        this.name = name;
        this.keys = KeyGen.generateKeyPair(1024);
        Nodes.registerNode(this.name);
    }

    protected synchronized void donateVCs()  {
        int myBalance = blockchain.getBalance(name, true);
        if (myBalance > 0) {
            int id = blockchain.getNextMsgId();
            List<String> tempList = Nodes.getNodes().stream()
                    .filter(str -> !str.equals(name))
                    .toList();
            if (!tempList.isEmpty()) {
                String luckyOne = tempList.get(ThreadLocalRandom.current().nextInt(tempList.size()-1)+1);
                int amount = ThreadLocalRandom.current().nextInt(myBalance/3)+1;
                blockchain.addMsg(new Message(id, name, luckyOne, amount, keys));
            }
        }
    }
}
