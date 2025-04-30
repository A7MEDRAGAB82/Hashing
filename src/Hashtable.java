public interface Hashtable {
    void insert(String word);          // Insert word
    boolean contains(String word);     // Check if word exists
    int getCollisions();               // Get collision count
    String printTableStructure();      // New: Returns the visual structure
}
