package org.example.service;

import org.example.model.Bus;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AutoP
{
    public ArrayList<Bus> arrayList = new ArrayList<>();


    public AutoP(ArrayList<Bus> arrayList) {
        this.arrayList = arrayList;
    }

    public AutoP() {
        arrayList = new ArrayList<>();
    }


    //добавление в массив
    public void addBus(Bus newBus) {
        arrayList.add(newBus);
    }


    //удаление из массива
    public void removeBus(long index)
    {
        for (int i = 0; i < arrayList.size(); i++)
            if (arrayList.get(i).getGovNumber() == index)
            {
                arrayList.remove(i);
                break;
            }
    }


    //получение массива
    public ArrayList<Bus> getBusAll() {
        return arrayList;
    }


    //получение элемента из массива
    public Bus getBus(int index)
    {
        for (Bus bus : arrayList)
        {
            if (bus.getGovNumber() == index)
            {
                return bus;
            }
        }
        return null;
    }



    //запись в формате XML
    public Object record(String fileName) throws IOException {
        System.out.println(arrayList);

        // Создаем новый документ JDOM
        Document doc = new Document();
        Element root = new Element("autoPark");
        doc.setRootElement(root);

        for (Bus bus : arrayList) {
            Element busElement = new Element("Bus");

            busElement.setAttribute("govN", String.valueOf(bus.getGovNumber()));
            busElement.setAttribute("name", bus.getNameOfBus());
            busElement.setAttribute("dateOFcreate", String.valueOf(bus.getAgeOfcreate()));
            busElement.setAttribute("RouteN", bus.getNumberOfRoute());
            busElement.setAttribute("SitPass", String.valueOf(bus.getSitPassangers()));
            busElement.setAttribute("Cap", String.valueOf(bus.getCapacity()));
            busElement.setAttribute("CurrentAmount", String.valueOf(bus.getCurrentAmount()));

            // Добавляем элемент Bus к корневому элементу autoPark
            root.addContent(busElement);
        }

        // Документ JDOM сформирован и готов к записи в файл
        XMLOutputter xmlWriter = new XMLOutputter(Format.getPrettyFormat());

        try (FileOutputStream fileOutputStream = new FileOutputStream("Bus.xml")) {
            xmlWriter.output(doc, fileOutputStream);
        }

        xmlWriter.output(doc, System.out);

        return doc;
    }



    //чтение файла XML
    public ArrayList<Bus> giveElement(String fileName) throws JDOMException, IOException {
        // В этом методе, вам не нужно создавать DocumentBuilderFactory и DocumentBuilder для JDOM.
        // Вы уже используете JDOM SAXBuilder, поэтому используйте его.

        SAXBuilder saxBuilder = new SAXBuilder();
        try {
            Document jdomDocument = saxBuilder.build(new File(fileName));
            Element root = jdomDocument.getRootElement(); // Получаем корневой элемент

            if (root.getName().equals("autoPark")) {
                List<Element> busElements = root.getChildren("Bus");

                for (Element busElement : busElements) {
                    Bus bus = new Bus();
                    bus.setGovNumber(Integer.parseInt(busElement.getAttributeValue("govN")));
                    bus.setNameOfBus(busElement.getAttributeValue("name"));
                    bus.setAgeOfcreate(Integer.parseInt(busElement.getAttributeValue("dateOFcreate")));
                    bus.setNumberOfRoute(busElement.getAttributeValue("RouteN"));
                    bus.setSitPassangers(Integer.parseInt(busElement.getAttributeValue("SitPass")));
                    bus.setCapacity(Integer.parseInt(busElement.getAttributeValue("Cap")));
                    bus.setCurrentAmount(Integer.parseInt(busElement.getAttributeValue("CurrentAmount")));
                    arrayList.add(bus);
                }
            }
        } catch (JDOMException | IOException e) {
            // Обработка ошибок, если они возникнут при чтении XML
        }

        return arrayList;
    }

    //размер массива
    public int size() {
        return  arrayList.size();
    }
    //процентиль
    public double percentile(List<Double> values, double percentile) throws IOException {

        System.out.println("Введите процентиль: ");
        percentile = System.in.read();

        ArrayList<Integer> passengerCounts = new ArrayList<Integer>();
        for (Bus bus : arrayList) {
            passengerCounts.add(bus.getCurrentAmount());
        }
        Collections.sort (values);
        int index = (int) Math.ceil (percentile / 100.0 * values.size ());
        return values.get (index - 1);
    }

}
