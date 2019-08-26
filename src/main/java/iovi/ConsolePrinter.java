package iovi;

import java.util.Arrays;

public class ConsolePrinter implements Printer{
    public void print(Document document) {
       System.out.println(document.getName());
       System.out.print(Arrays.toString(document.getText()));
    }
}
