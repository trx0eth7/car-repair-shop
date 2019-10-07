package com.trx0eth7.projects.view.components.validators;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.RegexpValidator;

public class PhoneValidator implements Validator {
    public static final String REGEXP_PHONE_RU = "^((\\+7|7|8)+([0-9]){10})$";
    public static final String REGEXP_PHONE_US = "1?\\W*([2-9][0-8][0-9])\\W*([2-9][0-9]{2})\\W*([0-9]{4})(\\se?x?t?(\\d*))?";
    private CountryCode countryCode;
    private String errorMessage;

    public PhoneValidator(String errorMessage, CountryCode countryCode) {
        this.errorMessage = errorMessage;
        this.countryCode = countryCode;
    }

    @Override
    public void validate(Object o) throws InvalidValueException {
        RegexpValidator regexpValidator;

        switch (countryCode) {
            case US:
                regexpValidator = new RegexpValidator(REGEXP_PHONE_US, errorMessage);
                break;
            default:
                regexpValidator = new RegexpValidator(REGEXP_PHONE_RU, errorMessage);
        }

        regexpValidator.validate(o);
    }
}
