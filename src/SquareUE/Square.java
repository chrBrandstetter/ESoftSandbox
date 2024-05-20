package SquareUE;

public class Square {
    double a;

    // Konstruktor
    Square(double leng){
        if(leng < 0){
            leng *= -1;
        }
        a = leng;
    }

    // Method
    double circumference(){
        return (4 * a);
    }

    double area(){
        return (this.a * this.a); //this spricht die property a der Class an
    }

    void resizeBy(double p){
        this.a = this.a * (1 + (p/100));
    }

    boolean isLarger(Square otherSquare){
        return this.a > otherSquare.a;
    }

}
