package iovi;

import java.util.*;

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


    public boolean addDocument(Document document){
        synchronized (lock){
            return documents.add(document);
        }
    }

    public boolean removeDocumentByObject (Document document){
        synchronized (lock){
            return documents.remove(document);
        }
    }

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

    public long getAveragePrintDuration() {
        if (printed.size()==0)
            return 0;
        long allTime=0;
        for (int i=0;i<printed.size();i++)
            allTime+=printed.get(i).getType().getPrintDuration();
        return allTime/printed.size();

    }

    public void print(Document document) {
        boolean added=addDocument(document);
        if (added)
            printed.add(document);

    }
}
