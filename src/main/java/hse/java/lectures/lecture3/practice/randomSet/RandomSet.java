package hse.java.lectures.lecture3.practice.randomSet;

import java.util.Random;

public class RandomSet<T> {

    public static final int BIG_PRIME_NUMBER = 10_007;
    public static final int START_ELEMENTS_CAP = 16;

    private T[] elements;
    private int size;
    private Struct<T>[] ht;
    private Random random;

    private static class Struct<T>{
        T key;
        int value;
        Struct<T> next;

        Struct(T key, int value){
            this.key = key;
            this.value= value;
            this.next = null;
        }
    }


    public RandomSet(){
        elements = (T[]) new Object[START_ELEMENTS_CAP];
        ht = (Struct<T>[]) new Struct[BIG_PRIME_NUMBER];
        random = new Random();
        size = 0;
    }


    private void checkMaybeUpdateSize(){
        if (size == elements.length){
            T[] newElements = (T[]) new Object[elements.length * 2];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
    }

    private void updateIndex(T value, int newIndex){
        int hash = Math.abs(value.hashCode()) % BIG_PRIME_NUMBER;
        Struct<T> curr = ht[hash];

        while (curr != null){
            if (value.equals(curr.key)){
                curr.value = newIndex;
                return;
            }
            curr = curr.next;
        }
    }

    public boolean insert(T value) {
        if (contains(value)){
            return false;
        }

        checkMaybeUpdateSize();

        elements[size] = value;

        int hash = Math.abs(value.hashCode()) % BIG_PRIME_NUMBER;

        Struct<T> newStruct = new Struct<>(value,size);

        if (ht[hash] != null) {
            newStruct.next = ht[hash];
        }
        ht[hash] = newStruct;
        size++;
        return true;
    }

    public boolean remove(T value) {

        if (!contains(value)){
            return false;
        }

        int hash = Math.abs(value.hashCode()) % BIG_PRIME_NUMBER;

        Struct<T> prev = null;
        Struct<T> curr = ht[hash];

        while (curr != null){
            if (value.equals(curr.key)){
                int indexToRemove = curr.value;

                if (prev == null){
                    ht[hash] = curr.next;
                }else{
                    prev.next = curr.next;
                }

                if (indexToRemove < size - 1){
                    T lastElem = elements[size - 1];
                    elements[indexToRemove] = lastElem;
                    updateIndex(lastElem, indexToRemove);
                }

                elements[size - 1] = null;
                size--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    public boolean contains(T value) {
        int hash = Math.abs(value.hashCode()) % BIG_PRIME_NUMBER;

        Struct<T> curr = ht[hash];
        while (curr != null){
            if (value.equals(curr.key)){
                return true;
            }
            curr = curr.next;
        }

        return  false;
    }

    public T getRandom() {
        if (size == 0){
            throw new EmptySetException("Set is empty :0");
        }

        int randIndex = random.nextInt(size);
        return elements[randIndex];
    }

}
