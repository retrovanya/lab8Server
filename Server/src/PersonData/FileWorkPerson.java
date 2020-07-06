package PersonData ;

import org.json.simple.JSONObject;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
public class FileWorkPerson implements Serializable {
    private static String json1;
    JSONObject json;


    public FileWorkPerson() throws IOException {
    }

    public String jsonParse() throws IOException {

        String path = "inf.json";

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(path)) {
        };
        String content = Files

                .lines(Paths.get(path), StandardCharsets.UTF_8)
                .collect(Collectors.joining(System.lineSeparator()));
        return  content;}

}