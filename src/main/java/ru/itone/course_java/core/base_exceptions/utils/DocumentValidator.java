package ru.itone.course_java.core.base_exceptions.utils;

import ru.itone.course_java.core.base_exceptions.BaseExceptions;
import ru.itone.course_java.core.base_exceptions.exceptions.DocumentValidationException;
import ru.itone.course_java.core.base_exceptions.model.DocType;
import ru.itone.course_java.core.base_exceptions.model.IdentityDocument;


public class DocumentValidator {


    public void validate(IdentityDocument document, boolean isresident) {
        if (document==null) {
            throw new DocumentValidationException("Требуется паспорт");
        }
        validateType(document);
        validateResident(document.getType(), isresident);
        validateCode(document.getCode(),isresident);
        validateNumber(document.getNumber(),isresident);
        validateData(document);
    }

    private void validateData(IdentityDocument identityDocument) {
        var startDate= identityDocument.getStartDate();
        var expireDate = identityDocument.getExpireDate();
        var issueDate=identityDocument.getIssueDate();

        var today= BaseExceptions.today();

        if (issueDate == null || startDate == null || expireDate == null) {
            throw new DocumentValidationException("Все даты паспорта должны быть указаны");
        }


        if (issueDate.isAfter(startDate)) {
            throw new DocumentValidationException(
                    String.format("Дата выдачи паспорта %s не может быть позже даты начала действия паспорта %s",
                            issueDate, startDate)
            );
        }
        if (issueDate.isAfter(today) || startDate.isAfter(today)) {
            throw new DocumentValidationException(
                    String.format("Дата выдачи %s и начала действия паспорта %s не должна быть в будущем",
                            issueDate, startDate)
            );
        }
        if (expireDate.isBefore(today)) {
            throw new DocumentValidationException(
                    String.format("Паспорт должен быть действительным, срок действия паспорта подошёл к концу %s",
                            expireDate)
            );
        }

    }

    private void validateNumber(String number, boolean isResident) {
        if (isResident) {
            if (!number.matches("\\d{6}")) {
                throw new DocumentValidationException(
                        String.format("Номер паспорта для граждан должен состоять из 6 цифр, во внутреннем паспорте указан номер: %s", number)
                );
            }
        } else {
            if (!number.matches("\\d{8}|\\d{10}")) {
                throw new DocumentValidationException(
                        String.format("Номер паспорта для туристов должен состоять из 8 или 10 цифр, в зарубежном паспорте указан номер: %s", number)
                );
            }
        }
    }

    private void validateCode(String code, boolean isResident) {
        if (isResident) {
            if (!code.matches("\\d{4}")) {
                throw new DocumentValidationException(
                        String.format("Код паспорта для граждан должен состоять из 4 цифр, во внутреннем паспорте указан код: %s", code)
                );
            }
        } else {
            if (!code.matches("[ABC]")) {
                throw new DocumentValidationException(
                        String.format("Код паспорта для туристов должен быть A,B,C, в зарубежном паспорте указан код: %s", code)
                );
            }
        }
    }

    private void validateResident(DocType type, boolean isResident) {
        if (isResident&&type!=DocType.PASSPORT) {
            throw new DocumentValidationException(
                    String.format("Гражданство и тип паспорта должны совпадать, указано гражданин: Да, тип паспорта: %s",
                            "Зарубежный паспорт"
                    )
            );
        }
        if (!isResident&&type!= DocType.FOREIGN_PASSPORT) {
            throw new DocumentValidationException(
                    String.format("Гражданство и тип паспорта должны совпадать, указано гражданин: Нет, тип паспорта: %s",
                            "Внутренний паспорт"
                    )
            );
        }
    }

    private void validateType(IdentityDocument document) {
        if (document.getType() == null) {
            throw new DocumentValidationException("Тип паспорта должен быть указан");
        }
    }
}
