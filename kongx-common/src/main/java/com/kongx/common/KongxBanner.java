package com.kongx.common;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

public class KongxBanner implements Banner {
    private static final String BANNER = " \\                               \n" +
            " |   ,   __.  , __     ___. _  .-\n" +
            " |  /  .'   \\ |'  `. .'   `  \\,' \n" +
            " |-<   |    | |    | |    |  /\\  \n" +
            " /  \\_  `._.' /    |  `---| /  \\ \n" +
            "                      \\___/      \n";

    private static final String SPRING_BOOT = " :: Spring Boot :: ";

    private static final int STRAP_LINE_SIZE = 42;

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream printStream) {
        this.print(" :: Support kong :: ", "(v2.1.0)", printStream);
        printStream.println(BANNER);
        String copyright = " :: Copyright@2020 :: ";
        String version = SpringBootVersion.getVersion();
        version = (version != null) ? " (v" + version + ")" : "";
        this.print(copyright, "raoxiaoyan", printStream);
        this.print(SPRING_BOOT, version, printStream);

        printStream.println();
    }

    private void print(String title, String msg, PrintStream printStream) {
        StringBuilder padding = new StringBuilder();
        while (padding.length() < STRAP_LINE_SIZE - (msg.length() + title.length())) {
            padding.append(" ");
        }

        printStream.println(
                AnsiOutput.toString(
                        AnsiColor.GREEN, title, AnsiColor.DEFAULT,
                        padding.toString(), AnsiStyle.FAINT, msg
                )
        );
    }
}
