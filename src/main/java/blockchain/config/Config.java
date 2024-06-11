package blockchain.config;

import java.util.List;

public class Config {
    private Config() {}

    public static int BLOCK_TARGET_CREATION_SECONDS = 5;
    public static int INITIAL_LEADING_ZEROS = 0;
    public static int MAX_MESSAGES_PER_BLOCK = 20;

    public static int MIN_MSG_INTERVAL_MILLISECONDS = 10;
    public static int MAX_MSG_INTERVAL_MILLISECONDS = 1000;

    public static List<String> MESSAGE_BOTS = List.of("Bob", "Alice", "ShoesShop", "FastFood");

    public static int BLOCKCHAIN_SIZE = 15;
    public static int MAX_MINERS = Runtime.getRuntime().availableProcessors() - MESSAGE_BOTS.size();

    public static int MINING_SUCCESS_AWARD = 100;

}
