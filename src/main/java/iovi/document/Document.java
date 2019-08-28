package iovi.document;

/**Класс документа для печати*/
public class Document {

    public DocumentType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String[] getText() {
        return text;
    }

    DocumentType type;
    String name;
    String [] text;

    public Document(DocumentType type, String name, String[] text){
        this.name=name;
        this.text=text;
        this.type=type;
    }

}
