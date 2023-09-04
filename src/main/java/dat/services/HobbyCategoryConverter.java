package dat.services;

import dat.model.HobbyCategory;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class HobbyCategoryConverter implements AttributeConverter<HobbyCategory, String> {

    @Override
    public String convertToDatabaseColumn(HobbyCategory hobbyCategory) {
        return hobbyCategory.getName();
    }

    @Override
    public HobbyCategory convertToEntityAttribute(String s) {
        return HobbyCategory.valueOf(s);
    }
}