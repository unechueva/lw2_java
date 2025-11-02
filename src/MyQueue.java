public class MyQueue<T>
{
    private Object[] data;
    private int head;
    private int tail;
    private int size;

    public MyQueue()
    {
        this.data = new Object[10];
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }

    public void offer(T item)
    {
        if (size == data.length)
            expand();
        data[tail] = item;
        tail = (tail + 1) % data.length;
        size++;
    }

    public T poll()
    {
        if (size == 0) return null;
        T item = (T) data[head];
        data[head] = null;
        head = (head + 1) % data.length;
        size--;
        return item;
    }

    public T peek()
    {
        if (size == 0) return null;
        return (T) data[head];
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    private void expand()
    {
        Object[] newData = new Object[data.length * 2];
        for (int i = 0; i < size; i++)
            newData[i] = data[(head + i) % data.length];
        data = newData;
        head = 0;
        tail = size;
    }
}
