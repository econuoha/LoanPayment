package javaStuff.LoanPaymentProject;
/** Emmanuel Onuoha
Due Date: 7/27/2020 
Loan Payment Schedule 
Write a Loan Payment Schedule*/

import javax.swing.JOptionPane; //import class needed for JOptionPane
import javax.swing.JScrollPane; //import class needed for JScrollPane
import javax.swing.JTextArea; //import class needed for JTextArea
import javax.swing.JFrame; //import class needed for JFrame
import java.awt.Dimension; //import class needed for setting Dimensions
import java.io.*; //import class needed for exporting to a File.

public class LoanPaymentScheduleOnuoha extends JFrame
{
    /**
    	 *
    	 */
    private static final long serialVersionUID = 4369147281334702998L;
    public static final int numOfMonthsInYear = 12; // Create final variable to avoid confusion.
public static void main(String[] args)throws IOException  
{
  double loanAmount, interestRate, monthlyInterestRate, monthlyPayment, totalPayment;
  int numOfYears,paymentNumber; 
  String tableValue; // Create variables needed for main method.
  
  loanAmount = getLoan(); 
  numOfYears = getYear();   
  interestRate = getRate();
  paymentNumber = numOfYears * numOfMonthsInYear; // Calculate number of months the payment lasts
  monthlyInterestRate = getMonthlyInterestRate(interestRate);
  monthlyPayment = calculateMonthlyPayment(loanAmount, monthlyInterestRate,paymentNumber);
  totalPayment = monthlyPayment * paymentNumber; // Calculate the total amount paid after interest
  tableValue = createTable(loanAmount, monthlyInterestRate, monthlyPayment,paymentNumber,numOfYears,interestRate,totalPayment); 
  chooseDisplay(tableValue, loanAmount, monthlyInterestRate, monthlyPayment, paymentNumber,numOfYears,interestRate,totalPayment);
 
}

/** This method will accept ALL the variables in the main method and then display it as a Display Box, File, or in the console
based on user choice */
public static void chooseDisplay (String createTableValue,double loanAmountValue, double monthlyInterestRateValue,double monthlyPaymentValue,int paymentNumberValue,
                              int numOfYearsValue, double interestRateValue, double totalPaymentValue)throws IOException 
{
String answer;
answer = JOptionPane.showInputDialog("Please select a display by inputing the character of your preferred display.\nFor example : \"C\" for console"+
                                    "\n[C]onsole\n[F]ile\n[D]ialog Box"); // Get the choice from the User
answer = answer.toLowerCase(); // To eliminate user errors    
                               
while(!answer.equals("c") && !answer.equals("d") && !answer.equals("f")) // Input Validation
{
answer = JOptionPane.showInputDialog("You have entered an incorrect character.\nPlease enter>\nC for console\nD for Dialog Box\nor F for File");
answer = answer.toLowerCase();
}             
                         
switch (answer) {     // Change the display based on User's choice. The table is recreated in order for correct spacing to be displayed
        case "c":    System.out.printf("Loan amount: $%,.2f\nLoan period %d year(s)\nAnnual Interest Rate:%.2f"
                     + "\n\nMonthly Payment: %.2f \nTotal Payment: %,.2f\n"
                     ,loanAmountValue,numOfYearsValue,interestRateValue,monthlyPaymentValue,totalPaymentValue);

                    double interestTableValue, principleTableValue, balanceTableValue; // Declare the variables
                    int count = 1;
                    System.out.println("\nPayment\t\tInterest\t\tPrincipal\t\tBalance"); 
                    balanceTableValue = loanAmountValue;
                    while(count != 1 + paymentNumberValue) // Use loop to display table
                     { 
                       interestTableValue = monthlyInterestRateValue * balanceTableValue;
                       principleTableValue = monthlyPaymentValue - interestTableValue;
                       balanceTableValue = balanceTableValue - principleTableValue;
                       System.out.printf("%d\t\t\t\t%.2f\t\t\t%.2f\t\t\t%.2f\n", count, interestTableValue,
                       principleTableValue, balanceTableValue);
                       count++;   
                     } // Using System.out to print to console

                 break;
        case "d": JTextArea txt = new JTextArea(); //Create JTextArea
                    txt.setText(createTableValue);
                    JScrollPane sp = new JScrollPane(txt); // Create ScrollPane
                    JFrame jf = new JFrame("Loan Payment Schedule");
                    jf.getContentPane().add(sp);
                    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    jf.setSize(new Dimension(400,600)); // Set Dimensions on Dialog box
                    jf.setLocale(null);
                    jf.setVisible(true);
                 break; // Using JTextArea to print to a Dialog box
        case "f":
                    PrintWriter loanPayment = new PrintWriter("loanPayment.txt");
                    loanPayment.printf("Loan amount: $%,.2f\nLoan period %d year(s)\nAnnual Interest Rate:%.2f"
                    + "\n\nMonthly Payment: %.2f \nTotal Payment: %,.2f\n"
                    ,loanAmountValue,numOfYearsValue,interestRateValue,monthlyPaymentValue,totalPaymentValue);
                    
                    loanPayment.println("\nPayment\t\tInterest\t\tPrincipal\t\tBalance"); 
                    balanceTableValue = loanAmountValue;
                    int countValue = 1;
                    while(countValue != 1 + paymentNumberValue) // Use loop to display table
                     { 
                       interestTableValue = monthlyInterestRateValue * balanceTableValue;
                       principleTableValue = monthlyPaymentValue - interestTableValue;
                       balanceTableValue = balanceTableValue - principleTableValue;
                       loanPayment.printf("%d\t\t%.2f\t\t\t%.2f\t\t\t%.2f%n", countValue, interestTableValue,
                       principleTableValue, balanceTableValue);
                       countValue++;                                                      
                     }    // Using loanPAyment.println to print to the file
                     loanPayment.close();
                     JOptionPane.showMessageDialog(null, "Your results have been displayed in a file named \"loanPayment\"");
                 break;
               }  
               
} //Create a table to return the string value used in case the user chooses dialog box as their perferred method
public static String createTable(double loanAmountValue,double monthlyInterestRateValue,double monthlyPaymentValue,int paymentNumberValue,
                              int numOfYearsValue, double interestRateValue, double totalPaymentValue)
{  
  String formatLoanAmountValue, formatNumOfYearsValue, formatInterestRateValue, formatMonthlyPayValue, formatTotalPaymentValue;
  // Declare Variables
  formatLoanAmountValue = String.format("$%,.2f",loanAmountValue);
  formatNumOfYearsValue = String.format("%d",numOfYearsValue);
  formatInterestRateValue = String.format("%.2f",interestRateValue);
  formatMonthlyPayValue = String.format("%.2f",monthlyPaymentValue);
  formatTotalPaymentValue = String.format("%,.2f",totalPaymentValue); // Format the values to look neat on display
  
  StringBuffer sbf = new StringBuffer(); // Create a String buffer of everything needed for the dialog box and store it to return the value
  sbf.append("Loan amount: ");
  sbf.append(formatLoanAmountValue);
  sbf.append("\nLoan period ");
  sbf.append(formatNumOfYearsValue);
  sbf.append(" year(s)\nAnnual Interest Rate: ");
  sbf.append(formatInterestRateValue);
  sbf.append("\n\nMonthly Payment: ");
  sbf.append(formatMonthlyPayValue);
  sbf.append("\nTotal Payment: ");
  sbf.append(formatTotalPaymentValue);
  sbf.append("\n\nPayment\tInterest\tPrincipal\tBalance");
  
  double interestTableValue, principleTableValue, balanceTableValue;     
  String formatInterestTableValue, formatPrincipleTableValue, formatBalanceTableValue; //Declare variables needed for method
  int count = 1;
  balanceTableValue = loanAmountValue;
  
  while(count != 1 + paymentNumberValue) // Use loop to display table
  { 
  interestTableValue = monthlyInterestRateValue * balanceTableValue;
  principleTableValue = monthlyPaymentValue - interestTableValue;
  balanceTableValue = balanceTableValue - principleTableValue;
  
  formatInterestTableValue = String.format("%.2f", interestTableValue);
  formatPrincipleTableValue = String.format("%.2f", principleTableValue);
  formatBalanceTableValue = String.format("%.2f", balanceTableValue);

  sbf.append("\n   ");
  sbf.append(count);
  sbf.append("\t");
  sbf.append(formatInterestTableValue);
  sbf.append("\t");
  sbf.append(formatPrincipleTableValue);
  sbf.append("\t");
  sbf.append(formatBalanceTableValue);
  sbf.append("\n"); 
  count++;         
  }
  
  String str = sbf.toString();
  return str;  //Return String Buffer as String to be used in JTextArea      
}

/** This method will collect the user input on the loan amount and return the variable to the main method in order to calculate
the user's loan payment schedule */
public static double getLoan() 
{
  double newLoanAmount; 
  newLoanAmount = Double.parseDouble(JOptionPane.showInputDialog(null,
  "Enter a loan amount (ex. \"7000\") : "));
  
  while (newLoanAmount <= 0) // User Input Validation
  {
  newLoanAmount = Double.parseDouble(JOptionPane.showInputDialog(null,
  "You have entered an invalid number. Enter a loan amount above 0: "));
  }
  return newLoanAmount;      
}

