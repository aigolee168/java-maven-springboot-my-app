package com.bf.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.StandardOpenOption;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyAppTests {

	@Test
	void contextLoads() {
	}
	
	public static void main(String[] args) throws IOException {
		File lockFile = new File("/Users/limin/coding-workspace/java-maven-springboot-my-app/target/.lock");
		FileChannel fileChannel = FileChannel.open(lockFile.toPath(), 
				StandardOpenOption.READ, 
				StandardOpenOption.WRITE,
				StandardOpenOption.CREATE);
		FileLock lock = fileChannel.tryLock();
		if (lock == null) {
			System.err.print("不能重复启动应用程序");
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
