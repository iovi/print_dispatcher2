package iovi.dispatcher;

import iovi.PrintingThread;
import iovi.document.Document;
import iovi.printer.Printer;

import java.util.*;

/**Класс диспетчера печати для использования извне*/
public class ThreadSafeDispatcher implements PrintDispatcher, ThreadSafeDocumentService {
    LinkedList<Document> documents;
    ArrayList<Document> printed;
    Printer printer;
    Object lock;
    PrintingThread thread;

    public ThreadSafeDispatcher(Printer printer){
        lock=new Object();
        this.printer=printer;
        documents=new LinkedList<Document>();
        printed=new ArrayList<>();
        thread=new PrintingThread(printer,this);
        thread.start();

    }

    /**Реализация {@link ThreadSafeDocumentService#addDocument(Document)}, работает аналогично {@link LinkedList#add(Object)} */
    public boolean addDocument(Document document){
        synchronized (lock){
            return documents.add(document);
        }
    }

    /**Реализация {@link ThreadSafeDocumentService#removeDocumentByObject(Document)}, работает аналогично {@link LinkedList#remove()} */
    public boolean removeDocumentByObject (Document document){
        synchronized (lock){
            return documents.remove(document);
        }
    }

    /**Реализация {@link ThreadSafeDocumentService#pollDocument()}, работает аналогично {@link LinkedList#poll()}*/
    public Document pollDocument(){
        synchronized (lock){
            return documents.poll();
        }
    }



    public boolean abortPrinting(Document document) {

        return removeDocumentByObject(document);
    }

    public List<Document> getPrinted(SortingType sortingType) {
        return null;
    }

    public List<Document> stop() {
        thread.interrupt();
        return documents;
    }

    /**@return среднее время печати в мс*/
    public long getAveragePrintDuration() {
        if (printed.size()==0)
            return 0;
        long allTime=0;
        for (int i=0;i<printed.size();i++)
            allTime+=printed.get(i).getType().getPrintDuration();
        return allTime/printed.size();

    }

    public void print(Document document) {
        addDocument(document);
    }


    public boolean makePrinted(Document document) {
        return printed.add(document);
    }
}
