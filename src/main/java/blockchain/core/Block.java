package blockchain.core;

import java.util.List;
import java.util.stream.Collectors;

public class Block {
    private final int id;
    private final long timestamp;
    private int magic;
    private final String previousHash;
    private String hash;
    private final List<Message> data;
    private final String minerId;
    private final int award;

    private long timeToMine;

    public Block(int id, long timestamp, String previousHash, List<Message> data, String minerId, int award) {
        this.id = id;
        this.timestamp = timestamp;
        magic = 0;
        this.previousHash = previousHash;
        this.data = data;
        this.minerId = minerId;
        this.award = award;
    }

    public int getId() {
        return id;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public long getTimeToMine() {
        return timeToMine;
    }

    public void setTimeToMine(long timeToMine) {
        this.timeToMine = timeToMine;
    }

    public String getMinerId() {
        return minerId;
    }

    public int getAward() {
        return award;
    }

    public List<Message> getData() {
        return data;
    }

    public String toHashInputExclMagic() {
        return id + timestamp + previousHash +
                (data.isEmpty() ? "" :
                        data.stream()
                                .map(Message::toString)
                                .collect(Collectors.joining(",")))
                + minerId + award;
    }

    @Override
    public String toString() {
        return "Created by " + minerId + "\n" +
                minerId + " gets " + award + " VC\n" +
                "Id: " + id + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Magic number: " + magic + "\n" +
                "Hash of the previous block:" + "\n" +
                previousHash + "\n" +
                "Hash of the block:" + "\n" +
                hash + "\n" +
                (data.isEmpty() ? "Block data: no transactions" : "Block data:" + "\n" +
                        data.stream()
                                .map(Message::toString)
                                .collect(Collectors.joining("\n")));
    }
}
