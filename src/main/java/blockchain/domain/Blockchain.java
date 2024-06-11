package blockchain.domain;

import blockchain.config.Config;
import blockchain.security.SHA256Hash;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private final List<Block> blockchain;
    private int N;
    private final int tTarget_sec;
    private final int maxMessagesPerBlock;
    private List<Message> activeMessages;
    private List<Message> nextMessages;
    private int nextMsgId;
    private int minMsgId;

    public Blockchain() {
        blockchain = new ArrayList<>();
        N = Config.INITIAL_LEADING_ZEROS;
        tTarget_sec = Config.BLOCK_TARGET_CREATION_SECONDS;
        maxMessagesPerBlock = Config.MAX_MESSAGES_PER_BLOCK;
        activeMessages = new ArrayList<>();
        nextMessages = new ArrayList<>();
        nextMsgId = 1;
        minMsgId = 1;
    }

    public void addNewBlock(Block block) {
        if (isValidBlock(block)) {
            blockchain.add(block);
            activateNewData();
            System.out.println();
            System.out.println("Block:");
            System.out.println(block);
            System.out.printf("Block was generating for %d seconds%n", block.getTimeToMine() / 1000);
            adjustN(block.getTimeToMine());
        }
        else {
            System.out.println("Invalid block will not be added!");
        }
    }

    private boolean isValidBlock(Block block) {
        if (!block.getPreviousHash().equals(blockchain.isEmpty() ?
                "0" : blockchain.get(blockchain.size() - 1).getHash()))
            return false;
        // check that every message has an identifier greater than the maximum identifier of the previous block
        if (!blockchain.isEmpty()) {
            int maxIdPrevBlock = blockchain.get(blockchain.size()-1).getData().stream()
                    .mapToInt(Message::getId)
                    .max()
                    .orElse(0);
            if (block.getData().stream().anyMatch(msg -> msg.getId() <= maxIdPrevBlock))
                return false;
        }
        if (block.getData().stream().allMatch(Message::verifySignature)) {
            return SHA256Hash.applySha256(block.toHashInputExclMagic()+block.getMagic()).equals(block.getHash());

        }
        return false;
    }

    public ArrayList<Block> getBlockchain() {
        return new ArrayList<Block>(blockchain);
    }

    public int getN() {
        return N;
    }

    private void adjustN(long timeToMine) {
        if ((N > 0) && (timeToMine / 1000 > tTarget_sec + 1)) {
            N--;
            System.out.println("N was decreased to " + N);
        } else if (timeToMine / 1000 < tTarget_sec - 1) {
            N++;
            System.out.println(" N was increased to " + N);
        } else System.out.println("N stays the same");
    }

    public int getSize() {
        return blockchain.size();
    }

    public void addMsg(Message msg) {
        if (nextMessages.size() < maxMessagesPerBlock && msg.verifySignature() && verifyMsgIdInNextRange(msg)  &&
                getBalance(msg.getSender(),true)>=msg.getAmount()) {
            nextMessages.add(msg);
        }
    }

    public boolean verifyMsgIdInNextRange(Message msg) {
        if (nextMessages.stream().anyMatch(currMsg -> currMsg.getId() == msg.getId()))
            return false;
        return ((msg.getId() >= minMsgId) && (msg.getId() < nextMsgId));
    }

    public List<Message> getData() {
        return activeMessages;
    }

    private void activateNewData() {
        activeMessages = nextMessages;
        minMsgId = nextMsgId;
        nextMessages = new ArrayList<>();
    }

    public int getNextMsgId() {
        return nextMsgId++;
    }

    public int getBalance(String node, boolean includeMsgQueues) {
        int balance = 100;
        for (Block block : blockchain) {
            if (block.getMinerId().equals(node)){
                balance += block.getAward();
            }
            for (Message msg : block.getData()) {
                if (msg.getSender().equals(node)) {
                    balance -= msg.getAmount();
                }
                if (msg.getRecipient().equals(node)) {
                    balance += msg.getAmount();
                }
            }
        }
        if (includeMsgQueues) {
            for (Message msg : activeMessages) {
                if (msg.getSender().equals(node)) {
                    balance -= msg.getAmount();
                }
                if (msg.getRecipient().equals(node)) {
                    balance += msg.getAmount();
                }
            }
            for (Message msg : nextMessages) {
                if (msg.getSender().equals(node)) {
                    balance -= msg.getAmount();
                }
                if (msg.getRecipient().equals(node)) {
                    balance += msg.getAmount();
                }
            }
        }
        if (balance < 0) {
            throw new IllegalArgumentException("Balance below zero");
        }
        return balance;
    }
}
