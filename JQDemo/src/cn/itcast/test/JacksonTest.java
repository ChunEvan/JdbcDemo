package cn.itcast.test;

import cn.itcast.domain.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class JacksonTest {

    @Test
    public void test1() throws IOException {
        Person person = new Person();
        person.setName("evan");
        person.setAge(12);
        person.setGender("male");
        person.setBirthday(new Date());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(person);
        System.out.println(json);

//        mapper.writeValue(new File("a.properties"),person);

        mapper.writeValue(new FileWriter("a.txt"),person);
        json = "{\"name\":\"evan\",\"age\":12,\"gender\":\"male\"}";
        Person person1 = mapper.readValue(json, Person.class);
        System.out.println(person1);


    }

}
