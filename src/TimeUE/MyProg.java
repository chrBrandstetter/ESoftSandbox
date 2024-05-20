package TimeUE;

public class MyProg {
    public static void main(String[] args) {
        Time time = new Time();
        Time anotherTime = new Time();

        time.setTime(12, 00);
        anotherTime.setTime(9,10);

        if(!(time.setTime(0, -1))){
            Out.println("Time could not be set");
        }

        Out.println("Time: " + time.asText());
        if(time.isTime(12,0))
            Out.println("Time is high noon");

        if(time.isTime(anotherTime))
            Out.println("Time is the same as anotherTime");

        time.elapse(1,30);
        anotherTime.elapse(90);

        Out.println("Time: " + time.asText());
        Out.println("Time: " + anotherTime.asText());


    }


}
