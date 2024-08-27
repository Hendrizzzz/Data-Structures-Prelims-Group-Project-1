
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class CSVFileModifier {
    public static void main(String[] args) {
        String inputFile = "";
        String outputFile = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             FileWriter writer = new FileWriter(outputFile)) {

            String line;
            String header = reader.readLine();
            writer.write(header + "\n");

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                String newContactInfo = "+639" + String.format("%09d", new Random().nextInt(899999999) + 10000000);

                fields[4] = newContactInfo;
                writer.write(String.join(",", fields) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}