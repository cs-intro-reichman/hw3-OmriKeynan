// Computes the periodical payment necessary to pay a given loan.
public class LoanCalc {
	
	static double epsilon = 0.001;  // Approximation accuracy
	static int iterationCounter;    // Number of iterations 
	
	// Gets the loan data and computes the periodical payment.
    // Expects to get three command-line arguments: loan amount (double),
    // interest rate (double, as a percentage), and number of payments (int).  
	public static void main(String[] args) {		
		// Gets the loan data
		double loan = Double.parseDouble(args[0]);
		double rate = Double.parseDouble(args[1]);
		int n = Integer.parseInt(args[2]);
		System.out.println("Loan = " + loan + ", interest rate = " + rate + "%, periods = " + n);

		// Computes the periodical payment using brute force search
		System.out.print("\nPeriodical payment, using brute force: ");
		System.out.println((int) bruteForceSolver(loan, rate, n, epsilon));
		System.out.println("number of iterations: " + iterationCounter);

		// Computes the periodical payment using bisection search
		System.out.print("\nPeriodical payment, using bi-section search: ");
		System.out.println((int) bisectionSolver(loan, rate, n, epsilon));
		System.out.println("number of iterations: " + iterationCounter);
	}

	// Computes the ending balance of a loan, given the loan amount, the periodical
	// interest rate (as a percentage), the number of periods (n), and the periodical payment.
	private static double endBalance(double loan, double rate, int n, double payment) {	
		double result = loan; 
		for (int i = 0; i < n; i++){
			result = ((result - payment) * (1 + rate/100));
		}
		return result+1;
	}

	
	// Uses sequential search to compute an approximation of the periodical payment
	// that will bring the ending balance of a loan close to 0.
	// Given: the sum of the loan, the periodical interest rate (as a percentage),
	// the number of periods (n), and epsilon, the approximation's accuracy
	// Side effect: modifies the class variable iterationCounter.
    public static double bruteForceSolver(double loan, double rate, int n, double epsilon) {
		double payment = loan / n; 
		double result = endBalance(loan, rate, n, payment);
		iterationCounter = 0;

		while (result > 0) {
			payment += epsilon; 
			iterationCounter++;
			result = endBalance(loan, rate, n, payment);
		}
		return payment; 
    }
    
    // Uses bisection search to compute an approximation of the periodical payment 
	// that will bring the ending balance of a loan close to 0.
	// Given: the sum of the loan, the periodical interest rate (as a percentage),
	// the number of periods (n), and epsilon, the approximation's accuracy
	// Side effect: modifies the class variable iterationCounter.
    public static double bisectionSolver(double loan, double rate, int n, double epsilon) {  

		double upper_bound_pay = loan;
		double lower_bound_pay = loan/n;
		iterationCounter = 0;
		double mid_pay = 0.0;

		while ((upper_bound_pay - lower_bound_pay) > epsilon) {
			mid_pay = (upper_bound_pay+lower_bound_pay)/2;
			iterationCounter++;

			double mid_balance = endBalance(loan, rate, n, mid_pay);			
			double low_balance = endBalance(loan, rate, n, lower_bound_pay);

			if ((mid_balance * low_balance) > 0){ 

				lower_bound_pay = mid_pay;

			}
			else{

				upper_bound_pay = mid_pay;
			}

		}
		return mid_pay;
    }
}