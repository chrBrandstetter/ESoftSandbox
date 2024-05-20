package SquareUE;

public class MyProg {
    public static void main(String [] args){
        Square aSquare = new Square(10.5);

        Square bSquare = new Square(22.5);

        double umfang = aSquare.circumference();

        aSquare.resizeBy(10.0);

        Out.print(aSquare.a);

    }
}
