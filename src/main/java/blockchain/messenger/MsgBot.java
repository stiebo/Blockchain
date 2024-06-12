package blockchain.messenger;

import blockchain.config.Config;
import blockchain.core.Blockchain;
import blockchain.trader.Trader;

import java.util.concurrent.ThreadLocalRandom;

public class MsgBot extends Trader implements Runnable {
    private final int minMsgInterval_ms = Config.MIN_MSG_INTERVAL_MILLISECONDS;
    private final int maxMsgInterval_ms = Config.MAX_MSG_INTERVAL_MILLISECONDS;

    public MsgBot(Blockchain blockchain, String name) {
        super(blockchain, name);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                donateVCs();
                Thread.sleep(ThreadLocalRandom.current().nextInt(
                        maxMsgInterval_ms - minMsgInterval_ms) + minMsgInterval_ms);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
