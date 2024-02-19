package ru.spmi.temnov.lab9;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class MainTestGenerics {//класс тестов
    private void provideInput(String data){//имитация ввода в консоль
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }
    Main m;
    @BeforeEach
    void testible(){
        m = new Main();
    }

    @Test
    void getFeatureTest1(){//проверка метода возврата характеристики-фирмы
        Appliances<String> app = new Appliances<>("Холодильник", "LG");
        assertEquals("LG", app.getFeature());
    }

    @Test
    void getFeatureTest2(){//проверка метода возврата характеристики-стоимости
        Appliances<Double> app = new Appliances<>("Холодильник", 123.23);
        assertEquals(123.23, app.getFeature());
    }

    @Test
    void randGetRandomCompanyTest(){//проверка получения случайного значения компании
        try {
            Method method = Main.class.getDeclaredMethod("find", String.class);
            method.setAccessible(true);

            assertTrue((Boolean)method.invoke(m, RandomGenerator.getCompany()));
            System.out.println("Случайное значение массива company работает корректно\n");

        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);

        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void randGetRandomType(){//проверка получения случайного значения типа товара
        String type = RandomGenerator.getCompany();

        for (String st: RandomGenerator.getTypeAll()){
            if (st.equals(type)){
                assertEquals(st, type);
                break;
            }
        }

        System.out.println("Случайное значение массива company работает корректно\n");

    }

    @Test
    void companyGetAllTest(){//проверка получения массива названий-компаний
        assertArrayEquals(new String[]{"LG", "Haier", "Sharp", "Samsung", "Bosch", "Siemens", "Hitachi"}, RandomGenerator.getCompanyAll());
        System.out.println("Получение массива фирм работает корректно\n");
    }

    @Test
    public void testFound1(){//проверка соответствия названия фирмы строке LG
        try {
            Method method = Main.class.getDeclaredMethod("find", String.class);
            method.setAccessible(true);

            assertEquals(true, method.invoke(m, "LG"));
            System.out.println("Товар фирмы LG существует\n");

        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);

        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testFound2(){//проверка несоответствия названия фирмы строке не-названию-фирмы
        try {
            Method method = Main.class.getDeclaredMethod("find", String.class);
            method.setAccessible(true);

            assertEquals(false, method.invoke(m, "Qwerty"));
            System.out.println("Товара фирмы lG не существует\n");

        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);

        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testFound3(){//проверка несоответствия названия фирмы пустой строке
        try {
            Method method = Main.class.getDeclaredMethod("find", String.class);
            method.setAccessible(true);

            assertEquals(false, method.invoke(m, ""));
            System.out.println("Товара фирмы без названия не существует\n");

        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);

        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void inputTest1(){//проверка ввода названия фирмы
        provideInput("Haier");

        try {
            Method method = Main.class.getDeclaredMethod("inputCompany", null);
            method.setAccessible(true);

            String output = (String) method.invoke(m);
            assertEquals("Haier", output);
            System.out.println("Ввод Haier подерживается\n");

        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);

        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void inputTest2(){//проверка ввода иного значения (в т.ч. пустая строка)
        provideInput("");

        try {
            Method method = Main.class.getDeclaredMethod("inputCompany", null);
            method.setAccessible(true);

            String output = (String) method.invoke(m);
            assertNull(output);
            System.out.println("Ввод пустой строки подерживается\n");

        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);

        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    private HashMap<String, Integer> countCheck(){//локальный подсчет числа товаров заданной фирмы
        HashMap<String, Integer> hashMap = new HashMap<>();

        for (String st: RandomGenerator.getCompanyAll()){
            Integer num = 0;

            for (Appliances<String> app: m.getCompList())
                if (app.getFeature().equals(st))
                    ++num;

            hashMap.put(st, num);
        }

        return hashMap;
    }

    private double costCheck(){//локальный расчет стоимости
        double sum = 0.0;

        for (Appliances<Double> app: m.getCostList())
            sum += app.getFeature();

        return sum;
    }

    @Test
    public void countTestNotRandom() throws RuntimeException {//тест проверка методов подсчета с заданными значениями
        System.out.println("Заданные значения!\n");
        try {
            ArrayList<Appliances<String>> arrayListComp = new ArrayList<>();
            arrayListComp.add(new Appliances<>(RandomGenerator.getType(), "LG"));
            arrayListComp.add(new Appliances<>(RandomGenerator.getType(), "Bosch"));
            arrayListComp.add(new Appliances<>(RandomGenerator.getType(), "Siemens"));
            arrayListComp.add(new Appliances<>(RandomGenerator.getType(), "Bosch"));
            arrayListComp.add(new Appliances<>(RandomGenerator.getType(), "Hitachi"));
            m.setArrComp(arrayListComp);

            ArrayList<Appliances<Double>> arrayListCost = new ArrayList<>();
            arrayListCost.add(new Appliances<>(RandomGenerator.getType(), 100.00));
            arrayListCost.add(new Appliances<>(RandomGenerator.getType(), 250.00));
            arrayListCost.add(new Appliances<>(RandomGenerator.getType(), 300.00));
            arrayListCost.add(new Appliances<>(RandomGenerator.getType(), 450.45));
            arrayListCost.add(new Appliances<>(RandomGenerator.getType(), 123.67));
            m.setArrCost(arrayListCost);

            Method method = Main.class.getDeclaredMethod("printList");
            method.setAccessible(true);
            method.invoke(m);

            method = Main.class.getDeclaredMethod("countComp", String.class);//расчет количества товаров заданной фирмы
            method.setAccessible(true);

            int numOfApps = (int) method.invoke(m, "Haier");
            assertEquals(0, numOfApps);
            System.out.printf("Количество товаров компании Haier равно %d\n", numOfApps);
            numOfApps = (int) method.invoke(m, "LG");
            assertEquals(1, numOfApps);
            System.out.printf("Количество товаров компании LG равно %d\n", numOfApps);
            numOfApps = (int) method.invoke(m, "Bosch");
            assertEquals(2, numOfApps);
            System.out.printf("Количество товаров компании Bosch равно %d\n", numOfApps);
            numOfApps = (int) method.invoke(m, "Samsung");
            assertEquals(0, numOfApps);
            System.out.printf("Количество товаров компании Samsung равно %d\n", numOfApps);
            numOfApps = (int) method.invoke(m, "Hitachi");
            assertEquals(1, numOfApps);
            System.out.printf("Количество товаров компании Hitachi равно %d\n", numOfApps);
            numOfApps = (int) method.invoke(m, "Siemens");
            assertEquals(1, numOfApps);
            System.out.printf("Количество товаров компании Siemens равно %d\n", numOfApps);
            numOfApps = (int) method.invoke(m, "Sharp");
            assertEquals(0, numOfApps);
            System.out.printf("Количество товаров компании Sharp равно %d\n", numOfApps);

            System.out.println();

            method = Main.class.getDeclaredMethod("countCost");//расчет стоимости
            method.setAccessible(true);
            double c = costCheck();

            double costSum = 100.00 + 250.00 + 300.00 + 450.45 + 123.67;
            assertEquals(costSum, (double) method.invoke(m));
            System.out.printf("Стоимость товаров равна %.2f\n\n", c);

        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

   @Test
    public void countTest() throws RuntimeException {//тест проверка методов подсчета со случайными значениями
       System.out.println("Случайные значения!\n");
        try {
            Method method = Main.class.getDeclaredMethod("printList");
            method.setAccessible(true);
            method.invoke(m);

            HashMap<String, Integer> countFirm = countCheck();
            method = Main.class.getDeclaredMethod("countComp", String.class);//расчет числа товаров заданной фирмы
            method.setAccessible(true);

            for (String st: RandomGenerator.getCompanyAll()){
                int numOfApps = (int) method.invoke(m, st);
                assertEquals(countFirm.get(st), numOfApps);
                System.out.printf("Количество товаров компании %s равно %d\n", st, numOfApps);
            }
            System.out.println();

            method = Main.class.getDeclaredMethod("countCost");//расчет общей стоимости
            method.setAccessible(true);
            double c = costCheck();
            assertEquals(costCheck(), (double) method.invoke(m));
            System.out.printf("Стоимость товаров равна %.2f\n\n", c);

        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);

        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
