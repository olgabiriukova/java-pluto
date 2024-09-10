package Models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

/**
 * A class for downloading game status information from a JSON file.
 */
public class PlanetModel {
    private int x, y;
    private double health, weapon, maxHealth;
    private Double[] enemyHealth = new Double[4];
    private Double[] maxEnemyHealth = new Double[4];
    private Double[] enemyWeapon = new Double[4];
    private Boolean[] objects = new Boolean[6];
    private Boolean getKey, getHummer, getWood, getMetal;

    /**
     * Constructor for initializing values and settings.
     */
    public PlanetModel() {
        loadValues();
        loadSettings();
    }

    /**
     * Read information about planets from a file.
     */
    public void loadValues() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Read JSON file
            JsonNode rootNode = objectMapper.readTree(new File("src/main/input.json"));
            JsonNode plutoNode = rootNode.get("pluto");
            JsonNode xNode = plutoNode.get("x");
            JsonNode yNode = plutoNode.get("y");
            JsonNode healthNode = plutoNode.get("health");

            // Assign values to variables
            x = xNode.asInt();
            y = yNode.asInt();
            health = healthNode.asDouble();

            // Retrieve enemy health and object statuses
            JsonNode planetsNode = rootNode.get("planets");
            for (int i = 0; i < 4; i++) {
                String key = Integer.toString(i);
                enemyHealth[i] = planetsNode.get(key).asDouble();
            }
            JsonNode objectsNode = rootNode.get("objects");
            for (int i = 0; i < 6; i++) {
                String key = Integer.toString(i);
                objects[i] = objectsNode.get(key).asBoolean();
            }

            // Retrieve key, hammer, wood, and metal statuses
            JsonNode keyNode = rootNode.get("getKey");
            getKey = keyNode.asBoolean();
            JsonNode hummerNode = rootNode.get("getHummer");
            getHummer = hummerNode.asBoolean();
            JsonNode metalNode = rootNode.get("getMetal");
            getMetal = metalNode.asBoolean();
            JsonNode woodNode = rootNode.get("getWood");
            getWood = woodNode.asBoolean();

        } catch (IOException e) {
            System.out.println("read json error " + e.getMessage());
        }
    }

    /**
     * Load game settings from a file.
     */
    public void loadSettings() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Read JSON file
            JsonNode rootNode = objectMapper.readTree(new File("src/main/settings.json"));
            JsonNode plutoNode = rootNode.get("pluto");
            JsonNode healthNode = plutoNode.get("health");
            JsonNode weaponNode = plutoNode.get("weapon");

            // Assign values to variables
            maxHealth = healthNode.asDouble();
            weapon = weaponNode.asDouble();

            // Retrieve enemy weapon and maximum health
            JsonNode planetsNode = rootNode.get("planets");
            for (int i = 0; i < 4; i++) {
                JsonNode planetNode = planetsNode.get(i);
                enemyWeapon[i] = planetNode.path("weapon").asDouble();
                maxEnemyHealth[i] = planetNode.path("health").asDouble();
            }

        } catch (IOException e) {
            System.out.println("read json error " + e.getMessage());
        }
    }

    /**
     * Write information to a file.
     * @param x Pluto's x-coordinate
     * @param y Pluto's y-coordinate
     * @param health Pluto's health
     * @param enemy Array of enemies' health
     * @param obj Array of object statuses
     * @param getKey Whether the key is obtained
     * @param getHummer Whether the hammer is obtained
     */
    public void writeInfo(int x, int y, double health, Enemy[] enemy, Item[] obj, boolean getKey, boolean getHummer,
                          boolean getWood, boolean getMetal) {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode rootNode = objectMapper.createObjectNode();
        ObjectNode plutoNode = objectMapper.createObjectNode();

        // Write Pluto's information
        plutoNode.put("x", x);
        plutoNode.put("y", y);
        plutoNode.put("health", health);
        rootNode.set("pluto", plutoNode);

        // Write enemies' information
        ObjectNode planetsNode = objectMapper.createObjectNode();
        for (int i = 0; i < 4; i++) {
            if (enemy[i] != null) {
                planetsNode.put(Integer.toString(i), enemy[i].currentHealth);
            } else {
                planetsNode.put(Integer.toString(i), 0);
            }
        }
        rootNode.set("planets", planetsNode);

        // Write object statuses
        ObjectNode objectsNode = objectMapper.createObjectNode();
        for (int i = 0; i < 6; i++) {
            if (obj[i] == null) {
                objectsNode.put(Integer.toString(i), false);
            } else {
                objectsNode.put(Integer.toString(i), true);
            }
        }
        rootNode.set("objects", objectsNode);

        // Write key and hammer statuses
        rootNode.put("getHummer", getHummer);
        rootNode.put("getKey", getKey);
        rootNode.put("getWood", getWood);
        rootNode.put("getMetal", getMetal);

        try {
            // Write JSON to file
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/input.json"), rootNode);

        } catch (IOException e) {
            System.out.println("write json error " + e.getMessage());
        }
    }



    public int getX() {
        return x;
    }



    public int getY() {
        return y;
    }

    public double getHealth(){
        return health;
    }
    public double getWeapon(){
        return weapon;
    }

    public double getMaxHealth(){
        return maxHealth;
    }

    public double getPlanetHealth(int i){
        return enemyHealth[i];
    }

    public double getMaxPlanetHealth(int i){
        return maxEnemyHealth[i];
    }

    public double getPlanetWeapon(int i){
        return  enemyWeapon[i];
    }


    public boolean getObjectsInfo(int i){
        return objects[i];
    }


    public boolean hummerInfo(){
        return getHummer;
    }

    public boolean keyInfo(){
        return getKey;
    }
    public boolean woodInfo(){
        return getWood;
    }
    public boolean metalInfo(){
        return getMetal;
    }



}
