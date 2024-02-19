package ru.spmi.temnov.lab9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {//основной класс
    private ArrayList<Appliances<String>> arrComp= new ArrayList<Appliances<String>>();//реализаия обобщенного класса для расчета количества товаров указанной фирмы
    private ArrayList<Appliances<Double>> arrCost= new ArrayList<Appliances<Double>>();//реализаия обобщенного класса для расчета общей стоимости

    Main(){//конструктор - наполнитель экземпляров обобщенного класса
        for (int i = 0; i < 5; ++i){
            arrComp.add(new Appliances<>(RandomGenerator.getType(), RandomGenerator.getCompany()));
            arrCost.add(new Appliances<>(RandomGenerator.getType(), RandomGenerator.getCost()));
        }
    }
    public void setArrComp(ArrayList<Appliances<String>> arr){//метод изменения списка экземпляров String
        arrComp = arr;
    }

    public void setArrCost(ArrayList<Appliances<Double>> arr){
        arrCost = arr;
    }//метод изменения списка экземпляров double
    private boolean find(String inp){ // метод совпадения сстроки с названием фирмы
        for (String comp: RandomGenerator.getCompanyAll()){
            if (comp.equals(inp))
                return true;
        }
        return false;
    }

    private void printList(){//вывести список товаров
        System.out.println("Список товров с названием компании-производителя: ");
        for (Appliances<String> app: arrComp){
            app.showComp();
        }
        System.out.println("\nСписок товров со стоимостью: ");
        for (Appliances<Double> app: arrCost){
            app.showPrice();
        }
        System.out.println();
    }

    private String inputCompany() throws IOException{//метод чтения с консоли
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }

    private String menu(){//без понятия как сделать тест, реализация меню для выбора заданной фирмы для расчета ВП
        String needed = null;
        boolean excep;

        do{
            excep = true;
            System.out.print("Введите название фирмы, количество товаров которой хотите узнать {LG, Haier, Sharp, Samsung, Bosch, Siemens, Hitachi}: ");

            try {
                needed = inputCompany();

            } catch (IOException e) {
                excep = false;
                System.out.println("Неверный формат ввода!");
            }

            if (!find(needed)){
                System.out.println("Несуществующая фирма " + needed);
                excep = false;
            }

        }while(!excep);

        return needed;
    }

    private int countComp(String str){//вычисление количества товаров заданной фирмы
        int num = 0;
        for (Appliances<String> app: arrComp){
            if (app.getFeature().equals(str))
                num++;
        }
        return num;
    }

    private double countCost(){//вычисление стоимости
        double sum = 0.0;
        for (Appliances<Double> app: arrCost){
            sum += app.getFeature();
        }
        return sum;
    }

    public ArrayList<Appliances<String>> getCompList(){//возврат списка экземпляров класса String
        return arrComp;
    }

    public ArrayList<Appliances<Double>> getCostList(){//возврат списка экземпляров класса double
        return arrCost;
    }

    public static void main(String[] args){
        Main m = new Main();
        m.printList();
        System.out.printf("Количество товара заданной фирмы = %d\n", m.countComp(m.menu()));
        System.out.printf("Общая сумма = %.2f\n", m.countCost());
    }
}
