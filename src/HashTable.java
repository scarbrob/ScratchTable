import java.util.*;

/**
 * This class is a HashTable object that is able to store many arbitrary values,
 * indexed by an arbitrary key type.
 * 
 * @author Ben Scarbrough
 * @Version 1.0
 *
 * @param <K>
 *            The generic value for the key.
 * @param <V>
 *            The generic value for the value.
 */
class HashTable<K, V> {

	private List<SequentialSearchSymbolTable<K, V>> table; // A private list filled with sequentialSearchSymbolTable's.
	private final int capacity; // The capacity of the list "table".

	/**
	 * This is a private nested SequentialSearchSymbolTable object that holds a set
	 * of key-value pairs.
	 * 
	 * @author Ben Scarbrough
	 * @Version 1.0
	 *
	 * @param <K>
	 *            The generic value for the key.
	 * @param <V>
	 *            The generic value for the value.
	 */
	@SuppressWarnings("hiding")
	private class SequentialSearchSymbolTable<K, V> {

		ArrayList<K> keySet;
		ArrayList<V> valueSet;
		private int size;

		/**
		 * The constructor for SequentialSearchSymbolTable.
		 */
		private SequentialSearchSymbolTable() {
			keySet = new ArrayList<>();
			valueSet = new ArrayList<>();
			size = 0;
		}

		/**
		 * A method with deletes all key-value pairs from the SSST.
		 */
		private void clear() {
			keySet.clear();
			valueSet.clear();
			size = 0;
		}

		/**
		 * A method which removes a key and its corresponding value. If the key isn't in
		 * the SSST, it does nothing and returns false. Otherwise it deletes it and
		 * returns true.
		 * 
		 * @param key
		 *            The key to delete.
		 * @return True or False.
		 */
		private boolean delete(K key) {
			if (!keySet.contains(key)) {
				return false;
			} else {
				int index = keySet.indexOf(key);
				keySet.set(index, keySet.get(size - 1));
				valueSet.set(index, valueSet.get(size - 1));
				keySet.set(size - 1, null);
				valueSet.set(size - 1, null);
				size--;
				return true;
			}
		}

		/**
		 * A method which returns the value to which the key maps. If the key is not
		 * contained in the SSST, it returns null.
		 * 
		 * @param key
		 *            The key to get the value of.
		 * @return The value of the key or null.
		 */
		private V get(K key) {
			if (keySet.contains(key)) {
				return valueSet.get(keySet.indexOf(key));
			}
			return null;
		}

		/**
		 * A method which returns the number of key-value pairs there are in the SSST.
		 * 
		 * @return The size.
		 */
		private int getSize() {
			return size;
		}

		/**
		 * A method which puts the key-value pair into the object. If the key is already
		 * mapped to something else the old value is replaced with the new one.
		 * 
		 * @param key
		 *            The key's value.
		 * @param value
		 *            The value's value.
		 */
		private void put(K key, V value) {
			if (key == null || value == null)
				return;
			else if (keySet.contains(key)) {
				valueSet.set(keySet.indexOf(key), value);
			} else {
				keySet.add(key);
				valueSet.add(keySet.indexOf(key), value);
				size++;
			}
		}
	}

	/**
	 * A constructor for HashTable, which makes a brand new, empty HashTable, with
	 * an internal array list of tableSize SequentialSearchSymbolTable's.
	 * 
	 * @param tableSize
	 *            The number of SequentialSearchSymbolTable's to be put in the list.
	 */
	public HashTable(int tableSize) {
		table = new ArrayList<>(tableSize);
		capacity = tableSize;
		for (int i = 0; i < tableSize; i++) {
			table.add(new SequentialSearchSymbolTable<K, V>());
		}
	}

	/**
	 * A method which deletes all key-value pairs from the HashTable.
	 */
	public void clear() {
		for (int i = 0; i < table.size(); i++) {
			table.get(i).clear();
		}
	}

	/**
	 * A method which removes a key and its value. If the key isn't in the
	 * HashTable, do nothing and return false. If it is, delete it and return true.
	 * 
	 * @param key
	 *            The key/value pair to delete.
	 * @return True or False.
	 */
	public boolean delete(K key) {
		return table.get(calcIndex(key)).delete(key);

	}

	/**
	 * A method which returns the value to which the key maps. If the key is not
	 * contained in the HashTable, return null.
	 * 
	 * @param key
	 *            The key to search for.
	 * @return The value that the key maps to.
	 */
	public V get(K key) {
		return table.get(calcIndex(key)).get(key);
	}

