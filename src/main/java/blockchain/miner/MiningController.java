package blockchain.miner;

import blockchain.config.Config;
import blockchain.core.Block;
import blockchain.core.Blockchain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MiningController {
    private final Blockchain blockchain;
    private final int maxMiners;
    private final Random sharedRandom;

    public MiningController(Blockchain blockchain) {
        this.blockchain = blockchain;
        this.maxMiners = Config.MAX_MINERS;
        this.sharedRandom = new Random();
    }

    public void run() {
        while (blockchain.size() < Config.BLOCKCHAIN_SIZE) {
            ExecutorService executor = Executors.newFixedThreadPool(maxMiners);
            List<Miner> miners = IntStream.range(0, maxMiners)
                    .mapToObj(i -> new Miner(blockchain, "miner" + (i + 1), sharedRandom))
                    .collect(Collectors.toCollection(ArrayList::new));
            Block newBlock = null;
            try {
                newBlock = executor.invokeAny(miners);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            blockchain.addNewBlock(newBlock);
            executor.shutdownNow();
            try {
                executor.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (!executor.isTerminated()) System.out.println("executor still not terminated");
        }
    }
}
