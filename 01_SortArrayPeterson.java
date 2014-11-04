import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * The goal here is to observe the time elapsed in
 * milliseconds of the Java Array.sort() method
 *
 */
public class SortArrayPeterson {

    /**
     * @param args
     */
    public static void main(String[] args) {

        String input = "";

		/* 1. Load comma delimited file of integers (Name: INPUT.TXT) */
        try {
            BufferedReader in = new BufferedReader(new FileReader("input.txt"));
            input = in.readLine();
            in.close();
        } catch (IOException e) {
            System.out.println("File Read Error");
            System.exit(1);
        }

        int[] array = buildArray(input);
		
		/* 2. Print it out */
        printArray(array);
		
		/* 3. Start timer in ms */
        long startTime = System.currentTimeMillis();
		
		/* 4. Call sort of any kind */
        Arrays.sort(array);
		
		/* 5. Stop timer */
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
			  
		/* 6. Print sorted array */
        printArray(array);
		
		/* 7. Print time in ms */
        System.out.println("Time to sort:\t\t" + elapsedTime + " ms");

        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("Start Time (ms):\t" + startTime);
        System.out.println("Stop Time (ms):\t\t" + stopTime);
    }

    /* Prints an integer array, space delimited */
    private static void printArray(int[] array) {
        for(int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
        System.out.println();
    }

    /* Builds an int array from a comma delimited string */
    private static int[] buildArray(String arrayAsString) {
        String[] stringArray = arrayAsString.split(",");
        int[] intArray = new int[stringArray.length];

        for(int i = 0; i < stringArray.length; i++)
        {
            intArray[i] = Integer.parseInt(stringArray[i]);
        }

        return intArray;
    }
}