import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public class FolderListener {

    public static void main(String[] args) {
        TimerTask task = new RunMeTask();

        Timer timer = new Timer();
        //calistirdiktan  1 sn sonra folder i dinlemeye baslayacak her 5 dakikada bir guncelleyecek 
        timer.schedule(task, 1000,300000);
    }

    public static void listenToFolder(){
        //define a folder root
        Path myDir = Paths.get("D:/data");

        try {
            WatchService watcher = myDir.getFileSystem().newWatchService();
            myDir.register(watcher, ENTRY_CREATE,
                    ENTRY_DELETE,
                    ENTRY_MODIFY);

            WatchKey watckKey = watcher.take();

            List<WatchEvent<?>> events = watckKey.pollEvents();

            for (WatchEvent event : events) {
                if (event.kind() == ENTRY_CREATE) {
                    System.out.println("Created: " + event.context().toString());
                }
                if (event.kind() == ENTRY_DELETE) {
                    System.out.println("Delete: " + event.context().toString());
                }
                if (event.kind() == ENTRY_MODIFY) {
                    System.out.println("Modify: " + event.context().toString());
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }

    private static class RunMeTask extends TimerTask
    {
        @Override
        public void run() {
            FolderListener.listenToFolder();
        }
    }
}

