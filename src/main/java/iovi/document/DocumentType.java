package iovi.document;

/**Класс типа документа*/
public class DocumentType {

    String typeName;
    int paperHeight;
    int paperWidth;
    int printDuration;

    /**
     @param typeName название типа документа
     @param paperHeight высота документа в мм
     @param paperWidth ширина документа в мм
     @param printDuration время печати документа в мс
     */
    public DocumentType(String typeName, int paperHeight, int paperWidth, int printDuration) {
        this.typeName = typeName;
        this.paperHeight = paperHeight;
        this.paperWidth = paperWidth;
        this.printDuration = printDuration;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getPaperHeight() {
        return paperHeight;
    }

    public void setPaperHeight(int paperHeight) {
        this.paperHeight = paperHeight;
    }

    public int getPaperWidth() {
        return paperWidth;
    }

    public void setPaperWidth(int paperWidth) {
        this.paperWidth = paperWidth;
    }

    /**@return время печати в мс*/
    public int getPrintDuration() {
        return printDuration;
    }

    public void setPrintDuration(int printDuration) {
        this.printDuration = printDuration;
    }
}
