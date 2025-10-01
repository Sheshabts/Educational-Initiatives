interface OldPaymentGateway { void pay(String amount); }
class LegacyGateway implements OldPaymentGateway {
    public void pay(String amount) { System.out.println("Paid (old): " + amount); }
}
interface NewPaymentGateway { void makePayment(int cents); }
class FancyGateway implements NewPaymentGateway {
    public void makePayment(int cents) { System.out.println("Paid (new): " + (cents / 100.0)); }
}
class PaymentAdapter implements OldPaymentGateway {
    private NewPaymentGateway gateway;
    public PaymentAdapter(NewPaymentGateway g) { this.gateway = g; }
    public void pay(String amount) { gateway.makePayment((int)(Double.parseDouble(amount) * 100)); }
}
public class MainAdapter {
    public static void main(String[] args) {
        OldPaymentGateway legacy = new LegacyGateway();
        legacy.pay("50.5");

        OldPaymentGateway adapted = new PaymentAdapter(new FancyGateway());
        adapted.pay("30.75");
    }
}
