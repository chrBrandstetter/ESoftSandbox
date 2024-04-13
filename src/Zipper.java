public class Zipper {
    public static void main(String[] args) {
        Out.print("Text: ");
        char c = In.read();
        char prevoius = '\n';
        int counter = 0;

        while(c != '\n'){
            if (c == prevoius){
                counter ++;
            }
            else{
                Out.print(counter);
                Out.print(c);
                counter = 1;
            }
            prevoius = c;
            c = In.read();
        }

    }
}
