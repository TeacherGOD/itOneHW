package ru.itone.course_java.core.base_exceptions.utils;


import ru.itone.course_java.core.base_exceptions.exceptions.PersonValidationException;
import ru.itone.course_java.core.base_exceptions.model.Person;
import ru.itone.course_java.core.base_exceptions.model.Sex;

import java.util.Arrays;
import java.util.regex.Pattern;

public class PersonValidator {

    private static final Pattern namePattern=Pattern.compile("^[a-zA-Zа-яА-Я\\s.\\-'`ё]+$");
    private final DocumentValidator documentValidator = new DocumentValidator();

    public void  validate(Person person){
        validateAllNames(person);
        validateAge(person.getAge());
        validateSex(person.getSex());
        validateDocument(person);

    }

    private void validateDocument(Person person) {
        documentValidator.validate(person.getIdentityDocument(),person.isResident());

    }

    private void validateSex(Sex sex) {
        if (sex == null) {
            throw new PersonValidationException("Пол является обязательным параметром");
        }
    }

    private void validateAge(int age) {
        if (age<=0 || age>120) {
            throw new PersonValidationException(
                    String.format("Возраст человека должен быть больше 0 и не больше 120 лет, указанный возраст %d",age)
            );
        }
    }

    private void validateAllNames(Person person){

        var names= Arrays.asList(
                person.getFirstName(),
                person.getMiddleName(),
                person.getPatronymic(),
                person.getLastName()
        );
        for (var name : names)  validateOneName(name);

        validatePatronymicAndMiddleName(person.getPatronymic(),person.getMiddleName());

        validateFirstName(person.getFirstName());
    }

    private void validateFirstName(String firstName) {
        if (firstName==null || firstName.isBlank()) {
            throw new PersonValidationException("Имя человека является обязательным параметром");
        }
    }



    private void validatePatronymicAndMiddleName(String patronymic, String middleName) {
        if (patronymic != null && middleName != null) {
            throw new PersonValidationException("Должно быть указано либо только Отчество, либо только Дополнительное Имя");
        }

    }

    private void validateOneName(String currentName) {
        if (currentName == null) return;
        for (char c : currentName.toCharArray())
            if (!namePattern.matcher(String.valueOf(c)).matches())
                throw new PersonValidationException(
                        String.format("ФИО может состоять только из латиницы, кириллицы, пробела, точки, тире, одинарной кавычки и апострофа, символ %s не валидный", c)
                );
    }

}
