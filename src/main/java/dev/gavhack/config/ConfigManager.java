package dev.gavhack.config;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;

public class ConfigManager {

    private final TomlWriter writer;
    private final Toml toml;

    public ConfigManager() {
        this.toml = new Toml();
        this.writer = new TomlWriter.Builder().build();
    }

    public Toml getToml() {
        return toml;
    }

    public TomlWriter getWriter() {
        return writer;
    }
}
