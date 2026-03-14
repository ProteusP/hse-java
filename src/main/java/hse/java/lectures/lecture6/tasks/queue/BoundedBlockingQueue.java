package hse.java.lectures.lecture6.tasks.queue;

import java.util.LinkedList;
import java.util.Queue;

public class BoundedBlockingQueue<T> {
    int capacity;
    Queue<T> queue = new LinkedList<T>();


    public BoundedBlockingQueue(int capacity) {
        if (capacity <= 0){
            throw new IllegalArgumentException();
        }

        this.capacity = capacity;
    }

    public void put(T item) {
        if (item == null){
            throw  new IllegalArgumentException();
        }

        while (queue.size() == capacity){
            try {wait();} catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        queue.offer(item);
    }

    public synchronized T take() {
        while (queue.isEmpty()){
            try {wait();} catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        T item = queue.poll();
        notifyAll();
        return item;
    }

    public synchronized int size() {
        return queue.size();
    }

    public int capacity() {
        return capacity;
    }
}
