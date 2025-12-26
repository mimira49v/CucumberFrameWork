package utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Generic Test Data Reader Framework
 * Supports: Properties, JSON, CSV files from any location
 * Designed to be flexible and reusable across different test data files
 */
public class TestDataReader {

    private static final String TEST_DATA_PATH = "src/test/resources/testData/";
    private static final Map<String, Properties> propertiesCache = new HashMap<>();
    private static final Map<String, JsonObject> jsonCache = new HashMap<>();

    // ============ Generic Data Retrieval Methods ============

    /**
     * Generic method to get data from Properties file
     * @param fileName - name of properties file (without path)
     * @param recordId - unique identifier for the record
     * @param prefix - prefix for properties (e.g., "customer1", "order1")
     * @return Map containing all properties for the record
     */
    public static Map<String, String> getDataFromProperties(String fileName, String recordId, String prefix) {
        Properties props = loadPropertiesFile(fileName);
        Map<String, String> data = new HashMap<>();

        String recordPrefix = prefix != null ? prefix + "." : recordId + ".";

        // Get all properties that match the prefix
        for (String key : props.stringPropertyNames()) {
            if (key.startsWith(recordPrefix)) {
                String fieldName = key.substring(recordPrefix.length());
                data.put(fieldName, props.getProperty(key));
            }
        }

        if (data.isEmpty()) {
            throw new RuntimeException("No data found for record ID '" + recordId + "' in " + fileName);
        }

        return data;
    }

    /**
     * Generic method to get data from JSON file
     * @param fileName - name of JSON file (without path)
     * @param arrayName - name of the JSON array containing records (e.g., "customers", "orders", "products")
     * @param idField - field name to match (e.g., "id", "customerId")
     * @param idValue - value to match
     * @return Map containing all fields for the matched record
     */
    public static Map<String, String> getDataFromJson(String fileName, String arrayName, String idField, String idValue) {
        JsonObject jsonData = loadJsonFile(fileName);

        if (!jsonData.has(arrayName)) {
            throw new RuntimeException("Array '" + arrayName + "' not found in " + fileName);
        }

        JsonArray array = jsonData.getAsJsonArray(arrayName);
        for (int i = 0; i < array.size(); i++) {
            JsonObject record = array.get(i).getAsJsonObject();

            if (record.has(idField) && record.get(idField).getAsString().equals(idValue)) {
                return jsonObjectToMap(record);
            }
        }

        throw new RuntimeException("Record with " + idField + "='" + idValue + "' not found in " + fileName);
    }

    /**
     * Generic method to get ALL records from JSON array
     * @param fileName - name of JSON file
     * @param arrayName - name of the JSON array
     * @return List of Maps, each representing a record
     */
    public static List<Map<String, String>> getAllDataFromJson(String fileName, String arrayName) {
        JsonObject jsonData = loadJsonFile(fileName);

        if (!jsonData.has(arrayName)) {
            throw new RuntimeException("Array '" + arrayName + "' not found in " + fileName);
        }

        List<Map<String, String>> allRecords = new ArrayList<>();
        JsonArray array = jsonData.getAsJsonArray(arrayName);

        for (int i = 0; i < array.size(); i++) {
            JsonObject record = array.get(i).getAsJsonObject();
            allRecords.add(jsonObjectToMap(record));
        }

        return allRecords;
    }

