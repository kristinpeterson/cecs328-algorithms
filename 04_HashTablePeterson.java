package me.kristinpeterson.cecs328.lab4;

import java.util.Scanner;

/**
 * This program prompts the user to enter a size of a hash table
 * that is subsequently created.  The user is then prompted to enter
 * the probing type (LINEAR or QUADRATIC) which they would like to use
 * for the newly created hash table. Then the user is prompted to
 * enter integer values to insert into the hash table using the chosen
 * probing type method when inserting, the user is prompted for new values
 * until 'stop' is entered or the hash table is full. Then the user is then
 * prompted to enter an integer value to search for within the hash table,
 * if the value is found it is returned with its associated index
 * in the hash table, if it is not found the user is informed. The
 * search prompt repeats until the user enters 'stop'. This entire
 * process runs until the user enters 'quit' when prompted to
 * enter the hash table size.
 *
 */
public class HashTablePeterson {

    private static Integer[] hashTable;
    private static String probeType;

    public static void main(String[] args) {

        /* Variable declarations */
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        String input ;
        int size, value;

        // Prompt user for hash table size
        System.out.println("------------------------------------------------------------");
        System.out.print("\nEnter the size of the hash table (or 'quit' to exit):\n");

        input = scanner.next();

        // Run until user enters "quit"
        while(!input.equalsIgnoreCase("quit")) {

            // Create hash table based on size provided by user
            size = Integer.parseInt(input);
            hashTable = new Integer[size];

            // Get user input for probing type
            do {
                System.out.print("\nEnter the probing type - LINEAR or QUADRATIC (or 'quit' to exit):\n");
                probeType = scanner.next();
            } while(!probeType.equalsIgnoreCase("linear") && !probeType.equalsIgnoreCase("quadratic"));

            // Insert sequence
            int insertCount = 0;
            System.out.println("\nEnter a value to be inserted into hash table (or 'stop' when finished):\n");
            input = scanner.next();
            while(!input.equalsIgnoreCase("stop")) {
                value = Integer.parseInt(input);
                if(insert(value)) {
                    print();
                    insertCount++;
                    if (insertCount == size) {
                        break;
                    }
                } else {
                    // This error occurs if there are too many collisions
                    // As the quadratic probing method can only handle size/2 collisions
                    System.out.println("\nERROR: Could not insert value '" + value
                            + "' as there are too many collisions\n");
                }
                System.out.println("\nEnter a value to be inserted into hash table (or 'stop' when finished):\n");
                input = scanner.next();
            }

            // Search sequence
            System.out.println("************************************************************");
            System.out.println("\nEnter value to search for in the hash table (or 'stop' when finished):\n");
            input = scanner.next();
            while(!input.equalsIgnoreCase("stop")) {
                value = Integer.parseInt(input);
                int searchIndex = search(value);
                if(searchIndex == -1) {
                    // Not found
                    System.out.println("\nValue " + value + " not found in hash table\n");
                } else {
                    // Found!
                    System.out.println("\nValue " + value + " found in index " + searchIndex + "\n");
                    printHighlightIndex(searchIndex);
                }
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("\nEnter value to search for in the hash table (or 'stop' when finished):\n");
                input = scanner.next();
            }

            // Prompt user for new hash table size
            System.out.println("------------------------------------------------------------");
            System.out.print("\nEnter the size of the hash table (or 'quit' to exit):\n");

            input = scanner.next();
        }
    }

    /**
     * Prints the hash table, replacing null (empty)
     * values with 'x'
     *
     */
    private static void print() {
        System.out.println();
        for(int i = 0; i < hashTable.length; i++) {
            if(hashTable[i] == null) {
                System.out.print("x | ");
            } else {
                System.out.print(hashTable[i] + " | ");
            }
        }
        System.out.println();
    }

    /**
     * Prints the hash table, highlighting the given
     * index value with brackets
     *
     */
    private static void printHighlightIndex(int index) {
        System.out.println();
        for(int i = 0; i < hashTable.length; i++) {
            if(i == index) {
                System.out.println("[" + hashTable[i] + "]" + " | ");
            } else {
                System.out.print(hashTable[i] + " | ");
            }
        }
        System.out.println();
    }

    /**
     * Inserts the given value into the hashTable
     * using either linear or quadratic probing
     * based on set probeType.
     *
     * @param value the value to insert into the hashTable
     * @return true if value was inserted successfully, false if not
     */
    private static boolean insert(int value) {
        int hashIndex;
        int adjust;
        for(int i = 0; i < hashTable.length; i++) {
            if(probeType.equalsIgnoreCase("linear")) {
                adjust = i;
            } else {
                adjust = (i * i);
            }
            hashIndex = (value + adjust) % hashTable.length;
            if(hashTable[hashIndex] == null) {
                hashTable[hashIndex] = value;
                return true;
            }
        }
        return false;
    }

    /**
     * Searches for a given value, either via linear or quadratic
     * probing (based on set probeType) and returns its index
     * from within hashTable.
     * <p>
     * If the value does not exist, returns -1
     *
     * @param value the value to search for
     * @return the index of the value being searched for, -1 if not found
     */
    private static int search(int value) {
        int hashIndex;
        int adjust;
        for(int i = 0; i < hashTable.length; i++) {
            if(probeType.equalsIgnoreCase("linear")) {
                adjust = i;
            } else {
                adjust = (i * i);
            }
            hashIndex = (value + adjust) % hashTable.length;
            if(hashTable[hashIndex] == value) {
                return hashIndex;
            }
        }
        return -1;
    }

}
