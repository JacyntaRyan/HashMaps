/**
 *
 * @author Jacynta Ryan
 */
public class DoubleHashingHashMap<K, V> extends SimpleHashMap<K, V>{
  
    @SuppressWarnings("unchecked")
    public DoubleHashingHashMap(int initCapacity, double loadFactor){
        size = 0;
        this.loadFactor = loadFactor;
        if (initCapacity < MIN_TABLE_SIZE){
            capacity = MIN_TABLE_SIZE;
        }
        else{
            capacity = nextSuitablePrime(initCapacity);
        }
        table = (MapEntry<K,V> []) new MapEntry[capacity];
    }

    @Override
    public V get(K key){
        if (key == null)
            throw new IllegalArgumentException("Keys cannot be null");
        
        probeCount = 0;
        int index;
        int i = 1;
        int initialIndex = hash(key);
        for (index = initialIndex; table[index] != null && i <= capacity; i++){
            probeCount++;
            if((table[index].key).equals(key)){
                return table[index].value;
            }
            //if collision index = index and hash 2 key - mod capacity so it isnt out of bounds
            index = (index + (hash2(key))) % capacity;
        }
        probeCount++;
        return null;
    }

    @Override
    public V put(K key, V value){
        V oldValue = null;
        
        if (key == null)
            throw new IllegalArgumentException("Keys cannot be null");
        
        // Check if table is too full first and resize if needed
        if (size >= capacity * loadFactor){
            resize(nextSuitablePrime(2 * capacity));
        }
        
        probeCount = 0;
        int index;
        int i = 1;
        int initialIndex = hash(key);
        
        // If key is already present - update value and return old value
        for (index = initialIndex; table[index] != null; i++) {
            
            probeCount++;
            if ((table[index].key).equals(key)) {
                oldValue = table[index].value;
                table[index].value = value;
                return oldValue;
            }
            
            index = (index + (hash2(key)))%capacity;
        }
        
        // Otherwise add new entry and increment size
        probeCount++;
        table[index] = new MapEntry<K,V>(key, value);
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
        int initialIndex = hash(key);
        for (index = initialIndex; table[index] != null && i <= capacity; i++){
            probeCount++;
            if((table[index].key).equals(key)){
                //when key snd value are found set index to null to remove/delete key and value
                V value = table[index].value;
                table[index] = null;
                return value;
            }
            index = (index + (hash2(key))) % capacity;
        }
        probeCount++;
        return null;
    }
    
    
    
    protected int hash2(K key){
        //second hash key cant be negative so use math.abs and mod capacity to make sure its not out of bounds
        int hash2 = 1 + Math.abs((key.hashCode() % (capacity - 1)));
        
        return hash2;
    }
}   