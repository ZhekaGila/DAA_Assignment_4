import io.JsonReader;
import model.Graph;

public class Main {
    public static void main(String[] args) {
        String path = "src/main/resources/tasks.json";

        JsonReader.readGraphFromJson(path);

    }
}