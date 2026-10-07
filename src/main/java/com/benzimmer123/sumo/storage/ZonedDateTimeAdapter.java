package com.benzimmer123.sumo.storage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * Reads both the legacy ISO-8601 strings stored in older Sumo saves and the
 * reflective object form Gson 2.14 writes for {@link ZonedDateTime}.
 */
public final class ZonedDateTimeAdapter extends TypeAdapter<ZonedDateTime> {

	@Override
	public void write(JsonWriter out, ZonedDateTime value) throws IOException {
		if (value == null) {
			out.nullValue();
			return;
		}
		out.value(value.toString());
	}

	@Override
	public ZonedDateTime read(JsonReader in) throws IOException {
		if (in.peek() == JsonToken.NULL) {
			in.nextNull();
			return null;
		}
		if (in.peek() == JsonToken.STRING) {
			return ZonedDateTime.parse(in.nextString());
		}

		LocalDateTime dateTime = null;
		ZoneId zone = null;
		Integer offsetSeconds = null;
		in.beginObject();
		while (in.hasNext()) {
			switch (in.nextName()) {
			case "dateTime":
				dateTime = readDateTime(in);
				break;
			case "zone":
				zone = readZone(in);
				break;
			case "offset":
				offsetSeconds = readOffsetSeconds(in);
				break;
			default:
				in.skipValue();
				break;
			}
		}
		in.endObject();

		if (dateTime == null) {
			throw new JsonSyntaxException("ZonedDateTime is missing dateTime");
		}
		if (zone != null) {
			return dateTime.atZone(zone);
		}
		if (offsetSeconds != null) {
			return dateTime.atOffset(ZoneOffset.ofTotalSeconds(offsetSeconds)).toZonedDateTime();
		}
		throw new JsonSyntaxException("ZonedDateTime is missing zone");
	}

	private static LocalDateTime readDateTime(JsonReader in) throws IOException {
		if (in.peek() == JsonToken.STRING) {
			return LocalDateTime.parse(in.nextString());
		}
		int year = 0;
		int month = 0;
		int day = 0;
		int hour = 0;
		int minute = 0;
		int second = 0;
		int nano = 0;
		boolean hasDate = false;
		in.beginObject();
		while (in.hasNext()) {
			switch (in.nextName()) {
			case "date":
				in.beginObject();
				while (in.hasNext()) {
					switch (in.nextName()) {
					case "year":
						year = in.nextInt();
						break;
					case "month":
						month = in.nextInt();
						break;
					case "day":
						day = in.nextInt();
						break;
					default:
						in.skipValue();
						break;
					}
				}
				in.endObject();
				hasDate = true;
				break;
			case "time":
				in.beginObject();
				while (in.hasNext()) {
					switch (in.nextName()) {
					case "hour":
						hour = in.nextInt();
						break;
					case "minute":
						minute = in.nextInt();
						break;
					case "second":
						second = in.nextInt();
						break;
					case "nano":
						nano = in.nextInt();
						break;
					default:
						in.skipValue();
						break;
					}
				}
				in.endObject();
				break;
			default:
				in.skipValue();
				break;
			}
		}
		in.endObject();
		if (!hasDate) {
			throw new JsonSyntaxException("ZonedDateTime dateTime is missing date");
		}
		return LocalDateTime.of(year, month, day, hour, minute, second, nano);
	}

	private static ZoneId readZone(JsonReader in) throws IOException {
		if (in.peek() == JsonToken.STRING) {
			return ZoneId.of(in.nextString());
		}
		String id = null;
		Integer totalSeconds = null;
		in.beginObject();
		while (in.hasNext()) {
			switch (in.nextName()) {
			case "id":
				id = in.nextString();
				break;
			case "totalSeconds":
				totalSeconds = in.nextInt();
				break;
			default:
				in.skipValue();
				break;
			}
		}
		in.endObject();
		if (id != null) {
			return ZoneId.of(id);
		}
		if (totalSeconds != null) {
			return ZoneOffset.ofTotalSeconds(totalSeconds);
		}
		throw new JsonSyntaxException("ZonedDateTime zone is missing id");
	}

	private static Integer readOffsetSeconds(JsonReader in) throws IOException {
		if (in.peek() == JsonToken.NUMBER) {
			return in.nextInt();
		}
		Integer totalSeconds = null;
		in.beginObject();
		while (in.hasNext()) {
			switch (in.nextName()) {
			case "totalSeconds":
				totalSeconds = in.nextInt();
				break;
			default:
				in.skipValue();
				break;
			}
		}
		in.endObject();
		return totalSeconds;
	}
}
