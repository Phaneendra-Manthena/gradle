package com.bhargo.user.utils;

import java.util.HashMap;
import java.util.Map;

public  final class IndexList<E> {
    private final Map<Integer, E> indexObj = new HashMap<>();
    private final Map<String, Integer> keyIndex = new HashMap<>();


    public int getIndexByKey(String key) {
        if(keyIndex.containsKey(key)){
            return keyIndex.get(key);
        }else{
            return -1;
        }
    }

    public int size() {
        return keyIndex.size();
    }


    public void set(String key,E e){
        if(keyIndex.containsKey(key)){
            indexObj.put(keyIndex.get(key),e);
        }
    }

    public boolean add(E e, String key){
        if (keyIndex.containsKey(key)){
            return false;
        }
//            throw new IllegalArgumentException("Key '" + key + "' duplication");
        int index = size();
        keyIndex.put(key, index);
        indexObj.put(index, e);
        return true;
    }

    public E get(int index) {
        return indexObj.get(index);
    }

    public E get(String key){
        return indexObj.get(keyIndex.get(key));
    }

    public void remove(int index){
        indexObj.remove(index);
    }

   public IndexList<E> subList(IndexList<E>indexList,int endIndex){
        for (int i = 0; i <size() ; i++) {
            if (i > 1) {
                indexList.remove(i);
            }
        }
        return indexList;
   }
}
