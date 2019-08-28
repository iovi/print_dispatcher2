package iovi.printer;

import iovi.document.Document;

import java.util.Arrays;

/**Простая реализация принтера, позволяющая вывести данные в консоль*/
public class ConsolePrinter implements Printer{
    public void print(Document document) {
       System.out.println(document.getName());
       System.out.println(Arrays.toString(document.getText()));
    }
}
