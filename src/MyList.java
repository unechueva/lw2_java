public class MyList<T>
{
    private Object[] data;
    private int size;

    public MyList()
    {
        this.data = new Object[10];
        this.size = 0;
    }

    public void add(T item)
    {
        if (size == data.length)
            ensureCapacity();
        data[size++] = item;
    }

    public T get(int index)
    {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index);
        return (T) data[index];
    }

    public int size()
    {
        return size;
    }

    public boolean contains(T item)
    {
        for (int i = 0; i < size; i++)
            if (data[i] == null ? item == null : data[i].equals(item))
                return true;
        return false;
    }

    public T removeAt(int index)
    {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index);
        T removed = (T) data[index];
        for (int i = index; i < size - 1; i++)
            data[i] = data[i + 1];
        data[--size] = null;
        return removed;
    }

    private void ensureCapacity()
    {
        Object[] newData = new Object[data.length * 2];
        for (int i = 0; i < data.length; i++)
            newData[i] = data[i];
        data = newData;
    }

    public String toString()
    {
        if (size == 0) return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++)
        {
            sb.append(data[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
