public class FindeMinimum {
    public static void main(String[] args) {

        //int[] numbers = new int[]{2, 4, 6, 7, 2, 9, 1};
        //int[] numbers = null;
        //int[] numbers = {};
        //int[] numbers = {-1};
        //int[] numbers = {1};
        //int[] numbers = {1,-2,1};
        //int[] numbers = {2,1,-1};
        int[] numbers = {3,1,2};

        int min = findMin(numbers);						// returns -1 in case no minimum can be identified
        if (min < 0) Out.print("No minimum found!");
        else Out.print("Minimum: " + min);
    }

    static int findMin(int[] numbers){
        if(numbers == null || numbers.length == 0 ){
            return -1;
        }else{
            int min = numbers[0];
            for(int i = 0; i < numbers.length; i++){
                if(numbers[i] < min){
                    min = numbers[i];
                }
            }
            return min;
        }
    }
}
