package com.precognitiveresearch.elevation.service;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.precognitiveresearch.elevation.domain.ElevationSegment;
import com.precognitiveresearch.elevation.exception.UnexpectedElevationQueryException;

@Service
public class ElevationDataLoaderImpl implements ElevationDataLoader {

	private static final Logger LOG = LoggerFactory
			.getLogger(ElevationDataLoaderImpl.class);

	private final String baseFileURLString;
	private final String fileExtension;

	@Autowired
	public ElevationDataLoaderImpl(
			@Value("${srtm.base.url}") String baseFileURLString,
			@Value("${srtm.file.extension}") String fileExtension) {
		super();
		this.baseFileURLString = baseFileURLString;
		this.fileExtension = fileExtension;
	}

	@Override
	@Cacheable("elevationSegments")
	public ElevationSegment load(String segmentIdentifier) {
		// load a segment for a coordinate
		try {
			LOG.trace("Starting load of elevation segment " + segmentIdentifier);
			List<Short> data = getElevationDataForSegment(segmentIdentifier);
			LOG.trace("Completed load of elevation segment "
					+ segmentIdentifier);
			return ElevationSegment.newSegmentFromIdentifier(segmentIdentifier, data);
		} catch (FileNotFoundException e) {
			LOG.error("Elevation segment " + segmentIdentifier + " not found.", e);
			return null;
		} catch (IOException e) {
			throw new UnexpectedElevationQueryException(
					"Error loading elevation segment " + segmentIdentifier, e);
		}
	}

	private List<Short> getElevationDataForSegment(String segmentIdentifier)
			throws IOException {
		String url = baseFileURLString + segmentIdentifier + fileExtension;
		URL fileURL = new URL(url);
		InputStream inputStream = fileURL.openStream();
		ZipInputStream zipInputStream = null;
		DataInputStream dataInputStream = null;
		try {
			zipInputStream = new ZipInputStream(inputStream);
			ZipEntry archiveEntry = zipInputStream.getNextEntry();
			dataInputStream = new DataInputStream(zipInputStream);
			int size = (int) (archiveEntry.getSize() / 2); // shorts = 2 bytes

			List<Short> data = new ArrayList<Short>(size);
			LOG.info("Loading " + size + " elevations into data array.");
			for (int i = 0; i < size; i++) {
				data.add(dataInputStream.readShort());
			}
			return data;
		} catch (IOException e) {
			throw new UnexpectedElevationQueryException(
					"Error unpacking SRTM3 archive for identifier "
							+ segmentIdentifier, e);
		} finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(zipInputStream);
			IOUtils.closeQuietly(dataInputStream);
		}
	}
}