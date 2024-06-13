# File Type Analyzer - Command-line Tool demonstrating Sub-string searching

## About

Here's the link to the project: https://hyperskill.org/projects/50

Check out my profile: https://hyperskill.org/profile/500961738

All documentation retrieved from https://hyperskill.org/projects/50, provided by JetBrains Academy.

Blockchains are data structures where blocks are inseparably connected. What makes blockchains so special is the security level they offer due to the way they are constructed. Blockchains are unhackable, so it makes perfect sense why cryptocurrency makes use of this technology. This project develops a multithreaded microcosm where virtual <u>**miners**</u> earn cryptocurrency ("virtual coins") and exchange messages and <u>**transactions**</u> using <u>**blockchain**</u>. They are accompanied by various <u>**messengers**</u> that also exchange encrypted messages and transactions of virtual coins with each other and with the miners through the blockchain.

### Description
Today, the most common application of blockchains is cryptocurrencies. A cryptocurrency’s blockchain contains a list of transactions: everyone can see the transactions but no one is able to change them. In addition, no one can send a transaction as another person; this is possible using digital signatures. You have actually implemented all of this functionality in the previous stages.

A miner who finds the magic number and creates a new block should be awarded some virtual money, for example, 100 virtual coins. This can be remembered in the blockchain if the block stores information about the miner who created this block. Of course, this message also should be proved, so the miner adds this information to the blockchain before starting a search for a magic number.

After that, a miner can spend these 100 virtual coins by giving them to someone else. In the real world, he can buy things and pay for them using these virtual coins instead of real money. These virtual coins go to the company that sells the things, and the company can pay salaries with these virtual coins. The circulation of these coins starts here and suddenly the virtual coins become more popular than real money!

To check how many coins a person has, the program checks all transactions to and from a user, assuming that the person started with some initial virtual coins (e.g. 100VC). The transaction should be rejected when the person tries to spend more money than he has at the moment.

### Algorithm
So, the algorithm of adding messages is the following:

- The first block doesn't contain any messages. Miners should find the magic number of this block.
- During the search of the current block, the users can send the messages to the blockchain. The blockchain should keep them in a list until miners find a magic number and a new block would be created.
- All messages must include a signature as a special part of the message. Users can generate a pair of keys: a public key and a private key. The message should be signed with a private key. Then anyone can verify that the message and the signature pair is valid using a public key.
- After the creation of the new block, all new messages that were sent during the creation should be included in the new block and deleted from the list.
- After that, no more changes should be made to this block apart from the magic number. All new messages should be included in a list for the next block. The algorithm repeats from step 2.


### Example output
```text
Block:
Created by: miner9
miner9 gets 100 VC
Id: 1
Timestamp: 1539866031047
Magic number: 76384756
Hash of the previous block:
0
Hash of the block:
1d12cbbb5bfa278734285d261051f5484807120032cf6adcca5b9a3dbf0e7bb3
Block data:
No transactions
Block was generating for 0 seconds
N was increased to 1
```
```text
Block:
Created by: miner7
miner7 gets 100 VC
Id: 2
Timestamp: 1539866031062
Magic number: 92347234
Hash of the previous block:
1d12cbbb5bfa278734285d261051f5484807120032cf6adcca5b9a3dbf0e7bb3
Hash of the block:
04a6735424357bf9af5a1467f8335e9427af714c0fb138595226d53beca5a05e
Block data:
miner9 sent 30 VC to miner1
miner9 sent 30 VC to miner2
miner9 sent 30 VC to Nick
Block was generating for 0 seconds
N was increased to 2
```
```text
Block:
Created by: miner1
miner1 gets 100 VC
Id: 3
Timestamp: 1539866031063
Magic number: 42374628
Hash of the previous block:
04a6735424357bf9af5a1467f8335e9427af714c0fb138595226d53beca5a05e
Hash of the block:
0061924d48d5ce30e97cfc4297f3a40bc94dfac6af42d7bf366d236007c0b9d3
Block data:
miner9 sent 10 VC to Bob
miner7 sent 10 VC to Alice
Nick sent 1 VC to ShoesShop
Nick sent 2 VC to FastFood
Nick sent 15 VC to CarShop
miner7 sent 90 VC to CarShop
Block was generating for 0 seconds
N was increased to 3
```
```text
Block:
Created by miner2
miner2 gets 100 VC
Id: 4
Timestamp: 1539866256729
Magic number: 45382978
Hash of the previous block:
0061924d48d5ce30e97cfc4297f3a40bc94dfac6af42d7bf366d236007c0b9d3
Hash of the block:
000856a20d767fbbc38e0569354400c1750381100984a09a5d8b1cdf09b0bab6
Block data:
CarShop sent 10 VC to Worker1
CarShop sent 10 VC to Worker2
CarShop sent 10 VC to Worker3
CarShop sent 30 VC to Director1
CarShop sent 45 VC to CarPartsShop
Bob sent 5 VC to GamingShop
Alice sent 5 VC to BeautyShop
Block was generating for 5 seconds
N was increased to 4
```
```text
... (another 10 blocks, so the output contains 15 blocks)
```
