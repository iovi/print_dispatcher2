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
        resignationStatement = new DocumentType("resignation",210,297,2000);
    }



    /**
     * <ul><li>
     *     Отправляет на печать 1ый документ
     * </li>
     * <li>ждет время его печати</li>
     * <li>Отправляет на печать 2ой</li>
     * <li>Отправляет на печать 3ий</li>
     * <li>Останавливает диспетчер, запоминает возвращенный список ненапечатанных документов</li>
     * <li>Проверяет, что 3ий документ вошел в список, а 1-2 - нет </li>
     * </ul>
     */
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
            Thread.sleep(vacationStatement.getPrintDuration());
        } catch (InterruptedException e){
            Assert.fail();
        }
    }


    /**
     * <ul>
     * <li>Отправляет на печать 1ый документ</li>
     * <li>Через небольшую паузу отправляет на печать 2ой</li>
     * <li>Проверяет, что текущее среднее время = 0 (ни один документ не напечатан)</li>
     * <li>Ждет время печати первого</li>
     * <li>Проверяет, что текущее среднее время = время печати первого</li>
     * <li>Ждет время печати второго</li>
     * <li>Проверяет, что текущее среднее время = среднее время между печатью 1го и 2го</li>
     * </ul>
     */
    @Test
    public void averageDurationTest(){
        Document resignationSidorov=new Document(resignationStatement,"Sidorov Vacation 29.08.19",
                new String[]{"I, Sidorov","Want a vacation very much","from 29.08.19, please"});

        Document vacationPetrov=new Document(vacationStatement,"Petrov Vacation 21.08.19",
                new String[]{"I, Petrov","Need some rest","from 21.08.19, please"});

        PrintDispatcher dispatcher=new ThreadSafeDispatcher(new ConsolePrinter());
        try{
            dispatcher.print(vacationPetrov);
            Thread.sleep(100);
            dispatcher.print(resignationSidorov);
            Assert.assertEquals(0,dispatcher.getAveragePrintDuration());
            Thread.sleep(vacationStatement.getPrintDuration());
            Assert.assertEquals(vacationStatement.getPrintDuration(),dispatcher.getAveragePrintDuration());
            Thread.sleep(resignationStatement.getPrintDuration());
            Assert.assertEquals(dispatcher.getAveragePrintDuration(),
                    (vacationStatement.getPrintDuration()+resignationStatement.getPrintDuration())/2);

        } catch (InterruptedException e){
            Assert.fail();
        }
    }


    /**
     * <ul>
     * <li>Отправляет на печать 1ый документ</li>
     * <li>Отправляет на печать 2ой</li>
     * <li>Ждет время, меньшее печати 1го</li>
     * <li>Проверяет, что отмена 2го работает, а 1го - нет</li>
     * </ul>
     */
    @Test
    public void printAndAbortTest(){
        Document vacationSidorov=new Document(vacationStatement,"Sidorov Vacation 29.08.19",
                new String[]{"I, Sidorov","Want a vacation very much","from 29.08.19, please"});

        Document vacationPetrov=new Document(vacationStatement,"Petrov Vacation 21.08.19",
                new String[]{"I, Petrov","Need some rest","from 21.08.19, please"});

        Document vacationOnischenko=new Document(vacationStatement,"Onischenko Vacation 30.08.19",
                new String[]{"I, Onischenko","cannot work anymore","from 30.08.19"});
        PrintDispatcher dispatcher=new ThreadSafeDispatcher(new ConsolePrinter());
        try{
            dispatcher.print(vacationPetrov);
            dispatcher.print(vacationSidorov);
            Thread.sleep(vacationStatement.getPrintDuration()-1);
            Assert.assertTrue(dispatcher.abortPrinting(vacationSidorov));
            Assert.assertFalse(dispatcher.abortPrinting(vacationPetrov));

        } catch (InterruptedException e){
            Assert.fail();
        }
    }
}
