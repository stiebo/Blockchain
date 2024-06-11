package blockchain.messenger;

import blockchain.domain.Blockchain;
import blockchain.trader.Trader;

import java.util.concurrent.ThreadLocalRandom;

public class MsgBot extends Trader implements Runnable {
    private final int minMsgIntervall_ms = 500;
    private final int maxMsgIntervall_ms = 1000;

    public MsgBot(Blockchain blockchain, String name) {
        super(blockchain, name);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                donateVCs();
                Thread.sleep(ThreadLocalRandom.current().nextInt(
                        maxMsgIntervall_ms - minMsgIntervall_ms) + minMsgIntervall_ms);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
