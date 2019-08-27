import iovi.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class ThreadSafeDispatcherTest {
    DocumentType vacationApplication;

    @Before
    public void setUpTypes(){
        vacationApplication = new DocumentType("vacation",297,210,1000);
    }


    static boolean isDocumentInList(List<Document> docs, Document document){
        for (Document doc:docs) {
            if (doc.equals(document))
                return true;
        }
        return false;
    }


    @Test
    public void printTest() {
        Document vacationSidorov=new Document(vacationApplication,"Sidorov Vacation 29.08.19",
                new String[]{"I, Sidorov","Want a vacation very much","from 29.08.19, please"});

        Document vacationPetrov=new Document(vacationApplication,"Petrov Vacation 21.08.19",
                new String[]{"I, Petrov","Need some rest","from 21.08.19, please"});

        Document vacationOnischenko=new Document(vacationApplication,"Onischenko Vacation 30.08.19",
                new String[]{"I, Onischenko","cannot work anymore","from 30.08.19"});

        PrintDispatcher dispatcher=new ThreadSafeDispatcher(new ConsolePrinter());
        try{
            //dispatcher.print(vacationOnischenko);
            Thread.sleep(10000);/*
            List<Document> docs=printDispatcher.getPrinted(SortingType.BY_DOCUMENT_TYPE);
            Assert.assertTrue(isDocumentInList(docs,vacationOnischenko));

            printDispatcher.print(vacationSidorov);
            Thread.sleep(vacationApplication.getPrintDuration());
            docs=printDispatcher.getPrinted(SortingType.BY_DOCUMENT_TYPE);

            Assert.assertTrue(isDocumentInList(docs,vacationSidorov));
            Assert.assertFalse(isDocumentInList(docs,vacationPetrov));*/

        } catch (InterruptedException e){}
    }
}
