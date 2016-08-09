package com.turlington.realmStatusChecker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

public class Tool {
    public static void main(String[] args) throws IOException {
        Path realmPath = Paths.get("C:\\Users\\Mitchell\\Desktop\\realmList.txt");
        List<String> lines = Files.lines(realmPath).map(l -> l+"\n").collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        lines.forEach(l -> sb.append(l.replace(" ","_").replace("'","")));
        Files.write(realmPath, sb.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.TRUNCATE_EXISTING);
    }
}
