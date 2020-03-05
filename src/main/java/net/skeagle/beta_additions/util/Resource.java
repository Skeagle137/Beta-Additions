package net.skeagle.beta_additions.util;

import lombok.Getter;
import net.skeagle.beta_additions.BetaMain;
import org.bukkit.util.config.Configuration;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

class Resource extends Configuration {
    @Getter
    private final File file;
    @Getter
    private final Configuration settings;
    private final BetaMain main;
    private final ClassLoader loader;

    Resource(String name, BetaMain main) {
        super(new File(main.getDataFolder(), name));
        this.main = main;
        this.loader = main.getLoader();
        this.file = new File(main.getDataFolder(), name);
        this.settings = new Configuration(this.file);
        if (!this.file.getParentFile().exists()) {
            this.file.getParentFile().mkdirs();
        }
        if (!this.file.exists()) {
            this.saveResource(name, true);
        }
        if (this.root == null) {
            this.root = new HashMap<>();
        }
    }

    public InputStream getResource(String filename) {
        try {
            URL url = this.loader.getResource(filename);
            if (url == null) {
                return null;
            } else {
                URLConnection connection = url.openConnection();
                connection.setUseCaches(false);
                return connection.getInputStream();
            }
        } catch (IOException ex) {
            return null;
        }
    }

    public void saveResource(String resourcePath, boolean replace) {
        if (resourcePath != null && !resourcePath.equals("")) {
            resourcePath = resourcePath.replace('\\', '/');
            InputStream in = this.getResource(resourcePath);
            if (in == null) {
                throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + this.file);
            } else {
                File outFile = new File(this.main.getDataFolder(), resourcePath);
                int lastIndex = resourcePath.lastIndexOf(47);
                File outDir = new File(this.main.getDataFolder(), resourcePath.substring(0, Math.max(lastIndex, 0)));
                if (!outDir.exists()) {
                    outDir.mkdirs();
                }

                try {
                    if (outFile.exists() && !replace) {
                        Commons.log("Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
                    } else {
                        OutputStream out = new FileOutputStream(outFile);
                        byte[] buf = new byte[1024];

                        int len;
                        while((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }

                        out.close();
                        in.close();
                    }
                } catch (IOException ex) {
                    Commons.log(ex, "Could not save " + outFile.getName() + " to " + outFile);

                }

            }
        } else {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }
    }
}