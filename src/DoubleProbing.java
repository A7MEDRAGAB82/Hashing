import java.util.ArrayList;

public class DoubleProbing {

    private ArrayList<Character> hashTable;
    public DoubleProbing() {
        hashTable = new ArrayList<>(52);
        for (int i = 0; i < 52; i++) {
            hashTable.add(null);
        }
    }


        public void doubleHashInsert(String key) {
            int tableSize = hashTable.size();

            if (tableSize == 0) {
                System.out.println("Hash table is not initialized");
                return;
            }

            for (int i = 0; i < key.length(); i++) {
                char currentChar = key.charAt(i);
                int ascii = (int) currentChar;

                int h1 = ascii % tableSize;
                int h2 = 47 - (ascii % 47);

                for (int j = 0; j < tableSize; j++) {
                    int newIndex = (h1 + j * h2) % tableSize;

                    if (hashTable.get(newIndex) == null) {
                        hashTable.set(newIndex, currentChar);
                        break;
                    }
                }
            }
        }
        public  void printHashTable(ArrayList<Character> hashTable) {
            System.out.println("Hash Table Contents:");
            for (int i = 0; i < hashTable.size(); i++) {
                Character value = hashTable.get(i);
                if (value != null) {
                    System.out.printf(" %d : %c \n", i, value);
                }
            }
        }


    }

