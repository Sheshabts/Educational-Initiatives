abstract class SupportHandler {
    protected SupportHandler next;
    public SupportHandler setNext(SupportHandler handler) {
        this.next = handler;
        return handler;
    }
    public abstract void handleRequest(String level, String message);
}
class LowLevelHandler extends SupportHandler {
    public void handleRequest(String level, String message) {
        if (level.equals("LOW")) {
            System.out.println("LowLevelHandler processed: " + message);
        } else if (next != null) next.handleRequest(level, message);
    }
}
class MediumLevelHandler extends SupportHandler {
    public void handleRequest(String level, String message) {
        if (level.equals("MEDIUM")) {
            System.out.println("MediumLevelHandler processed: " + message);
        } else if (next != null) next.handleRequest(level, message);
    }
}
class HighLevelHandler extends SupportHandler {
    public void handleRequest(String level, String message) {
        if (level.equals("HIGH")) {
            System.out.println("HighLevelHandler processed: " + message);
        } else if (next != null) next.handleRequest(level, message);
    }
}
public class MainChain {
    public static void main(String[] args) {
        SupportHandler chain = new LowLevelHandler();
        chain.setNext(new MediumLevelHandler()).setNext(new HighLevelHandler());

        chain.handleRequest("LOW", "Password reset");
        chain.handleRequest("MEDIUM", "Some data issue");
        chain.handleRequest("HIGH", "Security incident!");
    }
}
