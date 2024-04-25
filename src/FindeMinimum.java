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
        Out.print(findMin(numbers));
    }

    static int findMin(int[] numbers){
        int min = 10;
        if(numbers == null || numbers.length == 0 ){
            Out.print("Array is empty");
        }else{
            for(int i = 0; i < numbers.length; i++){
                if(numbers[i] < min){
                    min = numbers[i];
                }
            }
            return min;
        }
        return -1;
    }
}
