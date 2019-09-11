/**
 *
 * @author Jacynta Ryan
 */
public class QuadraticProbingHashMap<K, V> extends SimpleHashMap<K, V> {

    // DON'T CHANGE THIS - Bad things might happen
    @SuppressWarnings("unchecked")
    public QuadraticProbingHashMap(int initCapacity, double loadFactor) {
        size = 0;
        this.loadFactor = loadFactor;
        if (initCapacity < MIN_TABLE_SIZE) {
            capacity = MIN_TABLE_SIZE;
        } else {
            capacity = nextSuitablePrime(initCapacity);
        }
        table = (MapEntry<K, V>[]) new MapEntry[capacity];
    }

    @Override
    public V get(K key) {
        if (key == null)
            throw new IllegalArgumentException("Keys cannot be null");
        
        probeCount = 0;
        int index;
        int i = 1;
        int sign;
        int initialIndex = hash(key);
        for (index = initialIndex; table[index] != null && i <= capacity; i++){
            probeCount++;
            if((table[index].key).equals(key)){
                return table[index].value;
            }
            // -1 to the power of an odd number gives a negative value and 
            // an even gives a positive value and thats used to alternate 
            // the hash keys sign 
            sign = (int) Math.pow(-1,probeCount);
            //index is equal to the initial index and the amount of 
            //collisions squared which will alternate as a positive or negative int
            //mod the capacity keeps the index in the bounds of the array
            index = (initialIndex + (sign) * (i * i)) % capacity;
            //if index is less that 0 add capacity to avoid index out of bounds exception
            if (index < 0)
            {
                index = index + capacity;
            }
        }
        probeCount++;
        return null;
    }

    @Override
    public V put(K key, V value) {
        V oldValue = null;

        if (key == null) {
            throw new IllegalArgumentException("Keys cannot be null");
        }

        // Check if table is too full first and resize if needed
        if (size >= capacity * loadFactor) {
            resize(nextSuitablePrime(2 * capacity));
        }

        probeCount = 0;
        int index;
        int i = 1;
        int sign;
        int initialIndex = hash(key);
        // If key is already present - update value and return old value
        for (index = initialIndex; table[index] != null; i++) {
            
            if ((table[index].key).equals(key)) {
                oldValue = table[index].value;
                table[index].value = value;
                return oldValue;
            }
            sign = (int) Math.pow(-1,probeCount);
            index = (initialIndex + (sign) * (i * i)) % capacity;
            if (index < 0)
            {
                index = index + capacity;
            }
            probeCount++;
        }

        // Otherwise add new entry and increment size
        probeCount++;
        table[index] = new MapEntry<K, V>(key, value);
        size++;

        return oldValue;
    }

    @Override
    public V remove(K key) {
        if (key == null)
            throw new IllegalArgumentException("Keys cannot be null");
        
        probeCount = 0;
        int index;
        int i = 1;
        int sign;
        int initialIndex = hash(key);
        for (index = initialIndex; table[index] != null && i <= capacity; i++){
            probeCount++;
            if((table[index].key).equals(key)){
                //when key snd value are found set index to null to remove/delete key and value
                V value = table[index].value;
                table[index] = null;
                return value;
            }
            sign = (int) Math.pow(-1,probeCount);
            index = (initialIndex + (sign) * (i * i)) % capacity;
            if (index < 0)
            {
                index = index + capacity;
            }
        }
        probeCount++;
        return null;
    }
    
    
}
