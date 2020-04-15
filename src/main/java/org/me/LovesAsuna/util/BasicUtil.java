package org.me.LovesAsuna.util;

import org.me.LovesAsuna.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BasicUtil {
    public static int ExtraceInt(String string) {
        String s = string.trim();
        String collect = "";
        if (s != null && !"".equals(s)) {
            System.out.println(s.length());
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) >= 48 && s.charAt(i) <= 57) {
                    collect += s.charAt(i);
                }
            }
        }
        return collect.isEmpty() ? 0 : Integer.parseInt(collect);
    }

    public static List<Long> getUserList(String filepath) throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get(filepath));
        List<Long> userList = new ArrayList<>();
        String line = null;
        while ((line = reader.readLine()) != null) {
            userList.add(Long.parseLong(line));
        }
        return userList;
    }

    public static boolean getEnable(String filepath) throws IOException{
        Scanner scanner = new Scanner(new FileReader(new File(filepath)));
        if ("true".equalsIgnoreCase(scanner.nextLine())) {
            return true;
        } else {
            return false;
        }
    }

    public static long getUID(String str) {
        try {
            return Long.valueOf(str);
        }catch(Exception ex) {
            try {
                return Main.CC.getAt(str);
            }catch(Exception ex2) {
                return -1;
            }
        }
    }
}
