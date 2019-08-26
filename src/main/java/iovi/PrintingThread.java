package iovi;

import java.util.LinkedList;

public class PrintingThread extends Thread{

    Printer printer;
    LinkedList<Document> docs;
    public PrintingThread(Printer printer, LinkedList<Document> docs){
        this.printer=printer;
        this.docs=docs;
    }

    public void run() {
        try{
            while(docs.size()!=0){
                printer.print(docs.getFirst());
                Thread.sleep(docs.getFirst().getType().getPrintDuration());
                docs.removeFirst();
            }
        }catch(InterruptedException e){
            System.err.print("custom error: "+e.getMessage());
        }

    }

}
