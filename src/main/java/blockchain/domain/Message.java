package blockchain.domain;

import java.security.*;

public class Message {
    int id;
    String sender;
    String recipient;
    int amount;
    byte[] signature;
    PublicKey publicKey;

    public Message(int id, String sender, String recipient, int amount, KeyPair keys) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        signMessage(keys.getPrivate());
        this.publicKey = keys.getPublic();
    }

    public int getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public int getAmount() {
        return amount;
    }

    public byte[] getSignature() {
        return signature;
    }

    private void signMessage(PrivateKey privateKey) {
        try {
            Signature rsa = Signature.getInstance("SHA1withRSA");
            rsa.initSign(privateKey);
            rsa.update((sender + recipient + amount).getBytes());
            signature = rsa.sign();
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verifySignature() {
        Signature sig = null;
        try {
            sig = Signature.getInstance("SHA1withRSA");
            sig.initVerify(publicKey);
            sig.update((sender + recipient + amount).getBytes());
            if (!sig.verify(signature))
                return false;
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    @Override
    public String toString() {
        return String.format("%s sent %d VC to %s", sender, amount, recipient);
    }
}
