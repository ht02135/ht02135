package experiment.simplefileutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class SimpleFileUtils  {
	
	public static void FileUtils_copyFile() {
		String source = "src/main/resources/settings.properties";
		String target = "src/main/resources/";
		File sourceFile = new File(source);
		File targetFile = new File(target+"FileUtils_copyFile.txt");
		
		System.out.println("sourceFile.toPath()="+sourceFile.toPath());
		System.out.println("targetFile.toPath()="+targetFile.toPath());
		
		try {
			FileUtils.copyFile(sourceFile, targetFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void FileUtils_readFileToByteArray() {
		String source = "src/main/resources/settings.properties";
		String target = "src/main/resources/";
		File sourceFile = new File(source);
		File targetFile = new File(target+"writeByteArrayToFile.txt");
		
		System.out.println("sourceFile.toPath()="+sourceFile.toPath());
		System.out.println("targetFile.toPath()="+targetFile.toPath());
		
		try {
			byte[] bytes = FileUtils.readFileToByteArray(sourceFile);
			FileUtils.writeByteArrayToFile(targetFile, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void FileUtils_readLines() {
		String source = "src/main/resources/settings.properties";
		String target = "src/main/resources/";
		File sourceFile = new File(source);
		File targetFile = new File(target+"writeLines.txt");
		
		System.out.println("sourceFile.toPath()="+sourceFile.toPath());
		System.out.println("targetFile.toPath()="+targetFile.toPath());
		
		try {
			List<String> lines = FileUtils.readLines(sourceFile);
			FileUtils.writeLines(targetFile, lines);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void IOUtils_copy() {
		String source = "src/main/resources/settings.properties";
		String target = "src/main/resources/";
		File sourceFile = new File(source);
		File targetFile = new File(target+"IOUtils_copy.txt");
		File targetFile2 = new File(target+"IOUtils_copy2.txt");
		
		try {
			InputStream inputStream = new FileInputStream(sourceFile);
			
			// use StringWriter
			OutputStream outputStream = new FileOutputStream(targetFile);
			StringWriter stringWriter = new StringWriter();
			
			IOUtils.copy(inputStream, stringWriter); 
			String outputString = stringWriter.toString();
			System.out.println("outputWriter outputString="+outputString);
			IOUtils.write(outputString, outputStream);
			
			outputStream.flush();
			outputStream.close();
			
			// use OutputStreamWriter
			OutputStream outputStream2 = new FileOutputStream(targetFile2);
			Writer outputWriter = new OutputStreamWriter(outputStream2);
			outputWriter.write(outputString);
			outputWriter.close();
			
			outputStream2.flush();
			outputStream2.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public static void main( String[] args )
    {
    	String[] stringArray = new String[]{"12","23","34"};
    	List stringList = Arrays.asList(stringArray);
    	String[] stringArray2 = (String[]) stringList.toArray(new String[stringList.size()]);
    	
    	System.out.println("stringArray="+stringArray);
    	System.out.println("stringList="+stringList);
    	System.out.println("stringArray2="+stringArray2);
    	
    	FileUtils_copyFile();
    	FileUtils_readFileToByteArray();
    	FileUtils_readLines();
    	IOUtils_copy();
    }
}
