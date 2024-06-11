package blockchain.messenger;

import blockchain.domain.Blockchain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MsgBotExecutor {
    private ExecutorService executor;
    private final List<String> botNames = List.of("Bob", "Alice", "ShoesShop", "FastFood");
    private Set<MsgBot> msgBotSet;
    private final Blockchain blockchain;

    public MsgBotExecutor(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public void activate () {
        executor = Executors.newFixedThreadPool(3);
        msgBotSet = new HashSet<>();
        botNames.forEach(name -> msgBotSet.add(new MsgBot(blockchain, name)));
        msgBotSet.forEach(executor::submit);
    }

    public void stop() {
        executor.shutdownNow();
        try {
            executor.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
