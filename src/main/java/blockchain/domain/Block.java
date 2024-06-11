package blockchain.domain;

import java.security.MessageDigest;
import java.util.List;
import java.util.stream.Collectors;

public class Block {
    private final int Id;
    private final long timestamp;
    private int magic;
    private final String previousHash;
    private String hash;
    private final List<Message> data;
    private final String minerId;
    private final int award;

    private long timeToMine;

    public Block(int Id, long timestamp, String previousHash, List<Message> data, String minerId, int award) {
        this.Id = Id;
        this.timestamp = timestamp;
        magic = 0;
        this.previousHash = previousHash;
        this.data = data;
        this.minerId = minerId;
        this.award = award;
        this.hash = applySha256();
        timeToMine = -1;
    }

    public int getId() {
        return Id;
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

    @Override
    public String toString() {
        return "Created by " + minerId + "\n" +
                minerId + " gets " + award + " VC\n" +
                "Id: " + Id + "\n" +
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

    public String applySha256() {
        StringBuilder dataToHash = new StringBuilder();
        dataToHash.append(Id);
        dataToHash.append(timestamp);
        dataToHash.append(magic);
        dataToHash.append(previousHash);
        if (data != null) {
            data.forEach(dataToHash::append);
        }
        dataToHash.append(minerId);
        dataToHash.append(award);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            /* Applies sha256 to our input */
            byte[] hash = digest.digest(dataToHash.toString().getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte elem : hash) {
                String hex = Integer.toHexString(0xff & elem);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
