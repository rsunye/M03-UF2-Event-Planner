class SunyeRobertoEventTask {
    private String text;
    private boolean isCompleted;

    public SunyeRobertoEventTask(String text) {
        this.text = text;
        this.isCompleted = false;
    }

    public void toggleCompleted() {
        isCompleted = !isCompleted;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public String toString() {
        return text + " - " + (isCompleted ? "[Completada]" : "[Pendiente]");
    }
}