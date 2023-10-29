package org.example.model;

public class Bus {
    private int govNumber;
    private String nameOfBus;
    private int ageOfcreate;
    private String numberOfRoute;
    private int sitPassangers;
    private int capacity;
    private int currentAmount;


    public int getGovNumber()
    {
        return govNumber;
    }

    public void setGovNumber(int govNumber)
    {
        this.govNumber = govNumber;
    }

    public String getNameOfBus()
    {
        return nameOfBus;
    }

    public void setNameOfBus(String nameOfBus)
    {
        this.nameOfBus = nameOfBus;
    }

    public int getAgeOfcreate()
    {
        return ageOfcreate;
    }

    public void setAgeOfcreate(int ageOfcreate)
    {
        this.ageOfcreate = ageOfcreate;
    }

    public String getNumberOfRoute()
    {
        return numberOfRoute;
    }

    public void setNumberOfRoute(String numberOfRoute) {
        this.numberOfRoute = numberOfRoute;
    }

    public int getSitPassangers() {
        return sitPassangers;
    }

    public void setSitPassangers(int sitPassangers)
    {
        this.sitPassangers = sitPassangers;
    }

    public int getCapacity()
    {
        return capacity;
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }

    public int getCurrentAmount() {return currentAmount;}

    public void setCurrentAmount(int currentAmount)
    {
        this.currentAmount = currentAmount;
    }

    public Bus(int govNumber, String nameOfBus, int ageOfcreate, String numberOfRoute, int sitPassangers, int capacity, int currentAmount)
    {
        this.govNumber = govNumber;
        this.nameOfBus = nameOfBus;
        this.ageOfcreate = ageOfcreate;
        this.numberOfRoute = numberOfRoute;
        this.sitPassangers = sitPassangers;
        this.capacity = capacity;
        this.currentAmount = currentAmount;
    }

    public Bus()
    {
        this.govNumber = 0;
        this.nameOfBus = "";
        this.ageOfcreate = 0;
        this.numberOfRoute = "";
        this.sitPassangers = 0;
        this.capacity = 0;
        this.currentAmount = 0;
    }

    public String toString()
    {
        return "Номер автобуса " + govNumber + " " + "Название автобуса " + nameOfBus + " " + "Год создания " +
                ageOfcreate+ "  "  + "Номер/название маршрута " +numberOfRoute+ " " + "Сидячих мест " + sitPassangers+ " "
                + "Общая вместимость " + capacity+ " " + "Кол-во пассажиров " + currentAmount + "\n";
    }

    public void open_door()
    {
        System.out.println("Двери открыты");
    }
    public void  close_door()
    {
        System.out.println("Двери закрыты");
    }

    public void SETpassenger (int p)
    {
        System.out.println("Сколько пассажиров село в автобус?");
        if ((p + currentAmount)<capacity)
        {
            if ((p+currentAmount)>0)
            {
                System.out.println("Количество пассажиров ="+ (currentAmount=currentAmount+p));
            }
            else
            {
                System.out.println("Недопустимое число пассажиров");
            }
        }
        else
        {
            System.out.println("Недопустимое число пассажиров");
        }



    }


}