	/**
	 * A method which returns the total number of key-value pairs there are in the
	 * HashTable.
	 * 
	 * @return The total number of key-value pairs in the HashTable.
	 */
	public int getSize() {
		int total = 0;
		for (int i = 0; i < capacity; i++) {
			total += table.get(i).getSize();
		}
		return total;
	}

	/**
	 * A method which puts the given key-value pair into the Hash Table. If the key
	 * is already mapped to something else, the old value is replaced with the new
	 * one.
	 * 
	 * @param key
	 *            The key's value.
	 * @param value
	 *            The value's value.
	 */
	public void put(K key, V value) {
		table.get(calcIndex(key)).put(key, value);
	}

	/**
	 * A private method to calculate hash code.
	 * 
	 * @param key
	 *            The key to calculate hash code of.
	 * @return The index the key will be placed in.
	 */
	private int calcIndex(K key) {
		return Math.abs(key.hashCode() % capacity);
	}

	// Main Method
	public static void main(String[] args) {
		HashTable<String, Integer> table = new HashTable<>(10);
		HashTable<String, String> table2 = new HashTable<>(10);

		System.out.println("===========================");
		System.out.println("Testing a HashTable <String, Integer>.");
		System.out.println("===========================");
		table.put("hello", 24);
		table.put("goodbye", 48);
		table.put("pizza", 128);
		table.put("pie", 89);
		System.out.println("Added (hello, 24), (goodbye, 48), (pizza, 128), (pie, 89) to the table.");
		System.out.println("The size of table is (4): " + table.getSize());
		System.out.println("Get (hello) from the table and return (24): " + table.get("hello"));
		System.out.println("Get (goodbye) from the table and return (48): " + table.get("goodbye"));
		System.out.println("Get (pizza) from the table and return (128): " + table.get("pizza"));
		System.out.println("Get (pie) from the table and return (89): " + table.get("pie"));
		System.out.println("Delete (pizza) (true): " + table.delete("pizza"));
		System.out.println("Delete (alaska) (false); " + table.delete("alaska"));
		System.out.println("Replacing the value of (goodbye, 48) with (goodbye, 84).");
		table.put("goodbye", 84);
		System.out.println("Get (goodbye) from the table and return (84): " + table.get("goodbye"));
		table.clear();
		System.out.println("Clearing the table, the size is: " + table.getSize());
		System.out.println("Get (hello) from the table and return (null): " + table.get("hello"));
		System.out.println("Get (goodbye) from the table and return (null): " + table.get("goodbye"));
		System.out.println("Get (pie) from the table and return (null): " + table.get("pie"));

		System.out.println("\n");

		System.out.println("===========================");
		System.out.println("Testing a HashTable <String, String>.");
		System.out.println("===========================");
		table2.put("hello", "twentyFour");
		table2.put("goodbye", "fourtyEight");
		table2.put("pizza", "oneHundredTwentyEight");
		table2.put("pie", "eightyNine");
		System.out.println(
				"Added (hello, twentyFour), (goodbye, fourtyEight), (pizza, oneHundredTwentyEight), (pie, eightyNine) to the table.");
		System.out.println("The size of table is (4): " + table2.getSize());
		System.out.println("Get (hello) from the table and return (twentyFour): " + table2.get("hello"));
		System.out.println("Get (goodbye) from the table and return (fourtyEight): " + table2.get("goodbye"));
		System.out.println("Get (pizza) from the table and return (oneHundredTwentyEight): " + table2.get("pizza"));
		System.out.println("Get (pie) from the table and return (eightyNine): " + table2.get("pie"));
		System.out.println("Delete (pizza) (true): " + table2.delete("pizza"));
		System.out.println("Delete (alaska) (false); " + table2.delete("alaska"));
		System.out.println("Replacing the value of (goodbye, fourtyEight) with (goodbye, eightyFour).");
		table2.put("goodbye", "eightyFour");
		System.out.println("Get (goodbye) from the table and return (eightyFour): " + table2.get("goodbye"));
		table2.clear();
		System.out.println("Clearing the table, the size is: " + table2.getSize());
		System.out.println("Get (hello) from the table and return (null): " + table2.get("hello"));
		System.out.println("Get (goodbye) from the table and return (null): " + table2.get("goodbye"));
		System.out.println("Get (pie) from the table and return (null): " + table2.get("pie"));

	}

}
