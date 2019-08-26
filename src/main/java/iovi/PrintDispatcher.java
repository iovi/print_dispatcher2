package iovi;


import java.util.List;

public interface PrintDispatcher {

    List<Document> stop();
    void print(Document document);
    boolean abortPrinting (Document document);
    List<Document> getPrinted(SortingType sortingType);
    long getAveragePrintDuration();


}
