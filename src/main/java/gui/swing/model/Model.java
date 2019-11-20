package gui.swing.model;

public class Model {

    private String textPathFile = "";
    private String textOfThreadStatus;

    public String getTextPathFile() {
        return textPathFile;
    }

    public void setTextPathFile(String textPathFile) {
        this.textPathFile = textPathFile;
    }

    public String getTextOfThreadStatus() {
        return textOfThreadStatus;
    }

    public void setTextOfThreadStatus(String textOfThreadStatus) {
        synchronized(this) {
            this.textOfThreadStatus = textOfThreadStatus;
        }
    }

}
