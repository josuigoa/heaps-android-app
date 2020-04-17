package org.haxe;

import java.io.File;
import java.util.concurrent.Semaphore;

import org.libsdl.app.SDLActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

public class HashLinkActivity extends SDLActivity {

    // Used to load the native libraries on application startup.
    static {
        System.loadLibrary("openal");
        System.loadLibrary("SDL2");
        System.loadLibrary("heapsapp");
    }

    @Override
    protected String[] getLibraries() {
        return new String[] {
                "openal",
                "SDL2",
                "heapsapp"
        };
    }

    private final Semaphore semaphore = new Semaphore(0, true);

    public void createAlertDialogVisitWebsite(final String title, final String message, final String buyButtonText, final String websiteUrl) {
        final HashLinkActivity activity = this;

        this.runOnUiThread(new Runnable() {
            public void run() {
                new AlertDialog.Builder(activity)
                    .setTitle(title)
                    .setMessage(message)
                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(buyButtonText, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent browserIntent = new Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(websiteUrl));
                            startActivity(browserIntent);
                            semaphore.release();
                        }
                    })
                    // A null listener allows the button to dismiss the dialog and take no further
                    // action.
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            semaphore.release();
                        }
                    })
                    .show();
            }
        });

        try {
            semaphore.acquire();
        }
        catch (InterruptedException e) { }
    }

    public File getFilesDir(String dir) {
        return getFilesDir();
    }
}
