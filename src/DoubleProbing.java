import java.util.ArrayList;

public class DoubleProbing {

    private ArrayList<String> hashTable;
    private final int tableSize = 52;

    public DoubleProbing() {
        hashTable = new ArrayList<>(tableSize);
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
    }

   

}
