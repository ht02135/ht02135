package com.hung.utils.parameters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class FileParameters implements Parameters {
    private static Logger log = Logger.getLogger(FileParameters.class);
    
    private String fileName;
    
    public FileParameters(String fileName) {
        this.fileName = fileName;
    }
    
    protected Collection getDataFromFile() {
        log.info("########## getDataFromFile : start ##########");
        log.info("fileName="+fileName);
        
        ArrayList<String[]> data = new ArrayList<String[]>();
        
        try {
            File file = new File(fileName);
            List<String> lines = FileUtils.readLines(file);
            Iterator<String> lineIter = lines.iterator();
            while (lineIter.hasNext()) {
                String line = lineIter.next();
                log.info("line="+line);
                data.add(line.split(","));
            }
        } catch (IOException e) {
            log.error("e="+e);
        }
        
        log.info("########## getDataFromFile : start ##########");
        
        return data;
    }
    
    @Override
    public Collection data() {
        // return this.getDataFromFile("src/test/resources/data/domainUsers.properties");
        return this.getDataFromFile();
    }
}
