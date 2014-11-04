import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Scanner;

/**
 * The goal here is to compare the runtime of computing a value by using an 
 * accessor equation versus running through the sequence.
 *
 * 1. The program should run until the user types 'quit'
 * 2. Input the nth position in the Fibonacci sequence
 *    a. Calculate the value of the nth position using the following equation:
 *       Fn = 1/sqrt(5) * [ ((1 + sqrt(5)) / 2) - ((1 - sqrt(5) / 2) ]
 *    b. Calculate the value of the nth position by looping until the nth position
 *       and calculate incrementally.
 *
 * What is the runtime of both methods in Big O? Test it by using 1000 and 10000 as inputs
 * and compare your results.
 *
 * Example input:
 *     Enter the nth position in the Fibonacci sequence (or 'quit'):
 *
 * Example output:
 *     Value using the equation:
 *     Time took in ms:
 *
 *     Value using recursion:
 *     Time took in ms:
 */
public class FibonacciPeterson {

    /**
     * @param args command line arguments (if any)
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String input;
        int n;
        long startTime;
        long stopTime;
        long elapsedTime;

		/* run until user input equals "quit" */
        while(true) {
			
			/* initialize n to loop control value */
            n = -1;
			
			/* Prompt user for input */
            System.out.println("------------------------------------------------------------");
            System.out.print("Enter the nth position in the Fibonacci sequence (or 'quit'): ");
            System.out.println();
			
			/* Save user input */
            input = scanner.next();
			
			/* Validate input, ensure it's an integer > 0
			 * and check to see if user quits */
            while(n < 0) {
                try {
                    n = Integer.parseInt(input);
                } catch(NumberFormatException nfe) {
                    n = -1;
                }
                if(n < 0) {
                    if(input.equalsIgnoreCase("quit"))
                        System.exit(0);
                    System.out.println("Input must be an integer > 0.  Enter the nth position in the Fibonaci sequence" +
                            " (or 'quit'): ");
                    input = scanner.next();
                }
            }
			
			/*
			* Calc using equation
			*/
			
			/* Start timer */
            startTime = System.currentTimeMillis();
			
			/* Output results */
            System.out.print("\nValue using the equation:\t");
            System.out.println(fibWithEquation(n));

            /* Stop timer and calculate elapsed time in ms */
            stopTime = System.currentTimeMillis();
            elapsedTime = stopTime - startTime;

            System.out.print("Time took in ms: ");
            System.out.println(elapsedTime);
            System.out.println();

            /*
            * Calc using loop
            */

			/* Start timer */
            startTime = System.currentTimeMillis();

			/* Output results */
            System.out.print("Value using loop:\t\t\t");
            System.out.println(fibWithLoop(n));

            /* Stop timer and calculate elapsed time in ms */
            stopTime = System.currentTimeMillis();
            elapsedTime = stopTime - startTime;

            System.out.print("Time took in ms: ");
            System.out.println(elapsedTime);
            System.out.println();

			/* Calc using recursion
			
			/* Start timer *
            startTime = System.currentTimeMillis();
			
			/* Calculate result *
            result = calcWithRecursion(n);
			
			/* Stop timer and calculate elapsed time in ms *
            stopTime = System.currentTimeMillis();
            elapsedTime = stopTime - startTime;

            System.out.print("Value using recursion:\t\t");
            System.out.println(result);
            System.out.print("Time took in ms: ");
            System.out.println(elapsedTime);
            */
        }
    }

    /*
    * Calculates the nth fibonacci number using equation
    *
    * Time complexity depends on implementation of Math.sqrt & BigInteger.pow
    *
    * @param n the position of the fibonacci sequence to calculate
    *
    */
    private static BigInteger fibWithEquation(int n) {
        double root_five = Math.sqrt(5);
        BigDecimal a = new BigDecimal((1.0 / root_five), MathContext.UNLIMITED);
        BigDecimal b = new BigDecimal((1.0 + root_five) / 2.0, MathContext.UNLIMITED);
        b = b.pow(n);
        BigDecimal c = new BigDecimal((1.0 - root_five) / 2.0, MathContext.UNLIMITED);
        c = c.pow(n);
        return a.multiply(b.subtract(c)).setScale(0, RoundingMode.HALF_UP).toBigInteger();
    }

    /*
    * Calculate the nth fibonacci number using loop
    *
    * Time complexity O(n)
    *
    * @param n the position of the fibonacci sequence to calculate
    *
     */
    private static BigInteger fibWithLoop(int n) {
        BigInteger result = BigInteger.valueOf(1);
        if(n == 0)
            return BigInteger.valueOf(0);
        else if(n == 1)
            return BigInteger.valueOf(1);
        else {
            BigInteger a = BigInteger.valueOf(0);
            BigInteger b = BigInteger.valueOf(1);
            for (int i = 1; i < n; i++) {
                result = a.add(b);
                a = b;
                b = result;
            }
        }
        return result;
    }

    /*
     * Calculates the nth fibonacci number using equation (LESS PRECISE)
     *
     * @param n the position of the fibonacci sequence to calculate
     *
    private static long OLDfibWithEquation(int n) {
        return Math.round((1/Math.sqrt(5))*(Math.pow(((1+Math.sqrt(5))/2),n)-Math.pow(((1-Math.sqrt(5))/2),n)));
    }

    /* 
	* Calculates the nth fibonacci number using recursion
	* 
	* @param n the position of the fibonacci sequence to calculate
	*
    private static BigInteger fibWithRecursion(int n) {
        if(n == 0)
            return BigInteger.valueOf(0);
        else if(n == 1)
            return BigInteger.valueOf(1);
        else
            return fibWithRecursion(n - 1).add(fibWithRecursion(n - 2));
    }
    */
}