  /** This method will collect the user input on the amount of years they plan on paying the loan off in 
   and return the variable to the main method in order to calculate the user's loan payment schedule */
public static int getYear()
{
  int newNumOfYears;
  newNumOfYears = Integer.parseInt(JOptionPane.showInputDialog(null,
  "Enter the whole number of years that the loan is over (ex. \"6\") :"));
  
  while (newNumOfYears < 1) // User Input Validation
  {
  newNumOfYears = Integer.parseInt(JOptionPane.showInputDialog(null,
  "You have entered an invalid number. Enter a number of years above 0:"));
  }
  
  return newNumOfYears;
}

/** This method will collect the user input on how much the interest on their loan is 
   and return the variable to the main method in order to calculate the user's loan payment schedule */
public static double getRate()
{
  double newInterestRate; 
  newInterestRate = Double.parseDouble(JOptionPane.showInputDialog(null,
  "Enter an interest rate (ex. \"12\"): "));
  
  while (newInterestRate <= 0 || newInterestRate > 100 ) // User Input Validation
  {
  newInterestRate = Double.parseDouble(JOptionPane.showInputDialog(null,
  "You have entered an invalid number. Enter an interest rate above 0 or below 100:"));
  }
  return newInterestRate;   
}   

 /** This method will accept InterestRate and use calculations in order to return the monthly interest rate value */
public static double getMonthlyInterestRate(double yearlyRateValue)
{
  final int percentToDecimal = 100; // Setting final variable to avoid confusion
  double newMonthlyRateValue;
  newMonthlyRateValue = (yearlyRateValue / percentToDecimal) / numOfMonthsInYear;
  return newMonthlyRateValue; 
}
/** This method will accept the loan amount, monthly interest rate, and number of months and calculate the monthly payment
    and return the value. This monthly payment is used to calculate the loan payment schedule.*/
public static double calculateMonthlyPayment(double loanAmountValue, double monthlyInterestRateValue, int paymentNumberValue)
{
  double calcMonthlyPayment;
  calcMonthlyPayment = loanAmountValue* monthlyInterestRateValue * Math.pow(1 + monthlyInterestRateValue, paymentNumberValue)/
                       ((Math.pow(1 + monthlyInterestRateValue, paymentNumberValue) - 1)); //From formula
  return calcMonthlyPayment;
}   
}