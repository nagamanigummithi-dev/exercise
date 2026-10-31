
import java.util.LinkedList;

class PC
{
    LinkedList<Integer> list = new LinkedList<Integer>();
    int capacity = 2;
    int value = 0;

    void produce() throws InterruptedException
    {
        while (true)
        {
            synchronized (this)
            {
                while (list.size() == capacity)
                {
                    wait();
                }

                list.add(value);

                System.out.println("Producer produced-" + value);

                value++;

                notify();
            }

            Thread.sleep(1000);
        }
    }

    void consume() throws InterruptedException
    {
        while (true)
        {
            synchronized (this)
            {
                while (list.size() == 0)
                {
                    wait();
                }

                int val = list.removeFirst();

                System.out.println("Consumer consumed-" + val);

                notify();
            }

            Thread.sleep(1000);
        }
    }
}

class ProducerConsumer
{
    public static void main(String args[])
    {
        PC obj = new PC();

        Thread t1 = new Thread(() ->
        {
            try
            {
                obj.produce();
            }
            catch (InterruptedException e)
            {
                System.out.println(e);
            }
        });

        Thread t2 = new Thread(() ->
        {
            try
            {
                obj.consume();
            }
            catch (InterruptedException e)
            {
                System.out.println(e);
            }
        });

        t1.start();
        t2.start();
    }
}
