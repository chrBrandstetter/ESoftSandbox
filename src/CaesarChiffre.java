public class CaesarChiffre {
    public static void main(String[] args) {
        //Out.print("Schluessel: ");
        char schluessel = 'd';
        Out.print("Unchiffrierter Text: ");
        char c = In.read();
        while(c != '\n'){
            c += 3;
            if (c > 'z'){
                c -= 26;
            }
            Out.print(c + " ");

            c = In.read();
        }

    }
}
