class Settings {
    private static Settings instance;
    private Settings() {}
    public static synchronized Settings getInstance() {
        if (instance == null) instance = new Settings();
        return instance;
    }
    public void print() { System.out.println("Only one Settings instance!"); }
}
public class MainSingleton {
    public static void main(String[] args) {
        Settings s1 = Settings.getInstance();
        Settings s2 = Settings.getInstance();
        s1.print();
        System.out.println(s1 == s2); // Should print true
    }
}
