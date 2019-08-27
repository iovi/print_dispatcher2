package iovi;

public interface ThreadSafeDocumentService {
    boolean addDocument(Document document);
    boolean removeDocumentByObject(Document document);
    Document pollDocument();


}
