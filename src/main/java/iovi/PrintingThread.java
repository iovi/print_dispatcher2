package iovi;

import java.util.LinkedList;

public class PrintingThread extends Thread{

    Printer printer;
    ThreadSafeDocumentService documentService;
    public PrintingThread(Printer printer, ThreadSafeDocumentService documentService){
        this.printer=printer;
        this.documentService=documentService;
    }

    public void run() {
        System.out.print("try to try");
        try{
            while(!Thread.currentThread().interrupted()){
                Document document=documentService.pollDocument();

                if (document!=null){
                    System.out.print("doc is not null");
                    printer.print(document);
                    Thread.sleep(document.getType().getPrintDuration());
                } else
                    System.out.print("doc is null");
            }
        }catch(InterruptedException e){
            System.err.print("custom error: "+e.getMessage());
        }

    }

}
