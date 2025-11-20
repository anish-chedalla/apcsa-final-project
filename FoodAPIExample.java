import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FoodAPIExample {

    public static void main(String[] args) {
        String barcode = args[0]; // example
        //Example barcode: 737628064502
        String type_input = args[1];
        boolean isLong = false;
        if (type_input.equals("long")) {
            isLong = true;
        }else if (type_input.equals("short")) {
            isLong = false;
        } else {
            System.out.println("Invalid type input. Use 'long' or 'short'.");
        }

        String apiUrl = "https://world.openfoodfacts.org/api/v2/product/" + barcode;

        try {
            // Create the URL
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Set request method
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Java Food Tracker App - APCSA Project (Class)");

            int responseCode = conn.getResponseCode();
            System.out.println("Response code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );

                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                // Print raw JSON
                if (isLong) {
                    System.out.println("API Response:");
                    System.out.println(response.toString());
                }
                // Example: extract the product name using simple search
                String json = response.toString();
                String key = "\"product_name\":";
                int index = json.indexOf(key);

                if (index != -1) {
                    int start = json.indexOf("\"", index + key.length()) + 1;
                    int end = json.indexOf("\"", start);
                    String productName = json.substring(start, end);
                    System.out.println("Product Name: " + productName);

                    // parse brand name as well (looks for "brands": "Brand Name")
                    String brandKey = "\"brands\":";
                    int bIndex = json.indexOf(brandKey);
                    if (bIndex != -1) {
                        int bStart = json.indexOf("\"", bIndex + brandKey.length()) + 1;
                        int bEnd = json.indexOf("\"", bStart);
                        String brandName = json.substring(bStart, bEnd);
                        System.out.println("Brand: " + brandName);
                    } else {
                        System.out.println("Brand not found.");
                    }
                } else {
                    System.out.println("Product name not found.");
                }

            } else {
                System.out.println("Failed to connect to API.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // public static void runMain(String[] args) {
    //     main(args);
    // }
}
