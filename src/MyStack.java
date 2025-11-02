public class MyStack<T>
{
    private Object[] data;
    private int top;

    public MyStack()
    {
        this.data = new Object[10];
        this.top = 0;
    }

    public void push(T item)
    {
        if (top == data.length)
            grow();
        data[top++] = item;
    }

    public T pop()
    {
        if (top == 0) return null;
        T item = (T) data[--top];
        data[top] = null;
        return item;
    }

    public T peek()
    {
        if (top == 0) return null;
        return (T) data[top - 1];
    }

    public boolean isEmpty()
    {
        return top == 0;
    }

    private void grow()
    {
        Object[] nd = new Object[data.length * 2];
        for (int i = 0; i < data.length; i++)
            nd[i] = data[i];
        data = nd;
    }
}
