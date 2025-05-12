import java.util.*;


class LinearProbing implements Hashtable {
    private final ArrayList<String> hashTable;
    private static final int b = 31;
    private static final int m = 1000000009;
    private final Map<String , List<Integer> > collisionMap;
    private final List<String> collisionTimes;
    private int totalCollisions;



    public LinearProbing(int size) {
        this.hashTable = new ArrayList<>(Collections.nCopies(size, null));
        this.collisionMap = new HashMap<>();
        this.collisionTimes = new ArrayList<>();
        this.totalCollisions = 0;
    }


    public void linearProbing(ArrayList<String> array) {
        long hashFnRes;

        for (String word : array) {

            if (!hashTable.contains(null)) {
                System.out.println("Cannot insert " + word + " because hash table is full");
                continue;
            }

            String lCWord = word.toLowerCase();
            int n = word.length();
            long sumAscii = 0;

            int i = 0;
            for (char letter : lCWord.toCharArray()) {
                sumAscii += (long) (letter * Math.pow(b, (n - 1 - i)));
                i++;
            }

            hashFnRes = (sumAscii % m) % hashTable.size();
            int c = 0;
            if (hashTable.get((int) hashFnRes) == null) {
                hashTable.set((int) hashFnRes, word);
                this.collisionTimes.add(word + ": " + c);
            } else {
                while (hashTable.get((int) hashFnRes) != null) {
                    collisionMap.putIfAbsent(word, new ArrayList<>());
                    collisionMap.get(word).add((int) hashFnRes);
                    c++;
                    totalCollisions++;
                    hashFnRes = (hashFnRes + 1) % hashTable.size();
                }
                this.collisionTimes.add(word + ": " + c);
                hashTable.set((int) hashFnRes, word);
            }
        }

    }

    @Override
    public String printTableStructure() {
        System.out.println("Total collisions: " + totalCollisions);
        System.out.println("Total collisions for every word: " + collisionTimes);
        System.out.println("Collisions indices for every word:");

        for (Map.Entry<String, List<Integer> > i : collisionMap.entrySet()) {
            System.out.println(i.getKey() + " at indices: " + i.getValue());
        }

        String s = "";
        for (int i = 0; i < hashTable.size(); i++) {
            if (hashTable.get(i) != null) {
                s += i + ": " + hashTable.get(i) + "\n";
            }
        }
        return s;
    }
}