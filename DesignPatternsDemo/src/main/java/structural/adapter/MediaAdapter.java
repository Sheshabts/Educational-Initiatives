```java
package structural.adapter;

public class MediaAdapter implements MediaPlayer {
    private AdvancedPlayer advancedPlayer = new AdvancedPlayer();

    @Override
    public void play(String filename) {
        if (filename.endsWith(".mp4")) {
            advancedPlayer.playMP4(filename);
        } else {
            System.out.println("Unsupported format: " + filename);
        }
    }
}
```
