package org.example.service;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.example.model.Bus;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.scheduling.annotation.Async;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AutoP
{
    public ArrayList<Bus> arrayList = new ArrayList<>();
    String fileName = "src/main/resources/Bus.xml";
    public AutoP(ArrayList<Bus> arrayList) {
        this.arrayList = arrayList;
    }

    public AutoP() {
        arrayList = new ArrayList<>();
    }


    public void addBus(Bus newBus) {
        arrayList.add(newBus);
    }

    public List<Bus> getBusAll(){
        return arrayList;
    }
    public void removeBus(long index)
    {
        for (int i = 0; i < arrayList.size(); i++)
            if (arrayList.get(i).getGovNumber() == index)
            {
                arrayList.remove(i);
                break;
            }
    }

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
    public Object record(String fileName) throws IOException {
        System.out.println(arrayList);
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

            root.addContent(busElement);
        }

        XMLOutputter xmlWriter = new XMLOutputter(Format.getPrettyFormat());

        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            xmlWriter.output(doc, fileOutputStream);
        }

        xmlWriter.output(doc, System.out);

        return doc;
    }

    public ArrayList<Bus> giveElement(String fileName) throws JDOMException, IOException {

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
        }

        return arrayList;
    }

    public int size() {
        return  arrayList.size();
    }
    @Async
    public double calculatePercentile(int percentile) throws IOException, JDOMException {
        giveElement(fileName);
        List<Bus> sortedbusAll = getBusAll();
        Collections.sort(sortedbusAll, (bus1, bus2) -> Integer.compare(bus1.getCurrentAmount(), bus2.getCurrentAmount()));
        List<Double> currentAmiutnList = new ArrayList<>();
        for (Bus currentBus : sortedbusAll) {
            currentAmiutnList.add((double) currentBus.getCurrentAmount());
        }
        if (currentAmiutnList == null || currentAmiutnList.isEmpty() || percentile < 0 || percentile > 100) {
            throw new IllegalArgumentException("Invalid input data or percentile value");
        }
        double[] dataArray = currentAmiutnList.stream()
                .mapToDouble(Double::doubleValue)
                .toArray();
        Percentile percentileCalculator = new Percentile();
        percentileCalculator.setData(dataArray);
        double output = percentileCalculator.evaluate(percentile);
        System.out.println(output);
        return output;
    }
}
