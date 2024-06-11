package blockchain.miner;

import blockchain.config.Config;
import blockchain.domain.Block;
import blockchain.domain.Blockchain;
import blockchain.trader.Trader;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Callable;

public class Miner extends Trader implements Callable<Block> {
    private final ArrayList<Block> currBlockchain;
    private final int N;
    private final Random random;

    public Miner (Blockchain blockchain, String name) {
        super(blockchain, name);
        this.currBlockchain = blockchain.getBlockchain();
        this.N = blockchain.getN();
        this.random = new Random();
    }

    @Override
    public Block call() {
        Block newBlock = new Block(
                currBlockchain.isEmpty() ? 1 : currBlockchain.getLast().getId()+1,
                new Date().getTime(),
                currBlockchain.isEmpty() ? "0" : currBlockchain.getLast().getHash(),
                blockchain.getData(),
                this.name,
                Config.MINING_SUCCESS_AWARD
        );
        donateVCs();
        long startTime = System.currentTimeMillis();
        String prefixString = "0".repeat(N);
        while (!(newBlock.getHash().substring(0, N).equals(prefixString)) &&
                !Thread.interrupted())  {
            newBlock.setMagic(random.nextInt(Integer.MAX_VALUE));
            newBlock.setHash(newBlock.applySha256());
        }
        newBlock.setTimeToMine(System.currentTimeMillis() - startTime);
        return newBlock;
    }
}
