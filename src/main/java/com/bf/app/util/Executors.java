package com.bf.app.util;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class Executors {

    public static void repeating(int times, Runnable r) {
        for (int i = 0; i < times; i++) {
            r.run();
        }
    }

    public static void repeating(int times, Consumer<Integer> c) {
        for (int i = 0; i < times; i++) {
            c.accept(i);
        }
    }

    public static void repeatingIOTask(int times, RepeatingIOTask task) throws IOException {
        for (int i = 0; i < times; i++) {
            task.run(i);
        }
    }

    public static void repeatingExceptionTask(int times, RepeatingExceptionTask task) throws Exception {
        for (int i = 0; i < times; i++) {
            task.run(i);
        }
    }

    public static void tryExceptionTask(ExceptionTask task) {
        try {
            task.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T tryCallable(Callable<T> call) {
        try {
            return call.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void tryIOTask(IOTask task) {
        try {
            task.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FunctionalInterface
    public static interface RepeatingIOTask {
        void run(int times) throws IOException;
    }

    @FunctionalInterface
    public static interface RepeatingExceptionTask {
        void run(int times) throws Exception;
    }

    @FunctionalInterface
    public static interface ExceptionTask {
        void run() throws Exception;
    }

    @FunctionalInterface
    public static interface IOTask {
        void run() throws IOException;
    }

}
