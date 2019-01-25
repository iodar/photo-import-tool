package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Hex;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import managers.ImageFileManager;
import model.ImageFile;
import utility.ExifInfoUtility;

public class Main {

	private static final String ROOT_DIR = "D:/040_Bilder/Bilder_final"; 
//	private static final String ROOT_DIR = "D:\\040_Bilder\\Bilder_final\\2018_Rocco_und_Heike\\2018_Bilder\\06\\2018-06-08\\WP_20180608_020.jpg"; 
	
	public static void main(String[] args) throws ImageProcessingException, IOException {
		List<ImageFile> corruptImages = new ArrayList<>();
		List<ImageFile> imagesWithCorruptExifInfo = new ArrayList<>();
//		"D:/040_Bilder/Bilder_final"
		
		ImageFileManager.createImagesFromDirectory(new File(ROOT_DIR)).forEach((file) -> {
			if (file != null) {
				if (file.getFileName() == null & file.getAbsoluteFilePath() == null
						& file.getExifInfo() == null) {
				}
				if (file.getFileName() != null & file.getAbsoluteFilePath() != null
						& file.getExifInfo() == null) {
					imagesWithCorruptExifInfo.add(file);
				}
				if (file.getFileName() != null & file.getAbsoluteFilePath() != null
						& file.getExifInfo() != null) {
					file.toString();
				}
			}
		});
		
		System.out.println("\n------------- Images that could not been read -------------\n");
		
		if (imagesWithCorruptExifInfo != null) {
			imagesWithCorruptExifInfo.parallelStream().forEach((file) -> {
				final String message = String.format("Corrupt Metadata: %s", file.toString());
				System.out.println(message);
			});
		}
	}
	
	private static void readFiles() {
		Path rootPath = Paths.get(ROOT_DIR); 
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(rootPath)) {
			for (Path entry: stream) {
				File file = entry.toFile();
				
				String output = String.format("name: %s, dir: %s", file.getName(), file.isDirectory());
				
				System.out.println(output);
			}
		} catch (IOException ioException) {
			System.err.println("Problem occured while getting the dir stream.");
			ioException.printStackTrace();
		}
	}
	
	private static void readMetadataOfFile(File file) {
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(file);
			List<String> metadataAsList = new ArrayList<>();
			
			for (Directory directory : metadata.getDirectories()) {
				for (Tag tag : directory.getTags()) {
//					if (directory.getName() == EXIF_INFO.toString()) {
//						final String metadataEntry = String.format("[%s] %s = %s",
//								directory.getName(), tag.getTagName(), tag.getDescription());
//						metadataAsList.add(metadataEntry);
//					}
					
					final String metadataEntry = String.format("[%s] %s = %s",
							directory.getName(), tag.getTagName(), tag.getDescription());
					metadataAsList.add(metadataEntry);
					
				}
			}
			
			metadataAsList.stream()
				.forEach(System.out::println);
			
		} catch (ImageProcessingException imageProcessingException) {
			final String errorMessage = String.format("Processing metadata of file [%s] failed.", file.getName());
			System.err.println(errorMessage);
			imageProcessingException.printStackTrace();
		} catch (IOException ioException) {
			System.err.println("IO error occured.");
			ioException.printStackTrace();
		}
		
	}
	
	private static void getFileHash(File file) {
		try {
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
			byte[] bytesOfFile = Files.readAllBytes(file.toPath());
			byte[] hash = sha256.digest(bytesOfFile);
			final String hashAsHex = Hex.encodeHexString(hash);
			final String MESSAGE = String.format("Sha-256 [%s] = %s", file.getName(), hashAsHex);
			System.out.println(MESSAGE);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void testMetadata() throws ImageProcessingException, IOException {
		final File testdataDir = new File("src/test/data");
		final File file = new File(testdataDir.getAbsolutePath() + "/noPictureFile.txt");
		ExifInfoUtility.getMetadata(file);
	}
}
