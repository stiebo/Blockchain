package blockchain.miner;

import blockchain.config.Config;
import blockchain.domain.Block;
import blockchain.domain.Blockchain;
import blockchain.security.SHA256Hash;
import blockchain.trader.Trader;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class Miner extends Trader implements Callable<Block> {
    private final String prefixString;
    private final Random sharedRandom;
    private final Block newBlock;

    public Miner (Blockchain blockchain, String name, Random sharedRandom) {
        super(blockchain, name);
        this.prefixString = "0".repeat(blockchain.getN());
        this.sharedRandom = sharedRandom;

        newBlock = new Block(
                blockchain.isEmpty() ? 1 : blockchain.getLast().getId()+1,
                new Date().getTime(),
                blockchain.isEmpty() ? "0" : blockchain.getLast().getHash(),
                blockchain.getData(),
                name,
                Config.MINING_SUCCESS_AWARD
        );
    }

    @Override
    public Block call() {
        donateVCs();
        long startTime = System.currentTimeMillis();
        String hash;
        String input = newBlock.toHashInputExclMagic();
        do  {
            newBlock.setMagic(sharedRandom.nextInt(Integer.MAX_VALUE));
            hash = SHA256Hash.applySha256(input + newBlock.getMagic());
        } while (!(hash.startsWith(prefixString)) && !Thread.interrupted());
        newBlock.setHash(hash);
        newBlock.setTimeToMine(System.currentTimeMillis() - startTime);
        return newBlock;
    }
}
