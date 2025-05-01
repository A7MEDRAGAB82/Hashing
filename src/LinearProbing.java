import java.util.ArrayList;
import java.util.Collections;


public class LinearProbing implements HashTable {

    private ArrayList<Character> hashTable;

    public ArrayList<Character> linearProbing(ArrayList<Character> array) {
        hashTable = new ArrayList<>(Collections.nCopies(array.size(), null));
        int size = hashTable.size();
        int hashFnRes;

        for (int i = 0; i < array.size(); i++) {
            char iVal = array.get(i);
            hashFnRes = iVal % size;
            if (hashTable.get(hashFnRes) == null)
                hashTable.set(hashFnRes, iVal);
            else {
                while (hashTable.get(hashFnRes) != null) {
                    hashFnRes = (hashFnRes + 1) % size;
                }
                hashTable.set(hashFnRes, iVal);
            }
        }
        return hashTable;
    }

    @Override
    public String printTableStructure() {
        String s = "";
          for (int i = 0; i < hashTable.size(); i++) {
            if (hashTable.get(i) != null) {
                s += i + ": " + hashTable.get(i) + "\n";
            }
          }
            return s;
        }
    }

