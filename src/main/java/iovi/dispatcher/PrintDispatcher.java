package iovi.dispatcher;


import iovi.document.Document;

import java.util.List;

/**Интерфейс диспетчера в соответствии с требованиями*/
public interface PrintDispatcher {

    /**Остановка диспетчера*/
    List<Document> stop();

    /**Прием документа на печать*/
    void print(Document document);

    /**Отмена печати документа*/
    boolean abortPrinting (Document document);

    /**Получение отсоритрованного списка напечатанных документов*
     * @param sortingType тип соритровки из {@link SortingType}
     */
    List<Document> getPrinted(SortingType sortingType);

    /**Получение среднего времени печати в мс. Возвращает 0, если ни один документ не напечатан*/
    long getAveragePrintDuration();


}
