package edupackage.cisc191;

import com.opencsv.bean.CsvBindByName;

public class AccountInfo {

        @CsvBindByName(column = "id")
        private int id;

        @CsvBindByName(column = "number")
        private long cardNumber;

        @CsvBindByName(column = "pin")
        private String pin;

        @CsvBindByName(column = "balance")
        private double balance;

        @CsvBindByName(column = "debt")
        private double debt;

        @Override
        public String toString() {
            return String.format("AccountInfo[id=%d, number=%d, pin=%s, balance=%.2f, debt=%.2f]",
                    id, cardNumber, pin, balance, debt);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(long cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getPin() {
            return pin;
        }

        public void setPin(String pin) {
            this.pin = pin;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public double getDebt() {
            return debt;
        }

        public void setDebt(double debt) {
            this.debt = debt;
        }
    }

