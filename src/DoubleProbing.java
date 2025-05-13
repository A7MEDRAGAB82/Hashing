import java.util.ArrayList;
import java.util.Scanner;

public class DoubleProbing {

    private ArrayList<String> hashTable;
    private final int tableSize;
    private int collision;

    public DoubleProbing(int tableSize) {  
        this.tableSize = tableSize;

        hashTable = new ArrayList<>(tableSize);
        collision = 0;
        for (int i = 0; i < tableSize; i++) {
            hashTable.add(null);
        }
    }

    private int hashFunctionH(String key) {
        int b = 31;
        int m = 1000000009;
        int n = key.length();
        long hash = 0;

        for (int j = 0; j < n; j++) {
            int charValue = (int) key.charAt(j);
            long power = 1;

            // Compute b^(n-1-j) % m using modular exponentiation
            for (int p = 0; p < n - 1 - j; p++) {
                power = (power * b) % m;
            }

            hash = (hash + (charValue * power) % m) % m;
        }

        return (int) hash;
    }

    public void doubleHashInsert(String key) {
        key = key.toLowerCase();

        int H = hashFunctionH(key);
        int h1 = H % tableSize;
        int h2 = 97 - (H % 97);

        for (int j = 0; j < tableSize; j++) {
            int newIndex = (h1 + j * h2) % tableSize;
            if (hashTable.get(newIndex) == null) {
                hashTable.set(newIndex, key);
                return;
            } else {
                System.out.printf("Collision at index %d for \"%s\"\n", newIndex, key);
                collision++;
            }
        }
        System.out.printf("Failed to insert \"%s\" (Table is full)\n", key);
    }

    public void printHashTable() {
        System.out.println("\nHash Table Contents:");
        for (int i = 0; i < tableSize; i++) {
            String word = hashTable.get(i);
            if (word != null) {
                System.out.printf("%d : %s\n", i, word);
            }
        }
        System.out.printf("Total Collisions: %d\n", collision);
        System.out.printf("Table Size: %d\n", tableSize);

    }

    public static void main(String[] args) {
        DoubleProbing hashTable = new DoubleProbing(52);
        hashTable.doubleHashInsert("Hello");
        hashTable.doubleHashInsert("World");
        hashTable.doubleHashInsert("Test");
        hashTable.doubleHashInsert("Hash");
        hashTable.printHashTable();
    }

}
