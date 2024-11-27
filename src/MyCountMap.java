import ru.sbt.generics.CountMap;

import java.util.HashMap;
import java.util.Map;

public class MyCountMap<T> implements CountMap<T> {

    private final HashMap<T, Integer> map = new HashMap<>();
    // добавляет элемент в этот контейнер.
    @Override
    public void add(T key) {
        if (!map.containsKey(key)) {
            map.put(key, 1);
        } else {
            map.put(key, map.get(key) + 1);
        }
    }
    //Возвращает количество добавлений данного элемента
    @Override
    public int getCount(T key) {
        return map.getOrDefault(key, 0); // Возвращаем 0, если ключа нет
    }
    //Удаляет элемент из контейнера и возвращает количество его добавлений(до удаления)
    @Override
    public int remove(T key) {
        if (map.containsKey(key)) {
            int count = map.remove(key);
            return count;
        }
        return 0;
    }
    //Количество разных элементов
    @Override
    public int size() {
        return map.size(); // Количество уникальных ключей
    }
    //Добавить все элементы из source в текущий контейнер,
    // при совпадении ключей, суммировать значения
    @Override
    public void addAll(CountMap<T> source) {
        for (T key : source.toMap().keySet()) {
            int count = source.getCount(key);
            this.add(key);
            map.put(key, map.get(key) + count - 1); // Суммируем текущие добавления
        }
    }
    //Вернуть java.util.Map. ключ - добавленный элемент,
    // значение - количество его добавлений
    @Override
    public Map<T, Integer> toMap() {
        return new HashMap<>(map);
    }
    //Тот же самый контракт как и toMap(), только всю информацию записать в destination
    @Override
    public void toMap(Map<T, Integer> destination) {
        destination.clear();
        destination.putAll(map);
    }

    public static void main(String[] args) {
        CountMap<Integer> map = new MyCountMap<>();

        map.add(10);
        map.add(10);
        map.add(5);
        map.add(6);
        map.add(5);
        map.add(10);



        int count1 = map.getCount(5);  // 2
        int count2 = map.getCount(6);  // 1
        int count3 = map.getCount(10); // 3
        System.out.println("Количество повторений 5 = " + count1);
        System.out.println("Количество повторений 6 = " + count2);
        System.out.println("Количество повторений 10 = " + count3);
    }
}


