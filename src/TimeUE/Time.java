package TimeUE;

public class Time {
    int min;
    int h;

    Time(){
        min = 0;
        h = 0;
    }

    boolean setTime(int newH, int newM){
        if(newH > 24 || newM > 60 || newM < 0 || newH < 0){
            return false;
        }
        else{
            this.h = newH;
            this.min = newM;
            return true;
        }
    }

    String asText(){
        return h + " : " + min;
    }

    boolean isTime(int h, int m ){
        return h == 12 && m == 0;
    }

    boolean isTime(Time anotherTime){
        if(this.h == anotherTime.h && this.min == anotherTime.min){
            return true;
        }
        return false;
    }

    void elapse(int h, int m){
        this.h += h;
        this.min += m;
    }

    void elapse(int min){
        if(min >= 60){
            int h = 1;
            min = min % 60;
            this.h += h;
            this.min += min;
        }
        else {
            this.h += h;
            this.min += min;
        }


    }


}
