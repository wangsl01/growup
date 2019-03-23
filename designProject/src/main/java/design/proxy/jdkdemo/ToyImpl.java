package design.proxy.jdkdemo;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @Author wangsl
 * @Date Create In 10:38 2019/3/22
 * @Description:
 */
public class ToyImpl implements List {
    
    public void lamp() {
        System.out.println("lamp is lighting!!!!");
    }

    
    public void played() {
        System.out.println("this toy is playing!!!!");
    }

    
    public Object voice(int i,File file) {
        System.out.println("voice voice voice!!!!");
        return new Integer(1);
    }

    
    public int size() {
        return 0;
    }

    
    public boolean isEmpty() {
        return false;
    }

    
    public boolean contains(Object o) {
        return false;
    }

    
    public Iterator iterator() {
        return null;
    }

    
    public Object[] toArray() {
        return new Object[0];
    }

    
    public boolean add(Object o) {
        return false;
    }

    
    public boolean remove(Object o) {
        return false;
    }

    
    public boolean addAll(Collection c) {
        return false;
    }

    
    public boolean addAll(int index, Collection c) {
        return false;
    }

    
    public void clear() {

    }

    
    public Object get(int index) {
        return null;
    }

    
    public Object set(int index, Object element) {
        return null;
    }

    
    public void add(int index, Object element) {

    }

    
    public Object remove(int index) {
        return null;
    }

    
    public int indexOf(Object o) {
        return 0;
    }

    
    public int lastIndexOf(Object o) {
        return 0;
    }

    
    public ListIterator listIterator() {
        return null;
    }

    
    public ListIterator listIterator(int index) {
        return null;
    }

    
    public List subList(int fromIndex, int toIndex) {
        return null;
    }

    
    public boolean retainAll(Collection c) {
        return false;
    }

    
    public boolean removeAll(Collection c) {
        return false;
    }

    
    public boolean containsAll(Collection c) {
        return false;
    }

    
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }
}
