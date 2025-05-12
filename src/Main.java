import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Test with table size 20
        QuadraticProbingHashTable hashTable = new QuadraticProbingHashTable(20);

        // Insert test words
        String[] testWords = {
                "algorithm", "binary", "cache", "data", "exception",
                "function", "graph", "hash", "index", "java",
                "kernel", "loop", "memory", "node", "object",
                "pointer", "queue", "stack", "tree", "variable"
        };

        for (String word : testWords) {
            hashTable.insert(word);
        }

        // Display results
        hashTable.display();
    }
    }

