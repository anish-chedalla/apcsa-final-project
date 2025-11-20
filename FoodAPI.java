import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FoodAPI {

    public static class ProductInfo {
        private final String name;
        private final String brand;

        public ProductInfo(String name, String brand) {
            this.name = name;
            this.brand = brand;
        }

        public String getName() {
            return name;
        }

        public String getBrand() {
            return brand;
        }

        @Override
        public String toString() {
            return "ProductInfo{name='" + name + "', brand='" + brand + "'}";
        }
    }

    
     //java FoodAPI 737628064502 long
     
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java FoodAPI <barcode> [long|short]");
            return;
        }

        String barcode = args[0];
        boolean printRaw = false;

        if (args.length >= 2) {
            String typeInput = args[1];
            if (typeInput.equalsIgnoreCase("long")) {
                printRaw = true;
            } else if (!typeInput.equalsIgnoreCase("short")) {
                System.out.println("Invalid type input. Use 'long' or 'short'. Defaulting to 'short'.");
            }
        }

        ProductInfo info = getProductInfo(barcode, printRaw);

        if (info != null) {
            System.out.println("Product Name: " + info.getName());
            System.out.println("Brand: " + info.getBrand());
        } else {
            System.out.println("Could not fetch product info.");
        }
    }

    
    public static ProductInfo getProductInfo(String barcode, boolean printRaw) {
        String apiUrl = "https://world.openfoodfacts.org/api/v2/product/" + barcode;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Java Food Tracker App - APCSA Project (Class)");

            int responseCode = conn.getResponseCode();
            // Optional: keep this if it helps debugging
            System.out.println("Response code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );

                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String json = response.toString();

                if (printRaw) {
                    System.out.println("API Response:");
                    System.out.println(json);
                }

                String productName = extractJsonStringValue(json, "\"product_name\":");
                String brandName = extractJsonStringValue(json, "\"brands\":");

                if (productName == null) {
                    productName = "Unknown";
                }
                if (brandName == null) {
                    brandName = "Unknown";
                }

                return new ProductInfo(productName, brandName);

            } else {
                System.out.println("Failed to connect to API.");
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static String extractJsonStringValue(String json, String keyWithQuotesAndColon) {
        int index = json.indexOf(keyWithQuotesAndColon);
        if (index == -1) {
            return null;
        }

        int start = json.indexOf("\"", index + keyWithQuotesAndColon.length());
        if (start == -1) return null;
        start++; 

        int end = json.indexOf("\"", start);
        if (end == -1) return null;

        return json.substring(start, end);
    }
}
