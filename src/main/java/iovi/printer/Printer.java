package iovi.printer;

import iovi.document.Document;

/**Интерфейс принтера - устройства, умеющего выполнять печать в соответвствии с требованиями по времени из постановки задачи*/
public interface Printer {

    void print(Document document);
}
