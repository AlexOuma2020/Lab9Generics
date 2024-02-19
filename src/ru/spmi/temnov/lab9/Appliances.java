package ru.spmi.temnov.lab9;
public class Appliances <T>{//обобщенный класс
    private final T feature;//характеристика для вычисляемого показателя
    private final String type;//тип товара
    Appliances(String type, T feature){
        this.type = type;
        this.feature = feature;
    }
    public T getFeature(){
        return feature;
    }
    public void showComp(){
        System.out.println(type + " компании " + feature);
    }
    public void showPrice(){
        System.out.println(type + " со стоимостью " + feature);
    }
}