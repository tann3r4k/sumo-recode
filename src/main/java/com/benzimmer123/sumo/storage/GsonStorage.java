package com.benzimmer123.sumo.storage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.util.Base64;

import com.benzimmer123.sumo.Sumo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class GsonStorage {
	
	public static Gson gson() {
		return new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter()).setPrettyPrinting().create();
	}

	public static <T> void serialize(T type, String filePath, String setterValue) {
		Gson gson = gson();
		String jsonString = gson.toJson(type);
		File file = new File(Sumo.getInstance().getDataFolder(), filePath);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}

		FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
		fileConfig.set(setterValue, jsonString);

		try {
			fileConfig.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static <T> T deserialize(Class<T> type, String filePath, String setterValue) {
		File file = new File(filePath);
		FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
		String jsonString = fileConfig.getString(setterValue);
		Gson gson = gson();
		T object = gson.fromJson(jsonString, type);
		return object;
	}
	
	public static ItemStack fromBase64(String data) throws IOException {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getMimeDecoder().decode(data));
			BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
			ItemStack item = (ItemStack) dataInput.readObject();
			dataInput.close();
			return item;
		} catch (ClassNotFoundException e) {
			throw new IOException("Unable to decode class type.", e);
		}
	}

	public static String itemStackToBase64(ItemStack item) throws IllegalStateException {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
			dataOutput.writeObject(item);
			dataOutput.close();
			return Base64.getMimeEncoder().encodeToString(outputStream.toByteArray());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to save item stacks.", e);
		}
	}

}
