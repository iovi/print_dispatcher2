package iovi.dispatcher;

import iovi.document.Document;

/**Интерфейс сервиса, который предоставляет возможность потокобезопасно работать со списком документов*/
public interface ThreadSafeDocumentService {

    /**Добавление документа в список*/
    boolean addDocument(Document document);

    /**Удаление документа из списка по ссылке на объект документа*/
    boolean removeDocumentByObject(Document document);

    /**Удаляет первый документ из списка (FIFO) и возвращает его. Если список пуст, возвращает null*/
    Document pollDocument();


}
