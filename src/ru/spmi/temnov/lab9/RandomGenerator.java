package ru.spmi.temnov.lab9;

public class RandomGenerator {//класс дл случайной генерации значений
    private final static String[] type = new String[]{"Пылесос", "Холодильник", "Телевизор", "Чайник"};//массив типов товаров
    private final static String[] company = {"LG", "Haier", "Sharp", "Samsung", "Bosch", "Siemens", "Hitachi"};//массив названий-фирм

    public static String getType(){
       return type[(int)(Math.random() * type.length)];
    }//возврат случайного типа товара

    public static String getCompany(){
        return company[(int)(Math.random() * company.length)];
    }//возврат случайного названия фирмы

    public static double getCost(){//? как тестировать, возврат случайной соимости
        return (int)((Math.random() * 300.0 + 100.0) * 100) / 100.00;
    }

    public static String[] getTypeAll(){
        return type;
    }

    public static String[] getCompanyAll(){
        return company;
    }
}
