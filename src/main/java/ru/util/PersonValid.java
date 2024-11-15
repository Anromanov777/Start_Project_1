package ru.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import ru.dao.PeopleDAO;
import ru.model.Person;

@Component
public class PersonValid implements Validator {
    private final PeopleDAO peopleDAO;

    @Autowired
    public PersonValid(PeopleDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (peopleDAO.show(person.getEmail())!=null)errors.rejectValue("email","","Пользователь с таким емейл уже существует!");
    }
}
