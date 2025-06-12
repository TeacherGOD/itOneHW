package ru.itone.course_java.core.base_exceptions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.itone.course_java.core.base_exceptions.model.DocType;
import ru.itone.course_java.core.base_exceptions.model.IdentityDocument;
import ru.itone.course_java.core.base_exceptions.model.Person;
import ru.itone.course_java.core.base_exceptions.model.Sex;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static ru.itone.course_java.core.base_exceptions.BaseExceptions.today;

class BaseExceptionsTest {

    BaseExceptions baseExceptions = new BaseExceptions();

    @ParameterizedTest
    @MethodSource
    void checkPerson_Valid(Person person) {
        baseExceptions.checkPerson(person);
    }

    static Stream<Arguments> checkPerson_Valid() {
        return Stream.of(
                Arguments.of(Person.builder()
                        .lastName("Иванов Петров")
                        .firstName("Олег")
                        .patronymic("Олегович")
                        .age(18)
                        .sex(Sex.MALE)
                        .resident(true)
                        .identityDocument(IdentityDocument.builder()
                                .type(DocType.PASSPORT)
                                .code("1234")
                                .number("123456")
                                .issueDate(today().minusYears(1))
                                .startDate(today().minusYears(1).plusDays(1))
                                .expireDate(today().plusYears(1))
                                .build())
                        .build()),
                Arguments.of(Person.builder()
                        .lastName("Воробьёва")
                        .firstName("Марина")
                        .patronymic("Евгеньевна")
                        .age(25)
                        .sex(Sex.FEMALE)
                        .resident(true)
                        .identityDocument(IdentityDocument.builder()
                                .type(DocType.PASSPORT)
                                .code("0987")
                                .number("098765")
                                .issueDate(today().minusYears(5))
                                .startDate(today().minusYears(5))
                                .expireDate(today().plusYears(10))
                                .build())
                        .build()),
                Arguments.of(Person.builder()
                        .lastName("Стручкова-Мельницкая")
                        .firstName("Алиса")
                        .patronymic("Борисова")
                        .age(120)
                        .sex(Sex.FEMALE)
                        .resident(true)
                        .identityDocument(IdentityDocument.builder()
                                .type(DocType.PASSPORT)
                                .code("0000")
                                .number("000001")
                                .issueDate(today().minusYears(30))
                                .startDate(today().minusYears(30))
                                .expireDate(today().plusYears(100))
                                .build())
                        .build()),
                Arguments.of(Person.builder()
                        .lastName("Э")
                        .firstName("Чань")
                        .age(30)
                        .sex(Sex.MALE)
                        .resident(false)
                        .identityDocument(IdentityDocument.builder()
                                .type(DocType.FOREIGN_PASSPORT)
                                .code("A")
                                .number("12345678")
                                .issueDate(today().minusYears(5))
                                .startDate(today().minusYears(5))
                                .expireDate(today().plusYears(10))
                                .build())
                        .build()),
                Arguments.of(Person.builder()
                        .lastName("Э")
                        .firstName("Шэнь")
                        .age(29)
                        .sex(Sex.FEMALE)
                        .resident(false)
                        .identityDocument(IdentityDocument.builder()
                                .type(DocType.FOREIGN_PASSPORT)
                                .code("A")
                                .number("1234567890")
                                .issueDate(today().minusYears(1))
                                .startDate(today().minusYears(1))
                                .expireDate(today().plusYears(5))
                                .build())
                        .build()),
                Arguments.of(Person.builder()
                        .lastName("Э")
                        .firstName("Ян")
                        .age(1)
                        .sex(Sex.MALE)
                        .resident(false)
                        .identityDocument(IdentityDocument.builder()
                                .type(DocType.FOREIGN_PASSPORT)
                                .code("C")
                                .number("0987654321")
                                .issueDate(today())
                                .startDate(today())
                                .expireDate(today())
                                .build())
                        .build()),
                Arguments.of(Person.builder()
                        .lastName("Washington")
                        .firstName("John")
                        .middleName("F.")
                        .age(45)
                        .sex(Sex.MALE)
                        .resident(false)
                        .identityDocument(IdentityDocument.builder()
                                .type(DocType.FOREIGN_PASSPORT)
                                .code("A")
                                .number("0011223344")
                                .issueDate(today().minusYears(10))
                                .startDate(today().minusYears(10))
                                .expireDate(today().plusYears(20))
                                .build())
                        .build()),
                Arguments.of(Person.builder()
                        .lastName("Sean")
                        .firstName("O'Connor")
                        .age(20)
                        .sex(Sex.MALE)
                        .resident(false)
                        .identityDocument(IdentityDocument.builder()
                                .type(DocType.FOREIGN_PASSPORT)
                                .code("B")
                                .number("0011223344")
                                .issueDate(today().minusDays(1))
                                .startDate(today())
                                .expireDate(today().plusYears(10))
                                .build())
                        .build()),
                Arguments.of(Person.builder()
                        .lastName("O`Brien")
                        .firstName("Li")
                        .middleName("Na")
                        .age(45)
                        .sex(Sex.FEMALE)
                        .resident(false)
                        .identityDocument(IdentityDocument.builder()
                                .type(DocType.FOREIGN_PASSPORT)
                                .code("B")
                                .number("1234567800")
                                .issueDate(today().minusYears(5))
                                .startDate(today().minusYears(5))
                                .expireDate(today().plusYears(30))
                                .build())
                        .build())
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkPerson_InvalidPersonInfo(Person person, String message) {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> baseExceptions.checkPerson(person))
                .withMessage(message);
    }

    static Stream<Arguments> checkPerson_InvalidPersonInfo() {
        return Stream.of(
                Arguments.of(residentPerson().firstName("First1").build(),
                        "ФИО и Дополнительное Имя может состоять только из латиницы, кириллицы, пробела, точки, тире, одинарной кавычки и апострофа, символ 1 не валидный"),
                Arguments.of(residentPerson().lastName("*Last").build(),
                        "ФИО и Дополнительное Имя может состоять только из латиницы, кириллицы, пробела, точки, тире, одинарной кавычки и апострофа, символ * не валидный"),
                Arguments.of(residentPerson().patronymic("Patron;m;c").build(),
                        "ФИО и Дополнительное Имя может состоять только из латиницы, кириллицы, пробела, точки, тире, одинарной кавычки и апострофа, символ ; не валидный"),
                Arguments.of(residentPerson().middleName("MiddleName").build(), "Должно быть указано либо только Отчество, либо только Дополнительное Имя"),
                Arguments.of(residentPerson().age(0).build(), "Возраст человека должен быть больше 0 и не больше 120 лет, указанный возраст 0"),
                Arguments.of(residentPerson().age(-1).build(), "Возраст человека должен быть больше 0 и не больше 120 лет, указанный возраст -1"),
                Arguments.of(residentPerson().age(121).build(), "Возраст человека должен быть больше 0 и не больше 120 лет, указанный возраст 121"),
                Arguments.of(residentPerson().sex(null).build(), "Пол является обязательным параметром"),
                Arguments.of(residentPerson().identityDocument(null).build(), "Требуется паспорт")
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkPerson_InvalidIdentityDocument(Person person, String message) {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> baseExceptions.checkPerson(person))
                .withMessage(message);
    }

    static Stream<Arguments> checkPerson_InvalidIdentityDocument() {
        return Stream.of(
                Arguments.of(foreignPerson()
                        .identityDocument(residentDoc()
                                .build())
                        .build(), "Гражданство и тип паспорта должны совпадать, указано гражданин: Нет, тип паспорта: Внутренний паспорт"),
                Arguments.of(residentPerson()
                        .identityDocument(foreignDoc()
                                .build())
                        .build(), "Гражданство и тип паспорта должны совпадать, указано гражданин: Да, тип паспорта: Зарубежный паспорт"),
                Arguments.of(residentPerson()
                        .identityDocument(residentDoc()
                                .code("123")
                                .build())
                        .build(), "Код паспорта для граждан должен состоять из 4 цифр, а для туристов быть A,B,C, во внутреннем паспорте указан код: 123"),
                Arguments.of(residentPerson()
                        .identityDocument(residentDoc()
                                .code("A")
                                .build())
                        .build(), "Код паспорта для граждан должен состоять из 4 цифр, а для туристов быть A,B,C, во внутреннем паспорте указан код: A"),
                Arguments.of(residentPerson()
                        .identityDocument(residentDoc()
                                .code("00000")
                                .build())
                        .build(), "Код паспорта для граждан должен состоять из 4 цифр, а для туристов быть A,B,C, во внутреннем паспорте указан код: 00000"),
                Arguments.of(foreignPerson()
                        .identityDocument(foreignDoc()
                                .code("1")
                                .build())
                        .build(), "Код паспорта для граждан должен состоять из 4 цифр, а для туристов быть A,B,C, в зарубежном паспорте указан код: 1"),
                Arguments.of(foreignPerson()
                        .identityDocument(foreignDoc()
                                .code("D")
                                .build())
                        .build(), "Код паспорта для граждан должен состоять из 4 цифр, а для туристов быть A,B,C, в зарубежном паспорте указан код: D"),
                Arguments.of(foreignPerson()
                        .identityDocument(foreignDoc()
                                .code("a")
                                .build())
                        .build(), "Код паспорта для граждан должен состоять из 4 цифр, а для туристов быть A,B,C, в зарубежном паспорте указан код: a"),
                Arguments.of(foreignPerson()
                        .identityDocument(foreignDoc()
                                .code("ABC")
                                .build())
                        .build(), "Код паспорта для граждан должен состоять из 4 цифр, а для туристов быть A,B,C, в зарубежном паспорте указан код: ABC"),
                Arguments.of(residentPerson()
                        .identityDocument(residentDoc()
                                .number("12345")
                                .build())
                        .build(), "Номер паспорта для граждан должен состоять из 6 цифр, а для туристов 8 или 10 цифр, во внутреннем паспорте указан номер: 12345"),
                Arguments.of(residentPerson()
                        .identityDocument(residentDoc()
                                .number("1234567")
                                .build())
                        .build(), "Номер паспорта для граждан должен состоять из 6 цифр, а для туристов 8 или 10 цифр, во внутреннем паспорте указан номер: 1234567"),
                Arguments.of(residentPerson()
                        .identityDocument(residentDoc()
                                .number("abcdef")
                                .build())
                        .build(), "Номер паспорта для граждан должен состоять из 6 цифр, а для туристов 8 или 10 цифр, во внутреннем паспорте указан номер: abcdef"),
                Arguments.of(residentPerson()
                        .identityDocument(residentDoc()
                                .number("12345A")
                                .build())
                        .build(), "Номер паспорта для граждан должен состоять из 6 цифр, а для туристов 8 или 10 цифр, во внутреннем паспорте указан номер: 12345A"),
                Arguments.of(foreignPerson()
                        .identityDocument(foreignDoc()
                                .number("1234567")
                                .build())
                        .build(), "Номер паспорта для граждан должен состоять из 6 цифр, а для туристов 8 или 10 цифр, в зарубежном паспорте указан номер: 1234567"),
                Arguments.of(foreignPerson()
                        .identityDocument(foreignDoc()
                                .number("123456789")
                                .build())
                        .build(), "Номер паспорта для граждан должен состоять из 6 цифр, а для туристов 8 или 10 цифр, в зарубежном паспорте указан номер: 123456789"),
                Arguments.of(foreignPerson()
                        .identityDocument(foreignDoc()
                                .number("12345678900")
                                .build())
                        .build(), "Номер паспорта для граждан должен состоять из 6 цифр, а для туристов 8 или 10 цифр, в зарубежном паспорте указан номер: 12345678900"),
                Arguments.of(foreignPerson()
                        .identityDocument(foreignDoc()
                                .number("123456789A")
                                .build())
                        .build(), "Номер паспорта для граждан должен состоять из 6 цифр, а для туристов 8 или 10 цифр, в зарубежном паспорте указан номер: 123456789A"),
                Arguments.of(residentPerson()
                        .identityDocument(residentDoc()
                                .issueDate(today().minusDays(1))
                                .startDate(today().minusDays(2))
                                .build())
                        .build(), "Дата выдачи паспорта %s не может быть позже даты начала действия паспорта %s".formatted(today().minusDays(1), today().minusDays(2))),
                Arguments.of(residentPerson()
                        .identityDocument(residentDoc()
                                .issueDate(today())
                                .startDate(today().plusDays(1))
                                .build())
                        .build(), "Дата выдачи %s и начала действия паспорта %s не должна быть в будущем".formatted(today(), today().plusDays(1))),
                Arguments.of(residentPerson()
                        .identityDocument(residentDoc()
                                .issueDate(today().plusDays(1))
                                .startDate(today().plusDays(1))
                                .build())
                        .build(), "Дата выдачи %s и начала действия паспорта %s не должна быть в будущем".formatted(today().plusDays(1), today().plusDays(1))),
                Arguments.of(residentPerson()
                        .identityDocument(residentDoc()
                                .expireDate(today().minusDays(1))
                                .build())
                        .build(), "Паспорт должен быть действительным, срок действия паспорта подошёл к концу %s".formatted(today().minusDays(1)))
        );
    }

    static Person.PersonBuilder residentPerson() {
        return Person.builder()
                .lastName("LastName")
                .firstName("FirstName")
                .patronymic("Patronymic")
                .age(18)
                .sex(Sex.MALE)
                .resident(true)
                .identityDocument(residentDoc().build());
    }

    static Person.PersonBuilder foreignPerson() {
        return Person.builder()
                .lastName("LastName")
                .firstName("FirstName")
                .patronymic("Patronymic")
                .age(18)
                .sex(Sex.MALE)
                .resident(false)
                .identityDocument(foreignDoc().build());
    }

    private static IdentityDocument.IdentityDocumentBuilder residentDoc() {
        return IdentityDocument.builder()
                .type(DocType.PASSPORT)
                .code("1234")
                .number("123456")
                .issueDate(today().minusYears(1))
                .startDate(today().minusYears(1).plusDays(1))
                .expireDate(today().plusYears(1));
    }

    private static IdentityDocument.IdentityDocumentBuilder foreignDoc() {
        return IdentityDocument.builder()
                .type(DocType.FOREIGN_PASSPORT)
                .code("A")
                .number("12345678")
                .issueDate(today().minusYears(1))
                .startDate(today().minusYears(1).plusDays(1))
                .expireDate(today().plusYears(1));
    }
}