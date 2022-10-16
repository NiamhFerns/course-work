public class MyFirstProgram {
    public static void main(String[] args) {
        int [] intArray = {3,4,6};

        int mean = intArray[0] + intArray[1] + intArray[2] / 3;
        System.out.println(mean);

        mean = (intArray[0] + intArray[1] + intArray[2]) / 3;
        System.out.println(mean);

        double meand = (intArray[0] + intArray[1] + intArray[2]) / 3;
        System.out.println(meand);

        meand = (intArray[0] + intArray[1] + intArray[2]) / 3.0;
        System.out.println(meand);

        meand = (intArray[0] + intArray[1] + intArray[2]) / intArray.length;
        System.out.println(meand);

        meand = ((double)intArray[0] + intArray[1] + intArray[2]) / intArray.length;
        System.out.println(meand);

        boolean answer = (int)(Math.random() * 10) % 2 == 0;
        System.out.println(answer);
    }
}
