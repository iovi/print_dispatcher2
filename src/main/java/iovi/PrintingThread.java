package iovi;


import iovi.dispatcher.ThreadSafeDocumentService;
import iovi.document.Document;
import iovi.printer.Printer;

/**Поток, выполняющий основную логику по печати документов:
 * <ul>
 *     <li>получение первого документа в очереди</li>
 *     <li>вызов функции печати у принтера</li>
 *     <li>ожидание конца печати (время печати известно из типа документа)</li>
 *     <li>помечает документ как напечатанный</li>
 *     <li>повторение предыдущих шагов</li>
 * </ul>
 */
public class PrintingThread extends Thread{

    Printer printer;
    ThreadSafeDocumentService documentService;
    /**@param printer принтер, который будет печатать документы
     * @param documentService сервис для работы с документами,
     * будет использоваться для потокобезопасного получения печатаемых документов*/
    public PrintingThread(Printer printer, ThreadSafeDocumentService documentService){
        this.printer=printer;
        this.documentService=documentService;
    }

    public void run() {
        try{
            while(!Thread.currentThread().interrupted()){
                Document document=documentService.pollDocument();

                if (document!=null){
                    printer.print(document);
                    Thread.sleep(document.getType().getPrintDuration());
                    documentService.makePrinted(document);
                }
            }
        }catch(InterruptedException e){
            System.err.println("application message: "+e.getMessage());
        }

    }

}
