package utils;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class YAMLconfig {
    public static String parseYAML(){
        try (InputStream inputStream = new FileInputStream("D:\\Java\\LabReport\\src\\main\\java\\config.yml")) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            Map<String, Object> data1= (Map<String, Object>) data.get("basic-data");

            System.out.println(data1.get("base-uri"));
            return data1.get("base-uri").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }



}
