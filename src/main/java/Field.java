public class Field {

    private int startingX, startingY;
    private boolean editable;

    public Field(int startingX, int startingY) {
        this.startingX = startingX;
        this.startingY = startingY;
        this.editable = false;
    }

    public int getStartingX() {
        return startingX;
    }

    public void setStartingX(int startingX) {
        this.startingX = startingX;
    }

    public int getStartingY() {
        return startingY;
    }

    public void setStartingY(int startingY) {
        this.startingY = startingY;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
