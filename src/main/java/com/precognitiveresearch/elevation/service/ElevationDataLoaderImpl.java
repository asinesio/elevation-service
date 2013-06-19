package com.precognitiveresearch.elevation.service;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.precognitiveresearch.elevation.domain.Coordinate;
import com.precognitiveresearch.elevation.domain.ElevationSegment;

@Service
public class ElevationDataLoaderImpl implements ElevationDataLoader {
	
	private final String baseFileURLString;
	private final String fileExtension;

	@Autowired
	public ElevationDataLoaderImpl(@Value("${srtm.base.url}") String baseFileURLString, @Value("${srtm.file.extension}") String fileExtension) {
		
		
		super();
		this.baseFileURLString = baseFileURLString;
		this.fileExtension = fileExtension;
	}

	@Override
	public ElevationSegment load(Coordinate coordinate) {
		// load a segment for a coordinate
		try {
			List<Short> data = getElevationDataForCoordinate(coordinate);
			return new ElevationSegment(coordinate, data);
		} catch (IOException e) {
			// log the exception.
			e.printStackTrace();
		}
		return new ElevationSegment("test",new ArrayList<Short>());
	}

	private List<Short> getElevationDataForCoordinate(Coordinate coordinate) throws IOException {
		String url = baseFileURLString + coordinate.getSegmentIdentifier() + fileExtension;
		URL fileURL = new URL(url);
		InputStream inputStream = fileURL.openStream();
		BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
		ArchiveInputStream archiveInputStream = null;
		DataInputStream dataInputStream = null;
		try {
			List<Short> data = new ArrayList<Short>(1021 * 1021);
			archiveInputStream = new ArchiveStreamFactory().createArchiveInputStream(bufferedInputStream);
			ArchiveEntry archiveEntry = archiveInputStream.getNextEntry(); // first entry in file
			dataInputStream = new DataInputStream(archiveInputStream);
			long size = archiveEntry.getSize() / 2;  // shorts = 2 bytes
			for (int i = 0; i < size; i++) {
				data.add(dataInputStream.readShort());
			}
			return data;
		} catch (ArchiveException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(bufferedInputStream);
			IOUtils.closeQuietly(archiveInputStream);
			IOUtils.closeQuietly(dataInputStream);
		}
		return new ArrayList<Short>();
		
	}
	

}
