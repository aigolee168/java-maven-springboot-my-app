package com.bf;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.StandardOpenOption;

import com.bf.app.util.Strings;

public class FileLockTests {
    
    public static void main(String[] args) throws IOException {
        String filename = args[0];
        if (Strings.isEmpty(filename)) {
            System.err.println("文件名不能为空");
            return;
        }
        File lockFile = new File(filename);
        FileChannel fileChannel = FileChannel.open(lockFile.toPath(), 
                StandardOpenOption.READ, 
                StandardOpenOption.WRITE,
                StandardOpenOption.CREATE);
        FileLock lock = fileChannel.tryLock();
        if (lock == null) {
            System.err.println("不能重复启动应用程序");
            return;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null && !"quit".equals(line)) {
            System.out.println(line);
        }
        System.out.println("Bye!");
        lock.release();
    }

}
