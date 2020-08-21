/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umair.sftp.services;

/*
 *
 * @author m.umair
 */
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.file.filters.FileListFilter;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import java.util.regex.*;

public class LastModifiedLsEntryFileListFilter implements FileListFilter<LsEntry> {

    private final Logger log = LoggerFactory.getLogger(LastModifiedLsEntryFileListFilter.class);
    private static final long DEFAULT_AGE = 60;

    private volatile long age = DEFAULT_AGE;

    private volatile Map<String, Long> sizeMap = new HashMap<String, Long>();

    private String regexFilter;
    
    LastModifiedLsEntryFileListFilter(String regexFilter) {
        this.regexFilter = regexFilter;
    }
    
    public long getAge() {
        return this.age;
    }

    public void setAge(long age) {
        setAge(age, TimeUnit.SECONDS);
    }

    public void setAge(long age, TimeUnit unit) {
        this.age = unit.toSeconds(age);
    }
    
    @Override
    public List<LsEntry> filterFiles(LsEntry[] files) {

        List<LsEntry> list = new ArrayList<LsEntry>();
        long now = System.currentTimeMillis() / 1000;
        System.out.println("Total Files: " + files.length);

        for (LsEntry file : files) {

            if (file.getAttrs()
                    .isDir()) {
                continue;
            }
            String fileName = file.getFilename();
            Long currentSize = file.getAttrs().getSize();
            Long oldSize = sizeMap.get(fileName);

            if(!Pattern.matches(regexFilter, fileName)){
                System.out.println(fileName + " File does not match regex " + regexFilter);
                continue;
            }
            if(oldSize == null || currentSize.longValue() != oldSize.longValue() ) {
                // putting size in map, will verify in next iteration of scheduler
                sizeMap.put(fileName, currentSize);
                log.info("[{}] old size [{}]  increased to [{}]...", file.getFilename(), oldSize, currentSize);
                continue;
            }

            int lastModifiedTime = file.getAttrs()
                .getMTime();

            if (lastModifiedTime + this.age <= now ) {
                list.add(file);
                sizeMap.remove(fileName);
            } else {
                log.info("File [{}] is still being uploaded...", file.getFilename());
            }
        }
        return list;
    }

}
