package blockchain.messenger;

import blockchain.config.Config;
import blockchain.core.Blockchain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MsgBotController {
    private ExecutorService executor;
    private final List<String> botNames = Config.MESSAGE_BOTS;
    private Set<MsgBot> msgBotSet;
    private final Blockchain blockchain;

    public MsgBotController(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public void activate () {
        executor = Executors.newFixedThreadPool(botNames.size());
        msgBotSet = new HashSet<>();
        botNames.forEach(name -> msgBotSet.add(new MsgBot(blockchain, name)));
        msgBotSet.forEach(executor::submit);
    }

    public void stop() {
        executor.shutdownNow();
        try {
            if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                System.err.println("MsgBot-Executor did not terminate within the specified timeout.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