    /**
     * Generic method to get data from CSV file
     * @param fileName - name of CSV file (without path)
     * @param idColumn - name of the ID column (e.g., "customerId", "orderId")
     * @param idValue - value to match
     * @return Map containing all columns for the matched row
     */
    public static Map<String, String> getDataFromCSV(String fileName, String idColumn, String idValue) {
        String csvFile = TEST_DATA_PATH + fileName;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String headerLine = br.readLine();
            if (headerLine == null) {
                throw new RuntimeException("CSV file is empty: " + fileName);
            }

            String[] headers = headerLine.split(",");
            int idColumnIndex = -1;

            // Find the index of the ID column
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].trim().equals(idColumn)) {
                    idColumnIndex = i;
                    break;
                }
            }

            if (idColumnIndex == -1) {
                throw new RuntimeException("Column '" + idColumn + "' not found in CSV: " + fileName);
            }

            // Read data rows
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > idColumnIndex && values[idColumnIndex].trim().equals(idValue)) {
                    Map<String, String> rowData = new HashMap<>();
                    for (int i = 0; i < headers.length && i < values.length; i++) {
                        rowData.put(headers[i].trim(), values[i].trim());
                    }
                    return rowData;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file '" + fileName + "': " + e.getMessage());
        }

        throw new RuntimeException("Record with " + idColumn + "='" + idValue + "' not found in CSV: " + fileName);
    }

    /**
     * Generic method to get ALL records from CSV file
     * @param fileName - name of CSV file
     * @return List of Maps, each representing a CSV row
     */
    public static List<Map<String, String>> getAllDataFromCSV(String fileName) {
        String csvFile = TEST_DATA_PATH + fileName;
        List<Map<String, String>> allRecords = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String headerLine = br.readLine();
            if (headerLine == null) {
                throw new RuntimeException("CSV file is empty: " + fileName);
            }

            String[] headers = headerLine.split(",");

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Map<String, String> rowData = new HashMap<>();
                for (int i = 0; i < headers.length && i < values.length; i++) {
                    rowData.put(headers[i].trim(), values[i].trim());
                }
                allRecords.add(rowData);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file '" + fileName + "': " + e.getMessage());
        }

        return allRecords;
    }

    // ============ Convenience Methods for Common Use Cases ============

    /**
     * Convenience method: Get customer data from any file type
     * Auto-detects file type based on extension
     */
    public static Map<String, String> getCustomerData(String fileName, String customerId) {
        if (fileName.endsWith(".properties")) {
            return getDataFromProperties(fileName, customerId, customerId);
        } else if (fileName.endsWith(".json")) {
            return getDataFromJson(fileName, "customers", "id", customerId);
        } else if (fileName.endsWith(".csv")) {
            return getDataFromCSV(fileName, "customerId", customerId);
        } else {
            throw new RuntimeException("Unsupported file type: " + fileName);
        }
    }

    /**
     * Get specific property value from properties file
     * @param fileName - properties file name
     * @param key - property key
     * @return property value
     */
    public static String getProperty(String fileName, String key) {
        Properties props = loadPropertiesFile(fileName);
        String value = props.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in " + fileName);
        }
        return value;
    }

    // ============ Utility Methods ============

    /**
     * Generate unique email to avoid duplicates
     * @param baseName - base name for email
     * @param domain - email domain (default: "test.com")
     * @return unique email address
     */
    public static String generateUniqueEmail(String baseName, String domain) {
        String sanitizedName = baseName.toLowerCase().replaceAll("[\\s@]+", ".");
        long timestamp = System.currentTimeMillis();
        String emailDomain = (domain != null && !domain.isEmpty()) ? domain : "test.com";
        return sanitizedName + "." + timestamp + "@" + emailDomain;
    }

    public static String generateUniqueEmail(String baseName) {
        return generateUniqueEmail(baseName, "apple.com");
    }

    /**
     * Check if test data file exists
     */
    public static boolean fileExists(String fileName) {
        return new File(TEST_DATA_PATH + fileName).exists();
    }

    /**
     * List all available test data files
     */
    public static List<String> listTestDataFiles() {
        File testDataDir = new File(TEST_DATA_PATH);
        List<String> files = new ArrayList<>();

        if (testDataDir.exists() && testDataDir.isDirectory()) {
            File[] fileList = testDataDir.listFiles();
            if (fileList != null) {
                for (File file : fileList) {
                    if (file.isFile()) {
                        files.add(file.getName());
                    }
                }
            }
        }

        return files;
    }

    // ============ Private Helper Methods ============

    private static Properties loadPropertiesFile(String fileName) {
        if (propertiesCache.containsKey(fileName)) {
            return propertiesCache.get(fileName);
        }

        Properties props = new Properties();
        String filePath = TEST_DATA_PATH + fileName;

        try (FileInputStream fis = new FileInputStream(filePath)) {
            props.load(fis);
            propertiesCache.put(fileName, props);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file '" + fileName + "': " + e.getMessage());
        }

        return props;
    }

    private static JsonObject loadJsonFile(String fileName) {
        if (jsonCache.containsKey(fileName)) {
            return jsonCache.get(fileName);
        }

        String filePath = TEST_DATA_PATH + fileName;

        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JsonObject jsonData = JsonParser.parseString(content).getAsJsonObject();
            jsonCache.put(fileName, jsonData);
            return jsonData;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load JSON file '" + fileName + "': " + e.getMessage());
        }
    }

    private static Map<String, String> jsonObjectToMap(JsonObject jsonObject) {
        Map<String, String> map = new HashMap<>();

        for (String key : jsonObject.keySet()) {
            JsonElement element = jsonObject.get(key);
            if (element.isJsonPrimitive()) {
                map.put(key, element.getAsString());
            } else {
                // For nested objects, store as JSON string
                map.put(key, element.toString());
            }
        }

        return map;
    }

    /**
     * Clear all cached data (useful for testing)
     */
    public static void clearCache() {
        propertiesCache.clear();
        jsonCache.clear();
    }

    /**
     * Print debug information about loaded data
     */
    public static void printDebugInfo() {
        System.out.println("=== Test Data Reader Debug Info ===");
        System.out.println("Test Data Path: " + TEST_DATA_PATH);
        System.out.println("Available Files: " + listTestDataFiles());
        System.out.println("Cached Properties Files: " + propertiesCache.keySet());
        System.out.println("Cached JSON Files: " + jsonCache.keySet());
    }
}