package blockchain.miner;

import blockchain.domain.Block;
import blockchain.domain.Blockchain;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Coordinator {
    private Blockchain blockchain;
    private ExecutorService executor;
    private final int maxMiners;

    public Coordinator(Blockchain blockchain) {
        this.blockchain = blockchain;
        this.maxMiners = Runtime.getRuntime().availableProcessors() - 4;
    }

    public void run() {
        while (blockchain.getSize() < 15) {
            executor = Executors.newFixedThreadPool(maxMiners);
            ArrayList<Miner> miners = IntStream.range(0, maxMiners)
                    .mapToObj(i -> new Miner(blockchain, "miner" + Integer.toString(i+1)))
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
