public class MyList<T> {
    private Object[] data = new Object[10];
    private int size = 0;

    public int size() {
        return size;
    }

    public void add(T value) {
        ensure(size + 1);
        data[size++] = value;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return (T) data[index];
    }

    public boolean contains(T value) {
        return indexOf(value) != -1;
    }

    public int indexOf(T value) {
        for (int i = 0; i < size; i++) {
            Object o = data[i];
            if (o == null) {
                if (value == null) return i;
            } else {
                if (o.equals(value)) return i;
            }
        }
        return -1;
    }

    public void removeAt(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        for (int i = index; i < size - 1; i++) data[i] = data[i + 1];
        data[size - 1] = null;
        size--;
    }

    public void remove(int index) {
        removeAt(index);
    }

    public void removeValue(T value) {
        int idx = indexOf(value);
        if (idx != -1) removeAt(idx);
    }

    @SuppressWarnings("unchecked")
    private void ensure(int cap) {
        if (cap <= data.length) return;
        Object[] n = new Object[data.length * 2];
        for (int i = 0; i < size; i++) n[i] = data[i];
        data = n;
    }
}
