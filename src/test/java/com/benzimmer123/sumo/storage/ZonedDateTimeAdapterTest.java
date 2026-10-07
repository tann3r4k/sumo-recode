package com.benzimmer123.sumo.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class ZonedDateTimeAdapterTest {

	private static final ZonedDateTime MARCH_17 = ZonedDateTime.of(2025, 3, 17, 1, 0, 0, 0, ZoneId.of("America/Los_Angeles"));

	private final Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter()).create();

	@Test
	void readsLegacyIsoStringDates() {
		ZonedDateTime parsed = gson.fromJson("\"2025-03-17T01:00-07:00[America/Los_Angeles]\"", ZonedDateTime.class);
		assertEquals(MARCH_17.toInstant(), parsed.toInstant());
		assertEquals("America/Los_Angeles", parsed.getZone().getId());
	}

	@Test
	void readsPaperGsonObjectDates() {
		String json = """
				{
				  "dateTime": {
				    "date": { "year": 2025, "month": 3, "day": 17 },
				    "time": { "hour": 1, "minute": 0, "second": 0, "nano": 0 }
				  },
				  "offset": { "totalSeconds": -25200 },
				  "zone": { "id": "America/Los_Angeles" }
				}
				""";
		ZonedDateTime parsed = gson.fromJson(json, ZonedDateTime.class);
		assertEquals(MARCH_17.toInstant(), parsed.toInstant());
		assertEquals(1, parsed.getHour());
	}

	@Test
	void writesIsoStringsThatRoundTrip() {
		String json = gson.toJson(MARCH_17, ZonedDateTime.class);
		assertTrue(json.startsWith("\""));
		assertEquals(MARCH_17.toInstant(), gson.fromJson(json, ZonedDateTime.class).toInstant());
	}

	@Test
	void arenaSavesAreJsonFilesOnly() {
		assertTrue(ArenaFiles.isArenaSave("spawn.json"));
		assertFalse(ArenaFiles.isArenaSave("spawn.json.bak"));
	}
}
