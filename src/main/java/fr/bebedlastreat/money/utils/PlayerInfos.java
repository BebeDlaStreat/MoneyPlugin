package fr.bebedlastreat.money.utils;

import fr.bebedlastreat.money.Main;
import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class PlayerInfos {

    public static UUID getUuidFromName(String name) {
        String url = "https://api.mojang.com/users/profiles/minecraft/"+name;
        try {
            @SuppressWarnings("deprecation")
            String UUIDJson = IOUtils.toString(new URL(url));
            if(UUIDJson.isEmpty()) return null;
            JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(UUIDJson);
            StringBuilder uuidBuilder = new StringBuilder(UUIDObject.get("id").toString());
            uuidBuilder.insert(20, "-").insert(16, "-").insert(12, "-").insert(8, "-");
            return UUID.fromString(uuidBuilder.toString());
        } catch (IOException | ParseException e) {
        }

        return null;
    }
}