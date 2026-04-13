import java.util.*;

class BankAccount{
    int accountNumb;
    String username;
    int balance;

    public BankAccount(int accountNumb,String username,int balance){
        this.accountNumb=accountNumb;
        this.username=username;
        this.balance=balance;
    }
}
public class Main {
    static LinkedList<BankAccount> accounts =new LinkedList<>();
    static Scanner scan =new Scanner(System.in);
    static Stack<String> transaction=new Stack<>();
    static Queue<String> billQueue = new LinkedList<>();
    static Queue<BankAccount> accountRequests = new LinkedList<>();

    public static void main(String[] args){
        while(true){
        System.out.println("||  Menu  ||");
        System.out.println("1 Enter Bank");
        System.out.println("2 Enter ATM");
        System.out.println("3 Admin Area");
        System.out.println("4 Exit");
        System.out.print("Enter your choice:");

        int choice= scan.nextInt();
        switch (choice) {
            case 1: bank(); break;
            case 2: atm(); break;
            case 3: adminMenu(); break;
            case 4: return;
            default: System.out.println("Invalid choice");
        }
        }
    }
    static void bank(){
            while (true) {
                System.out.println("\n|| Bank Menu ||");
                System.out.println("1 Add User");
                System.out.println("2 Deposit");
                System.out.println("3 Withdraw");
                System.out.println("4 Bill Payment");
                System.out.println("5 Show Last Transaction");
                System.out.println("6 Undo Transaction");
                System.out.println("7 Back");

                int choice = scan.nextInt();

                switch (choice) {
                    case 1:adduser();break;
                    case 2: deposit(); break;
                    case 3: withdraw(); break;
                    case 4: billPayment(); break;
                    case 5: lastTran(); break;
                    case 6: undoTran(); break;
                    case 7: return;
                    default: System.out.println("Invalid choice");

            }
        }
    }
    static void atm() {
        while (true) {
            System.out.println("\n|| ATM |");
            System.out.println("1 Balance");
            System.out.println("2 Withdraw");
            System.out.println("3 Back");

            int choice = scan.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String name = scan.next();

                    for (BankAccount acc : accounts) {
                        if (acc.username.equals(name)) {
                            System.out.println("Balance: " + acc.balance);
                        }
                    }
                    break;

                case 2: withdraw(); break;
                case 3: return;
            }
        }
    }
    static void adminMenu() {
        while (true) {
            System.out.println("\n|| Admin Menu ||");
            System.out.println("1 View requests");
            System.out.println("2 Process request");
            System.out.println("3 Add bill");
            System.out.println("4 Process bill");
            System.out.println("5 Back");

            int choice = scan.nextInt();

            switch (choice) {
                case 1: showRequests(); break;
                case 2: processRequest(); break;
                case 3: addBill(); break;
                case 4: processBill(); break;
                case 5: return;
            }
        }
    }
    static void adduser(){

        System.out.print("How many accounts you want?: ");
        int n = scan.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter the account number: ");
            int acc = scan.nextInt();

            System.out.print("Enter username: ");
            String name = scan.next();

            System.out.print("Enter balance: ");
            int bal = scan.nextInt();
            accounts.add(new BankAccount(acc, name, bal));
        }
        System.out.println("\nAccounts added successfully!");
        System.out.println("Accounts List:");
        for (BankAccount acc : accounts) {
            System.out.println(acc.accountNumb + ". " + acc.username + " - Balance: " + acc.balance);
        }

    }
    static void deposit() {
        System.out.print("\nEnter username:");
        String name = scan.next();

        boolean found = false;

        for (BankAccount acc : accounts) {
            if (acc.username.equals(name)) {
                System.out.print("Enter deposit amount: ");
                int amount = scan.nextInt();

                acc.balance += amount;
                transaction.push("Deposit "+amount+"tg"+" to "+acc.username);
                System.out.println("New balance: " + acc.balance+" tg");
                found = true;
                break;

            }

        }
        System.out.println(transaction);
        if (!found) {
            System.out.println("User not found.");
        }


    }
    static void withdraw(){
        System.out.print("\nEnter username:");
        String n = scan.next();

        boolean found = false;

        for (BankAccount acc : accounts) {
            if (acc.username.equals(n)) {
                System.out.print("Enter withdraw amount: ");
                int amount = scan.nextInt();

                if (acc.balance >= amount) {
                    acc.balance -= amount;
                    System.out.println("New balance: " + acc.balance+" tg");
                } else {
                    System.out.println("Not enough money!");
                }
                if (acc.balance >= amount) {
                    acc.balance -= amount;
                    transaction.push("Withdraw " + amount+"tg" + " from " + acc.username);
                }



                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("User not found.");
        }
    }
    static void lastTran() {
        if (!transaction.isEmpty()) {
            System.out.println("Last transaction: " + transaction.peek());
        } else {
            System.out.println("No transactions.");
        }
    }
    static void undoTran() {
        if (!transaction.isEmpty()) {
            System.out.println("Undo: " + transaction.pop());
        } else {
            System.out.println("Nothing to undo.");
        }
    }
    static void billPayment() {
        System.out.print("Enter username: ");
        String name = scan.next();

        for (BankAccount acc : accounts) {
            if (acc.username.equals(name)) {
                System.out.print("Enter bill amount: ");
                int amount = scan.nextInt();

                if (acc.balance >= amount) {
                    acc.balance -= amount;
                    transaction.push("Bill payment " + amount + " from " + acc.username);
                    System.out.println("Bill paid. New balance: " + acc.balance);
                } else {
                    System.out.println("Not enough money!");
                }
                return;
            }
        }
        System.out.println("User not found.");
    }
    static void addBill() {
        System.out.print("Enter bill name: ");
        String bill = scan.next();
        billQueue.add(bill);
    }
    static void processBill() {
        if (!billQueue.isEmpty()) {
            System.out.println("Processing: " + billQueue.poll());
        } else {
            System.out.println("No bills.");
        }
    }
    static void requestAccount() {
        System.out.print("Enter account number: ");
        int acc = scan.nextInt();

        System.out.print("Enter username: ");
        String name = scan.next();

        System.out.print("Enter balance: ");
        int bal = scan.nextInt();

        accountRequests.add(new BankAccount(acc, name, bal));

        System.out.println("Request submitted!");
    }
    static void processRequest() {
        if (!accountRequests.isEmpty()) {
            BankAccount acc = accountRequests.poll();
            accounts.add(acc);

            System.out.println("Account approved: "+acc.username);
        } else {
            System.out.println("No requests.");
        }
    }
    static void showRequests() {
        if (accountRequests.isEmpty()) {
            System.out.println("No pending requests.");
        } else {
            for (BankAccount acc : accountRequests) {
                System.out.println(acc.accountNumb+" - "+acc.username);
            }
        }
    }
}








