class Shift {
    public static void main (String [] args) {

        Out.print("\nShift");
        Out.print("\n=====");

        int[] numbers = {11,22,33,44,55,66};

        printNumbers(numbers); // prints the array of INTs

        Out.print("\n   do shift ...");
        shift(numbers, 2, 9999);			//fill with 9999 if necessary


        printNumbers(numbers); 				// prints the array of INTs



        Out.print("\nEnd! ---\n");
        /* END PROGRAM */
    } // end main

    //Musterlösungen anschauen!
    static void shift(int [] numbers, int offset, int value) {
        for(int i = 0; i < numbers.length ; i++){
            numbers[numbers.length -(1+i)] = 0;
        }

    } // end shift(int[], int, int)


    static void printNumbers(int [] n) {
        if (n == null) {
            Out.print("--- keine Zahlen ---");							// no array given
            return;
        }

        for (int i = 0; i < n.length; i++) {
            Out.print(n[i]+" ");
        }
    } // end printNumbers(int [])



} // end class Shift