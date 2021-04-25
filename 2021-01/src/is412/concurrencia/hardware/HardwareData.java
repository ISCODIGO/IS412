package is412.concurrencia.hardware;

class HardwareData {
    private boolean value;

    public HardwareData(boolean data) {
        this.value = data;
    }

    public void setValue(boolean data) {
        this.value = data;
    }

    public boolean getAndSet(boolean data) {
        boolean oldValue = this.value;
        this.value = data;
        return oldValue;
    }
}
