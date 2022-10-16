public class MonthlyRainfall {
	public static int monthlyRain() {
		double n = Math.random();
		n *= 100;
		return (int)n;
	}

	public static void main(String[] args) {
		int [] monthlyRain = new int[12];
		for (int i = 0; i < monthlyRain.length; ++i) {
			monthlyRain[i] = monthlyRain();
		}

		String [] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		System.out.println("Average monthly rainfall:");
		int i = 0;
		for (int month : monthlyRain) {
			System.out.println(months[i] + ": " + month + "mm.");
			++i;
		}
	}
}
