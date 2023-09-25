package org.aren.particlefactory.compiler;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.*;

public class DBNCompiler {

    JavaPlugin plugin;
    Map<String, SyntaxType> lexerMap = new LinkedHashMap<>();

    public DBNCompiler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    private List<File> getAllScriptFiles() {

        if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdir();
        if (plugin.getDataFolder().listFiles() == null)
            return new ArrayList<>();

        List<File> fileList = Arrays.asList(Objects.requireNonNull(
                plugin.getDataFolder().listFiles(
                        ((dir, name) -> name.endsWith(".pfs"))
                )
        ));
        return Optional.of(new ArrayList<>(fileList)).orElse(new ArrayList<>());
    }

    public void lexer() {
        List<String> tokens = tokenize();

        for (String token : tokens) {
            if (token.isEmpty())
                continue;
            if (isLong(token))
                lexerMap.put(token, SyntaxType.NUMBER);
            else if (token.equals("{") || token.equals("}"))
                lexerMap.put(token, SyntaxType.PARAM);
            else
                lexerMap.put(token, SyntaxType.NAME);
        }

        lexerMap.forEach((key, value) -> {
            plugin.getLogger().info(String.format("key : %s, value : %s", key, value));
        });
    }

    private boolean isLong(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private List<String> tokenize() {
        List<File> files = getAllScriptFiles();

        List<String> tokens = new ArrayList<>();

        try {
            for (File file : files) {
                BufferedReader r = new BufferedReader(new FileReader(file));
                String s;

                while ((s = r.readLine()) != null) {
                    s = s.replace(":", " : ");
                    s = s.replace("{", " { ");
                    s = s.replace("}", " } ");
                    tokens.addAll(Arrays.asList(s.trim().split("\\s+")));
                }
            }

//            tokens.forEach(s -> {
//                plugin.getLogger().info(s);
//            });

            return tokens;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
