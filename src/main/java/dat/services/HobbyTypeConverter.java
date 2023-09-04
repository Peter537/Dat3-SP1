package dat.services;

import dat.model.HobbyType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class HobbyTypeConverter implements AttributeConverter<HobbyType, String> {

    @Override
    public String convertToDatabaseColumn(HobbyType hobbyType) {
        return hobbyType.getName();
    }

    @Override
    public HobbyType convertToEntityAttribute(String s) {
        return HobbyType.valueOf(s);
    }
}