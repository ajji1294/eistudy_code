interface PaymentStrategy { void pay(int amount); }

class CardPayment implements PaymentStrategy {
    public void pay(int amount) { System.out.println("Paid " + amount + " by Card"); }
}

class PayPalPayment implements PaymentStrategy {
    public void pay(int amount) { System.out.println("Paid " + amount + " by PayPal"); }
}

class ShoppingCart {
    PaymentStrategy payment;
    void setPayment(PaymentStrategy p) { payment = p; }
    void checkout(int amount) { payment.pay(amount); }
}

public class StrategyDemo {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        cart.setPayment(new CardPayment());
        cart.checkout(500);
        cart.setPayment(new PayPalPayment());
        cart.checkout(1200);
    }
}
