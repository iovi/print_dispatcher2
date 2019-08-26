package iovi;

public class DocumentType {

    String typeName;
    int paperHeight;
    int paperWidth;
    int printDuration;

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

    public int getPrintDuration() {
        return printDuration;
    }

    public void setPrintDuration(int printDuration) {
        this.printDuration = printDuration;
    }
}
