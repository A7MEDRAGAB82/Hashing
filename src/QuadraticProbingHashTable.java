import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class QuadraticProbingHashTable {
    // Constants as specified in the requirements
    private static final int b = 31;
    private static final int m = 1_000_000_009; // 10^9 + 9 (largest prime number as per requirements)
    private static final int c1 = 1; // As specified in requirements
    private static final int c2 = 1; // As specified in requirements
    private final Map<String, Integer> wordCollisions = new HashMap<>();

    private final String[] table;
    private int collisionCount;
    private final int tableSize;

    public QuadraticProbingHashTable(int tableSize) {
        this.tableSize = tableSize;
        this.table = new String[tableSize];
        this.collisionCount = 0;
    }

    // Hash function implementation as specified in requirements
    private int hashFunction(String word) {
        word = word.toLowerCase();
        int hash = 0;
        int n = word.length();

        for (int i = 0; i < n; i++) {
            int ascii = (int) word.charAt(i);
            hash = (hash + ascii * (int) Math.pow(b, n - 1 - i)) % m;
        }

        return Math.abs(hash);
    }

    // Get the primary hash value (h₁(s))
    private int getPrimaryHash(String word) {
        return hashFunction(word) % tableSize;
    }

    // Insert using quadratic probing exactly as specified:
    // Indexᵢ = (h₁(s) + c₁·i + c₂·i²) mod T
    public void insert(String word) {
        word = word.toLowerCase();
        int h1 = getPrimaryHash(word);
        int index = h1;
        int i = 0;
        int collisionsForThisWord = 0;


        // Try to find an empty slot using quadratic probing
        while (table[index] != null) {
            // Word already exists, no need to insert
            if (table[index].equals(word)) {
                wordCollisions.put(word, collisionsForThisWord);
                return;
            }

            // Collision detected, try next probe sequence
            collisionsForThisWord++;
            collisionCount++;
            i++;

            // Quadratic probing formula exactly as specified:
            // Indexᵢ = (h₁(s) + c₁·i + c₂·i²) mod T
            index = (h1 + c1 * i + c2 * i * i) % tableSize;

            // Ensure index is positive (Java's % can return negative)
            if (index < 0) {
                index += tableSize;
            }

            // If we've tried more positions than table size, table is likely full
            // or quadratic probing can't find an empty slot with current parameters
            if (i >= tableSize) {
                throw new RuntimeException("Failed to insert: hash table may be full or probing sequence failed to find empty slot");
            }
        }

        // Found an empty slot, insert the word
        table[index] = word;
        wordCollisions.put(word, collisionsForThisWord);
    }

    // Display the hash table contents
    public void display() {
        System.out.println("\nQuadratic Probing Hash Table Contents:");
        System.out.println("+------------+----------------------+----------------+");
        System.out.println("| Table Index | Stored Word          | Original Hash  |");
        System.out.println("+------------+----------------------+----------------+");

        for (int i = 0; i < tableSize; i++) {
            if (table[i] != null) {
                System.out.printf("| %10d | %-20s | %14d |\n",
                        i, table[i], getPrimaryHash(table[i]));
            }
        }

        System.out.println("+------------+----------------------+----------------+");
        System.out.println("Total Collisions: " + collisionCount);
        System.out.println("Table Size: " + tableSize);
     //   System.out.printf("Load Factor: %.2f%%\n", calculateLoadFactor() * 100);
    }

    private double calculateLoadFactor() {
        int count = 0;
        for (String word : table) {
            if (word != null) count++;
        }
        return (double) count / tableSize;
    }

    // New method to read words from file
    private static List<String> readWordsFromFile(String filename) throws IOException {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Handle both space-separated and line-separated words
                String[] lineWords = line.split("\\s+");
                for (String word : lineWords) {
                    if (!word.isEmpty()) {
                        words.add(word.toLowerCase()); // Preprocess to lowercase as required
                    }
                }
            }
        }
        return words;
    }

    public void displayWordCollisions() {
        System.out.println("\nCollisions per Word:");
        System.out.println("+----------------------+------------+");
        System.out.println("| Word                 | Collisions |");
        System.out.println("+----------------------+------------+");

        wordCollisions.forEach((word, count) ->
                System.out.printf("| %-20s | %10d |\n", word, count)
        );

        System.out.println("+----------------------+------------+");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get table size from user
        System.out.print("Enter the size of the hash table (preferably a prime number): ");
        int tableSize = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Create hash table instance with user-specified size
        QuadraticProbingHashTable hashTable = new QuadraticProbingHashTable(tableSize);

        try {
            // Read words from file (change "words.txt" to your actual file path)
            System.out.print("Enter the filename containing words (e.g., words.txt): ");
            String filename = scanner.nextLine();
            List<String> words = readWordsFromFile(filename);

            // Insert all words from the file
            for (String word : words) {
                hashTable.insert(word);
            }

            // Display results
            hashTable.display();

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();


        }
        finally {
            scanner.close();
        }

        // ... [file reading code]

        // After inserting all words
        hashTable.displayWordCollisions(); // Show per-word collisions

    }

}