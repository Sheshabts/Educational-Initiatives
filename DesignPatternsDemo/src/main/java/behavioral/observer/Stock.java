```java
package behavioral.observer;

public interface Stock {
    void register(Investor investor);
    void unregister(Investor investor);
    void notifyInvestors();
}
```
