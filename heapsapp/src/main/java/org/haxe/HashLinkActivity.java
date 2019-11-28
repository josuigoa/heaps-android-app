package org.haxe;

import java.io.File;
import org.libsdl.app.SDLActivity;

public class HashLinkActivity extends SDLActivity {

    // Used to load the native libraries on application startup.
    static {
        System.loadLibrary("openal");
        System.loadLibrary("SDL2");
        System.loadLibrary("heapsapp");
    }

    @Override
    protected String[] getLibraries() {
        return new String[]{
                "openal",
                "SDL2",
                "heapsapp"
        };
    }

    public File getFilesDir(String dir) {
        return getFilesDir();
    }
}
