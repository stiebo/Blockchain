package blockchain;

import blockchain.core.Blockchain;
import blockchain.messenger.MsgBotController;
import blockchain.miner.MiningController;

public class Main {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        MiningController miningController = new MiningController(blockchain);
        MsgBotController msgBotController = new MsgBotController(blockchain);
        msgBotController.activate();
        miningController.run();
        msgBotController.stop();
        System.out.println("Good bye!");
    }
}
