package experiment.setting;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class SettingParserEngine 
{
	List<String> headers = null;
	HashMap<Integer, List<String>> settings = new HashMap();
    
    public SettingParserEngine(File settingFile)
    {
    	try {
			List<String> lines = FileUtils.readLines(settingFile);
			headers = Arrays.asList(lines.get(0).split(","));	// split properly split empty column value
			
			// arrh, headers will also be part of settings at row 0
			for (int i=0; i<lines.size(); i++) {
				// lines.get(i).split(",") return array of string
				// Arrays.asList(ArrayOfString) return list of string
				settings.put(new Integer(i), Arrays.asList(lines.get(i).split(",")));
			}
			System.out.println("settings.size()="+settings.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public String parse(int rowIndex, String colName) throws IllegalArgumentException {
    	if (rowIndex < 1) throw new IllegalArgumentException("rowIndex="+rowIndex);
    	if (rowIndex > settings.size()) throw new IllegalArgumentException("rowIndex="+rowIndex+" greater than settings size");
    	
    	int colIndex = headers.indexOf(colName);
    	if (colIndex == -1) throw new IllegalArgumentException("colName="+colName+" not exist in headers");
    	
    	List<String> setting = settings.get(new Integer(rowIndex));
    	String parsedValue = setting.get(colIndex);
    	
    	return parsedValue;
    }
    
    public void displaySettings() {
    	System.out.print("[");
    	for (int i=0; i<headers.size(); i++) {
    		
    		System.out.print(headers.get(i)+",");
    		
    	}
    	System.out.println("]");
    	
    	System.out.println("[");
    	for (int i=0; i<settings.size(); i++) {
    		List<String> setting = settings.get(new Integer(i));
    		System.out.print("[");
    		for (int j=0; j<setting.size(); j++) {
    			System.out.print(setting.get(j)+",");
    		}
    		System.out.println("]");
    	}
    	System.out.println("]");
    }
    
    public void test() {
    	System.out.println("|Integer|=="+parse(1, "Type"));
    	System.out.println("|Bolean Seeting|=="+parse(2, "Name"));
    	System.out.println("||=="+parse(3, "Description2"));
    }
    
    public static void main( String[] args )
    {
    	File settingFile = new File("src/main/resources/settings.properties");
    	SettingParserEngine settingParserEngine = new SettingParserEngine(settingFile);
    	settingParserEngine.displaySettings();
    	settingParserEngine.test();
    }
}
