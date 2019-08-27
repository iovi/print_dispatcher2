package iovi;

import java.util.*;

public class PrintDispatcherImpl implements PrintDispatcher {
    LinkedList<Document> documents;
    Printer printer;
    Object lock;

    private boolean addToDocuments(Document document){
        synchronized (lock){
            return documents.add(document);
        }
    }

    private boolean removeFromDocuments(Document document){
        synchronized (lock){
            if (documents.getFirst()==document)
                return false;
            else{
                documents.remove(document);
                return true;
            }
        }
    }



    public PrintDispatcherImpl(Printer printer){
        this.printer=printer;
        documents=new LinkedList<Document>();


    }

    public boolean abortPrinting(Document document) {
        return false;
    }

    public List<Document> getPrinted(SortingType sortingType) {
        return null;
    }

    public List<Document> stop() {
        return null;
    }

    public long getAveragePrintDuration() {
        return 0;
    }

    public void print(Document document) {
        addToDocuments(document);
    }
}
