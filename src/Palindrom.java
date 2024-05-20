class Palindrom {
    public static void main (String [] args) {

        Out.print("\nPalindrom");
        Out.print("\n=======");

        char[] text1 = {'D', 'I', 'E', 'N', 'S', 'T', 'M', 'A', 'N', 'N', 'A', 'M', 'T', 'S', 'N', 'E', 'I', 'D'};
        char[] text2 = {'H', 'A', 'N', 'N', 'A', 'H'};
        char[] text3 = {'S', 'A', 'I', 'P', 'P', 'U', 'A', 'K', 'I', 'V', 'I', 'K', 'A', 'U', 'P', 'P', 'I', 'A', 'S'};
        char[] text4 = {'N', 'I', 'X'};
        char[] text5 = {};

        char[] text = text3;

        Out.print("\nDer Text ");
        print(text);

        Out.print(" ist ");
        if (isPalindrom(text)) {
            Out.print("ein ");
        } else {
            Out.print("kein ");
        } // end if

        Out.print("Palindrom!");

        Out.print("\nEnd! ---\n");
        /* END PROGRAM */
    } // end main


    static boolean isPalindrom(char [] text) {
        boolean palindrom = false;
        if(text == null || text.length == 0) return false;
        for(int i = 0; i < text.length; i++ ){
            if(text[i] == text[text.length-(1+i)]){
                palindrom = true;
            }
            else {
                return false;
            }
        }
        return palindrom;
    } // end isPalindrom


    static void print(char [] t) {
        if (t == null) Out.print("--- kein Text ---");							// no array given
        for (int i = 0; i < t.length; i++) {
            Out.print(t[i]);
        } // end for
    } // end print(char [])

} // end class Palindrom