package com.tenniscourts;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.apache.commons.io.IOUtils;

public class ResourceReader {

	public String read(
		final String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		try (InputStream resource = classLoader.getResourceAsStream(fileName)) {
			if (Objects.isNull(resource)) {
				throw new IllegalArgumentException("Could not find file: ".concat(fileName));
			} else {
				return IOUtils.toString(resource, StandardCharsets.UTF_8);
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("Could not load file: ".concat(fileName), e);
		}
	}
}
