interface Notification {
    void notifyUser(String msg);
}
class EmailNotification implements Notification {
    public void notifyUser(String msg) { System.out.println("EMAIL: " + msg); }
}
class SMSNotification implements Notification {
    public void notifyUser(String msg) { System.out.println("SMS: " + msg); }
}
class NotificationFactory {
    public static Notification createNotification(String type) {
        if (type.equalsIgnoreCase("EMAIL")) return new EmailNotification();
        else if (type.equalsIgnoreCase("SMS")) return new SMSNotification();
        return null;
    }
}
public class MainFactory {
    public static void main(String[] args) {
        Notification n = NotificationFactory.createNotification("EMAIL");
        n.notifyUser("Welcome!");
        n = NotificationFactory.createNotification("SMS");
        n.notifyUser("Your code: 1234");
    }
}
