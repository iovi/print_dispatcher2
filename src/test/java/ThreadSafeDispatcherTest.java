import iovi.dispatcher.PrintDispatcher;
import iovi.dispatcher.ThreadSafeDispatcher;
import iovi.document.Document;
import iovi.document.DocumentType;
import iovi.printer.ConsolePrinter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class ThreadSafeDispatcherTest {
    DocumentType vacationStatement, resignationStatement;

    @Before
    public void setUpTypes(){

        vacationStatement = new DocumentType("vacation",297,210,1000);
        resignationStatement = new DocumentType("resignation",210,297,500);
    }



    @Test
    public void printAndStopTest() {
        Document vacationSidorov=new Document(vacationStatement,"Sidorov Vacation 29.08.19",
                new String[]{"I, Sidorov","Want a vacation very much","from 29.08.19, please"});

        Document vacationPetrov=new Document(vacationStatement,"Petrov Vacation 21.08.19",
                new String[]{"I, Petrov","Need some rest","from 21.08.19, please"});

        Document vacationOnischenko=new Document(vacationStatement,"Onischenko Vacation 30.08.19",
                new String[]{"I, Onischenko","cannot work anymore","from 30.08.19"});

        PrintDispatcher dispatcher=new ThreadSafeDispatcher(new ConsolePrinter());
        try{
            dispatcher.print(vacationOnischenko);
            Thread.sleep(vacationStatement.getPrintDuration());
            dispatcher.print(vacationPetrov);
            dispatcher.print(vacationSidorov);
            List<Document> unprinted=dispatcher.stop();

            /*for (int i=0;i<unprinted.size();i++){
                System.out.println("unprinted: "+unprinted.get(i).getName());
            }*/
            Assert.assertFalse(unprinted.contains(vacationOnischenko));
            Assert.assertFalse(unprinted.contains(vacationPetrov));
            Assert.assertTrue(unprinted.contains(vacationSidorov));
        } catch (InterruptedException e){
            Assert.fail();
        }
    }
}
