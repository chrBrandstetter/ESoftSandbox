public class Pruefung {
    public static void main(String[] args) {
        char[] list = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};
        if(exchange(list, 3)){
            Out.print("richtig");
        }
    }

    static boolean exchange(char [] list, int pos){
        for(int i = 0; i < list.length; i++){
            if(i < pos){
                char buffer = list[i];
                list[i] = list[(list.length -1) - i];
                list[(list.length -1) - i] = buffer;
            }
        }
        return true;
    }
}
