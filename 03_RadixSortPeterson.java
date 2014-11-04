import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Implementing a sorting algorithm
 *
 * 1. The program should run until the user types 'quit'.
 * 2. The program should read in a file of comma delimited integers if the user types
 *    in the filename from the command line (Example: INPUT.txt)
 * 3. The program should read in a comma delimited set of integers from the command line
 * 4. Implement the radix sort algorithm. Do it yourself. Do not copy from the web.
 * 5. Run radix sort on the array and measure the time to sort in milliseconds.
 *    Print out both the sorted array and time elapsed.
 *
 */
public class RadixSortPeterson {

    /**
     * @param args command line arguments (if any)
     */
    public static void main(String[] args) {

        /* Variable declarations */
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        String input;
        int[] int_array;
        int[] sorted_array;
        long startTime;
        long stopTime;
        long elapsedTime;

        // Prompt user for input
        System.out.println("------------------------------------------------------------");
        System.out.print("Enter a source filename or comma delimited list of integers to sort (or 'quit'):\n");

        // Save user input
        input = scanner.next();

		// Run until user input equals "quit"
        while(!input.equalsIgnoreCase("quit")) {

            /*
             * Convert the string of comma delimited integers into an array of integers
             * parsing which method of entry was utilized (filename/comma-delimited-integer-string)
             *
             * If an error is encountered: when reading the file or when parsing the comma delimited string of integers
             * an error is flagged (method returns -1) and handled below
             *
             */
            int_array = get_int_array(input);

            // If error did not occur when converting string to integer array:
            if(int_array[0] != -1) {
                // Start timer
                startTime = System.currentTimeMillis();

                // Sort array
                sorted_array = radix_sort_array(int_array);

                // Stop timer and calculate elapsed time in ms
                stopTime = System.currentTimeMillis();
                elapsedTime = stopTime - startTime;

                // Output results
                System.out.print("\nSorted array using Radix:\t");
                print_array(sorted_array);

                System.out.print("\nTime took in ms: ");
                System.out.println(elapsedTime + "\n");
            } else {
                // Error occurred when converting string to integer array
                System.out.println("\nERROR: Must enter a comma delimited string of integers or a filename (" +
                        "file must contain a comma delimited string of integers).\n");
            }

            // Prompt user for input
            System.out.println("------------------------------------------------------------");
            System.out.print("Enter a source filename or comma delimited list of integers to sort (or 'quit'):\n");

			// Save user input
            input = scanner.next();
        }
    }


    /*
     * Takes a given input string and checks if it is a filename (if contains ".") if it is a filename, the contents
     * of the file (which should be a comma delimited string of integers) are stored for processing. If it is not a
     * filename, it is assumed that input is a comma delimited int string. \
     * The comma delimited string is then parsed into an integer array.
     * If any errors are encountered, an error flag is returned and the user is prompted to provide valid input.
     *
     * @param input the input string provided
     *
     * @return array of integers (derived from given comma delimited string of integers)
     *
     */
    private static int[] get_int_array(String input) {
        String int_string;
        String[] string_array;
        int[] int_array;

        // If input contains ".", assume it is a filename, and try to get file contents
        if (input.contains(".")) {
            // Try to load comma delimited file of integers
            try {
                BufferedReader in = new BufferedReader(new FileReader(input));
                int_string = in.readLine();
                in.close();
            } catch (IOException e) {
                // File read error occured
                // Set error flag and return from method
                return new int[]{-1};
            }
        } else {
            // Else, input is assumed to be comma delimited list of numbers
            // Strip any whitespace
            int_string = input.replaceAll("\\s+","");
        }

        // Split the int_string into an array of strings, using comma as delimiter
        string_array = int_string.split(",");

        // Initialize an array of integers the same length as string array
        int_array = new int[string_array.length];

        // Convert each value of the string array into an integer, and store value in the integer array
        // If any value is not an integer, the first element of the array is assigned an error flag value
        // of negative one
        for(int i = 0; i < string_array.length; i++)
        {
            try {
                int_array[i] = Integer.parseInt(string_array[i]);
            } catch(NumberFormatException nfe) {
                return new int[]{-1};
            }
        }
        return int_array;
    }

    /*
     * Sorts a given integer array in ascending order via the Radix method
     *
     * @param int_array an unsorted array of integers
     *
     * @return the sorted integer array
     *
     */
    private static int[] radix_sort_array(int[] int_array) {
        int divisor = 1;
        int element;
        int sorted_array_index = 0;

        /* Runs through the process as many times as there are
         * elements in the unsorted array
         *
         */
        for(int i = 0; i < int_array.length; i++) {
            // Initialize placeholder ArrayList
            List<List<Integer>> placeholder = new ArrayList<List<Integer>>(10);

            // Add rows 0 - 9 (one per digit), each row is an ArrayList of Integers
            for (int j = 0; j < 10; j++) {
                placeholder.add(new ArrayList<Integer>());
            }

            /*
             * 1. Cycle through the unsorted array,
             * 2. Determine ArrayList index (will be 0 - 9) using modulo
             * 3. Place element in placeholder @ index derived from above mentioned calculation
             *
             * Note: divisor increases by x10 each rotation through the outer loop,
             * thus checking each digit of each number starting from low to high significance
             *
             */
            for (int j = 0; j < int_array.length; j++) {
                element = int_array[j];
                int index = (element / divisor) % 10;
                placeholder.get(index).add(element);
            }

            /*
             * Move through the placeholder ArrayList starting from the beginning and add digits back to
             * the int_array (when the highest significance digit is reached the array will be sorted)
             *
             */
            for (int j = 0; j < 10; j++) {
                for (int n: placeholder.get(j)) {
                    int_array[sorted_array_index] = n;
                    sorted_array_index++;
                }
            }

            // Increment the divisor by x10 to move one step up in digit significance
            divisor *= 10;
            // Reset the int_array index to zero
            sorted_array_index = 0;
        }
        return int_array;
    }

    /*
     * Print the given integer array, space delimited
     *
     * @param int_array the integer array to print
     *
     */
    private static void print_array(int[] int_array) {
        for(int i = 0; i < int_array.length; i++) {
            System.out.print(int_array[i] + " ");
        }
    }
}