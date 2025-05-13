import java.util.*;

public class SeparateChaining {
    private List<List<String>> table; //arraylist
    private int collisions = 0;  //counter
    private static final int BASE = 31;
    private static final long MOD = 1_000_000_009;


    public SeparateChaining(int size) {
        table = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {   // Initialize linked lists at each index
            table.add(new ArrayList<>());
        }
    }

    private int hash(String word) {
        word = word.toLowerCase();
        long hash = 0;
        // Calculate hash = (ASCII(s[0])*31^(n-1) +
        // ASCII(s[1])*31^(n-2) + ... + ASCII(s[n-1])) % MOD
        for (int i = 0; i < word.length(); i++) {
            hash = (hash * BASE + word.charAt(i)) % MOD;
        }
        return (int)(hash % table.size()); //casting  final modulo with table size to get index
    }

    public void insert(String word) {
        int index = hash(word);
        if (!table.get(index).isEmpty()) {
            collisions++;
        }
        table.get(index).add(word);

    }

    public void display() {
        System.out.println("\nHash Table Contents:");
        for (int i = 0; i < table.size(); i++) {
            List<String> block = table.get(i);// Print each block with its contents
            System.out.print("Index " + i + ": " + block);
            if (block.size() > 1) {// Mark block with collisions
                System.out.print(" ← Collision (" + block.size() + " words)");
            }
            System.out.println();
        }
        System.out.println("Total collisions: " + collisions);
    }

    public static void main(String[] args) {
        String[] words = {"cat", "dog", "act", "bird", "bird", "act"};

        Scanner scanner = new Scanner(System.in); // Get table size from user
        System.out.print("Enter table size: ");
        int size = scanner.nextInt();

        SeparateChaining hashTable = new SeparateChaining(size);

        for (String word : words) {
            hashTable.insert(word);
        }

        hashTable.display();
        scanner.close();
    }
}