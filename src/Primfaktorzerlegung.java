public class Primfaktorzerlegung {
    public static void main(String[] args) {
        Out.print("\nZahl: ");
        int zahl = In.readInt();

        int prim = 2;
        int counter = 0;

        if (zahl <= 1) {
            Out.print(zahl + " hat keine Primfaktoren!");
        } else {
            Out.print(zahl + " = ");
            while (zahl > 1) {
                if ((zahl % prim) == 0) {
                    counter ++;
                    if (counter == 1){
                        Out.print(prim);
                    }
                    zahl = zahl / prim;
                } else {
                    if(counter > 0){
                        Out.print("^" + counter + " * ");
                        counter = 0;
                        prim ++;
                    }
                }
            }
            Out.print("^" + counter);
        }
    }
}
