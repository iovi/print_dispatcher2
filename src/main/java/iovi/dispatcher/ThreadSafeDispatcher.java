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
    Object documentsLock;
    Object printedLock;
    PrintingThread thread;

    public ThreadSafeDispatcher(Printer printer){
        documentsLock =new Object();
        printedLock =new Object();
        this.printer=printer;
        documents=new LinkedList<Document>();
        printed=new ArrayList<>();
        thread=new PrintingThread(printer,this);
        thread.start();

    }

    /**Реализация {@link ThreadSafeDocumentService#addDocument(Document)}, работает аналогично {@link LinkedList#add(Object)} */
    public boolean addDocument(Document document){
        synchronized (documentsLock){
            return documents.add(document);
        }
    }

    /**Реализация {@link ThreadSafeDocumentService#removeDocumentByObject(Document)}, работает аналогично {@link LinkedList#remove()} */
    public boolean removeDocumentByObject (Document document){
        synchronized (documentsLock){
            return documents.remove(document);
        }
    }

    /**Реализация {@link ThreadSafeDocumentService#pollDocument()}, работает аналогично {@link LinkedList#poll()}*/
    public Document pollDocument(){
        synchronized (documentsLock){
            return documents.poll();
        }
    }



    public boolean abortPrinting(Document document) {

        return removeDocumentByObject(document);
    }

    /**Возвращает отсортированный список напечатанных документов
     * @param sortingType порядок сортировки, возможны варианты:
     * <ul>
     * <li>{@link SortingType#BY_PRINT_ORDER} - сортировка в хронологическом порядке печати
     *                    (1ый элемент - первый напечатанный документ, и т.д.)</li>
     * <li>{@link SortingType#BY_DOCUMENT_TYPE} - сортировка в порядке возрастания названия типа документа</li>
     * <li>{@link SortingType#BY_PAPER_FORMAT} - сортировка в порядке возрастания площади листа документа</li>
     * <li>{@link SortingType#BY_PRINT_DURATION} - сортировка в порядке возрастания продолжительности печати</li>
     * <li>ни один из перечисленных - возвращается null</li>
     * </ul>
     */
    public List<Document> getPrinted(SortingType sortingType) {

        List<Document> sortedPrinted=null;
        synchronized (printedLock){
        switch (sortingType){
            case BY_PRINT_ORDER:
                //already sorted
                break;
            case BY_DOCUMENT_TYPE:
                Collections.sort(printed,new Comparator<Document>() {
                    @Override
                    public int compare(Document document1, Document document2) {
                        return document1.getType().getTypeName().compareTo(document2.getType().getTypeName());
                    }
                });
                break;
            case BY_PAPER_FORMAT:
                Collections.sort(printed,new Comparator<Document>() {
                    @Override
                    public int compare(Document document1, Document document2) {
                        return ((Integer)(document1.getType().getPaperHeight()*document1.getType().getPaperWidth()))
                                .compareTo(document2.getType().getPaperHeight()*document2.getType().getPaperWidth());
                    }
                });
                break;
            case BY_PRINT_DURATION:
                Collections.sort(printed,new Comparator<Document>() {
                    @Override
                    public int compare(Document document1, Document document2) {
                        return ((Integer)document1.getType().getPrintDuration())
                                .compareTo(document2.getType().getPrintDuration());
                    }
                });
                break;
            default:
                return null;
        }
        sortedPrinted=new ArrayList<>(printed);
        return sortedPrinted;
        }

    }

    public List<Document> stop() {
        thread.interrupt();
        return documents;
    }

    /**@return среднее время печати в мс*/
    public int getAveragePrintDuration() {
        synchronized (printedLock){
            if (printed.size()==0)
                return 0;
            int allTime=0;
            for (int i=0;i<printed.size();i++)
                allTime+=printed.get(i).getType().getPrintDuration();
            return allTime/printed.size();
        }

    }

    public void print(Document document) {
        addDocument(document);
    }


    public boolean makePrinted(Document document) {
        synchronized (printedLock){
            return printed.add(document);
        }
    }
}
