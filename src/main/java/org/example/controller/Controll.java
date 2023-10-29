package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.model.Bus;
import org.example.service.AutoP;
import org.jdom2.JDOMException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/bus")
public class Controll
{
    String fileName = "src/main/resources/Bus.xml";
    AutoP AutoP = new AutoP();


    private HttpServletRequest request;

    public Controll(AutoP AutoP, ArrayList<Bus> arrayListO)
    {
        this.AutoP = AutoP;
    }

    public Controll() throws IOException, ParserConfigurationException {
        AutoP.record(fileName);
    }

    @GetMapping
    public String info()
    {
        return "Название: Система городского транспорта\n" +
                "Работу выполнил: Аювджи  Дмирий Романович\n " +
                "Вариант: 1.25\n";
    }

    //получение всего массива
    @GetMapping("/all")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Bus> list() throws IOException, JDOMException
    {
        AutoP.giveElement(fileName);
        return AutoP.getBusAll();
    }

    //получение по номеру
    @GetMapping("/{govNumber}")
    public Object getBus(@PathVariable int govNumber) throws IOException, JDOMException
    {
        Bus bus =  this.AutoP.getBus(govNumber);
        if (bus == null)
        {
            return new ResponseEntity<Bus>(HttpStatus.NOT_FOUND);
        }
        else
        {
            AutoP.giveElement(fileName);
            new ResponseEntity<Bus>(HttpStatus.OK);
            return AutoP.getBus(govNumber);
        }
    }

    //добавление
    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Object create(@RequestBody Map<String, String> body) throws
            ParserConfigurationException, IOException, JDOMException
    {
        Bus bus = new Bus
                (
                        Integer.parseInt(body.get("govNumber")),
                        String.format(body.get("nameOfBus")),
                        Integer.parseInt(body.get("ageOfcreate")),
                        String.format(body.get("numberOfRoute")),
                        Integer.parseInt(body.get("sitPassangers")),
                        Integer.parseInt(body.get("capacity")),
                        Integer.parseInt(body.get("currentAmount"))
                );
        int localNUM = Integer.parseInt(body.get("govNumber"));
        if (AutoP.getBus(localNUM) != null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else
        {
            AutoP.addBus(bus);
            AutoP.record(fileName);
            return bus;

        }
    }


    //удаление по id
    @DeleteMapping("/delete/{govNumber}")
    public ResponseEntity<Bus> removeBus(@PathVariable int govNumber) throws
            ParserConfigurationException, IOException, JDOMException {
        Bus bus =  this.AutoP.getBus(govNumber);
        if (bus == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            AutoP.removeBus(govNumber);
            AutoP.record(fileName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }




    @GetMapping("/read")
    @ResponseStatus(code = HttpStatus.OK)
    public ArrayList<Bus> readInfo () throws IOException, JDOMException {
        return AutoP.giveElement(fileName);
    }

    //получение всего массива
    @GetMapping("/perL/{percentile}")
    public double percentile(@RequestParam List<Double> values, @RequestParam double percentile) throws IOException
    {
        ArrayList<Integer> passengerCounts = new ArrayList<Integer>();
        for (Bus bus : AutoP.arrayList)
        {
            passengerCounts.add(bus.getCurrentAmount());
        }
        Collections.sort(values);
        int index = (int) Math.ceil(percentile / 100.0 * values.size());
        return values.get(index - 1);

    }
}
