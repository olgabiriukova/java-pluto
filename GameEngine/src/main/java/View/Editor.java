package View;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for allowing user to edit the game map
 */
public class Editor {

    public JTextArea textArea = new JTextArea(); // Text area to display map
    public JTextArea jsonArea = new JTextArea(); // Text area to display JSON data

    public String filePath = "src/main/game_map.txt"; // File path for the game map

    private String plutoHealth, plutoWeapon; // Pluto's health and weapon
    private double[][] planets = new double[4][2]; // Array to store planet data
    private String[] planetsName = {"Mercury", "Mars", "Neptune", "Saturn"}; // Names of planets

    /**
     * Constructor for the Editor class
     */
    public Editor() {
        JFrame editorFrame = new JFrame("Editor"); // Creating a frame for the editor
        editorFrame.setLayout(new BorderLayout());
        editorFrame.setResizable(true);
        editorFrame.setBounds(200, 50, 961, 700);
        editorFrame.setVisible(true);
        JPanel editorPanel = new JPanel(); // Panel to contain editor components
        editorPanel.setLayout(null);
        editorPanel.setBounds(200, 50, 961, 700);
        textArea.setOpaque(false);
        textArea.setForeground(Color.LIGHT_GRAY);
        textArea.setBounds(10, 10, 500, 570);
        jsonArea.setOpaque(false);
        jsonArea.setForeground(Color.LIGHT_GRAY);
        jsonArea.setBounds(610, 300, 200, 700);
        Font font = new Font("Calibri", Font.BOLD, 14);
        jsonArea.setFont(font);

        editorPanel.add(textArea);
        editorPanel.add(jsonArea);

        // Creating and configuring the Save button
        JButton save = new JButton("Save");
        save.setBounds(410, 600, 145, 43);
        ClassLoader classLoader = Editor.class.getClassLoader();
        URL saveURL = classLoader.getResource("menu/save_button.png");
        save.setIcon(new ImageIcon(saveURL));
        save.addActionListener(e -> {
            writeToMap();
            writeToJson();
            editorFrame.dispose();
        });

        // Adding instruction label
        String instruction = "<html>Each number indicates an object on the playing field:<br>" +
                "0 - an empty field,<br> 1 - Pluto,<br> 2 - Mercury,<br> 3 - Mars,<br> 4 - Neptune,<br>" +
                " 5 - Saturn,<br> 6 - the key,<br> 7 - satellite,<br> 8 - door,<br> " +
                "9 and 10 - hearts that add health<br> 11 - hammer.</html>";
        JLabel instructionLabel = new JLabel(instruction);
        instructionLabel.setForeground(Color.LIGHT_GRAY);
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        instructionLabel.setBounds(610, 15, 200, 300);

        // Adding components to the editor panel
        editorPanel.add(save);
        editorPanel.add(instructionLabel);
        JLabel background = new JLabel();
        URL imageURL = classLoader.getResource("menu/editor_background.png");
        background.setIcon(new ImageIcon(imageURL));
        background.setBounds(-3, 0, 1000, 700);
        editorPanel.add(background);
        editorFrame.add(editorPanel);

        // Displaying the map and JSON data
        showMap();
        showJson();
    }

    /**
     * Displays the map to the user
     */
    public void showMap() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();

            textArea.setText(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the map has correct elements
     *
     * @return true if map is valid, false otherwise
     */
    public boolean checkMap() {
        // Checking if the map has correct number of elements
        String text = textArea.getText();
        String[] lines = text.split("\n");
        int elements = 0;
        for (String line : lines) {
            String[] words = line.split("\\s+");
            elements += words.length;
        }
        if (elements != 1750) {
            String message = "Can't save, because map doesn't have enough elements";
            JOptionPane.showMessageDialog(null, message);
            return false;
        }

        // Checking if all required elements are present
        boolean[] elementsList = new boolean[12];
        for (int i = 0; i < 12; i++) {
            String element = String.valueOf(i);
            elementsList[i] = textArea.getText().indexOf(element) != -1;
            ;
        }
        for (int i = 0; i < 12; i++) {
            if (!elementsList[i]) {
                JOptionPane.showMessageDialog(null, "element " + i + " must be on Map");
                return false;
            }
        }
        return true;
    }

    /**
     * Writes changes to the map
     */
    public void writeToMap() {
        try {
            if (checkMap()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
                writer.write(textArea.getText());
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays JSON data to the user
     */
    public void showJson() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(new File("src/main/settings.json"));
            JsonNode plutoNode = rootNode.get("pluto");
            JsonNode weaponNode = plutoNode.get("weapon");
            JsonNode healthNode = plutoNode.get("health");

            plutoHealth = healthNode.asText();
            plutoWeapon = weaponNode.asText();
            jsonArea.append("Pluto health: " + plutoHealth + "\n");
            jsonArea.append("Pluto weapon: " + plutoWeapon + "\n");

            JsonNode planetsNode = rootNode.get("planets");
            for (int i = 0; i < 4; i++) {
                JsonNode planetNode = planetsNode.get(i);
                planets[i][0] = planetNode.path("weapon").asDouble();
                planets[i][1] = planetNode.path("health").asDouble();
            }
            for (int i = 0; i < planets.length; i++) {
                jsonArea.append(planetsName[i] + "\n" + " weapon: " + planets[i][0] + "\n"
                        + " health: " + planets[i][1] + "\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void writeToJson() {
        String[] lines = jsonArea.getText().split("\n");
        List<Double> planetWeapons = new ArrayList<>();
        List<Double> planetHealths = new ArrayList<>();
        Double plutoWeapon = null;
        Double plutoHealth = null;

        // Parsing JSON data
        for (String line : lines) {
            if (line.startsWith("Pluto")) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    if (key.equals("Pluto health")) {
                        plutoHealth = Double.parseDouble(value);
                    } else if (key.equals("Pluto weapon")) {
                        plutoWeapon = Double.parseDouble(value);
                    }
                }

            } else {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String value = parts[1].trim();
                    if (parts[0].trim().equals("weapon")) {
                        planetWeapons.add(Double.parseDouble(value));
                    } else if (parts[0].trim().equals("health")) {
                        planetHealths.add(Double.parseDouble(value));
                    }
                }
            }
        }

        // Validating parsed data
        if (plutoWeapon == null || plutoHealth == null || planetWeapons.size() != 4 || planetHealths.size() != 4) {
            JOptionPane.showMessageDialog(null, "Incorrect data");
            return;
        }

        // Writing JSON data to file
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode rootNode = objectMapper.createObjectNode();
            ObjectNode plutoNode = objectMapper.createObjectNode();
            plutoNode.put("weapon", plutoWeapon);
            plutoNode.put("health", plutoHealth);
            rootNode.set("pluto", plutoNode);


            ArrayNode planetsNode = objectMapper.createArrayNode();
            for (int i = 0; i < 4; i++) {
                ObjectNode planetNode = objectMapper.createObjectNode();
                planetNode.put("weapon", planetWeapons.get(i));
                planetNode.put("health", planetHealths.get(i));
                planetsNode.add(planetNode);
            }
            rootNode.set("planets", planetsNode);
            ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();

            writer.writeValue(new File("src/main/settings.json"), rootNode);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
