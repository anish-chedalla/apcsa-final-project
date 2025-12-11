import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The FoodAPI class provides methods to interact with the Open Food Facts API.
 * It allows fetching product information based on barcodes.
 * It includes a nested ProductInfo class to encapsulate product details.
 * The main method demonstrates how to use the API to fetch and display product information.
 */
public class FoodAPI {
    /**
     * The ProductInfo class encapsulates product details such as name and brand.
     */
    public static class ProductInfo {
        private final String name;
        private final String brand;

        /**
         * Constructs a ProductInfo object with the specified name and brand.
         * @param name the name of the product
         * @param brand the brand of the product
         */
        public ProductInfo(String name, String brand) {
            this.name = name;
            this.brand = brand;
        }
        /**
         * Gets the name of the product.
         * @return name of the product
         */
        public String getName() {
            return name;
        }

        /**
         * Gets the brand of the product.
         * @return brand of the product
         */
        public String getBrand() {
            return brand;
        }

        /**
         * Returns a string representation of the ProductInfo object.
         * @return string representation of the ProductInfo
         */
        @Override
        public String toString() {
            return "ProductInfo{name='" + name + "', brand='" + brand + "'}";
        }
    }

    
     //java FoodAPI 737628064502 long
     
     /**
      * The main method to run the FoodAPI example.
      * 
      * @param args command line arguments: barcode and optional type (long/short)
      */
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

    /**
     * Fetches product information from the Open Food Facts API using the provided barcode.
     * 
     * @param barcode the product barcode
     * @param printRaw whether to print the raw JSON response
     * @return a ProductInfo object containing the product name and brand, or null if not found
     */
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

    /**
     * Extracts the value of a given key from a JSON string.
     * @param json the JSON string
     * @param keyWithQuotesAndColon the key to search for, including quotes and colon (e.g., "\"product_name\":")
     * @return the extracted value, or null if not found
     */
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
