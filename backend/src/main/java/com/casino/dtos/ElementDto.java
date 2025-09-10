package co.edu.uptc.dtos;

import co.edu.uptc.models.ElementModel;
import co.edu.uptc.models.ElementModel.UnitOfWeight;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ElementDto {
    private int id;
    private String name; // longitud mínima 10 caracteres
    private String description; // longitud mínima 100 caracteres
    private UnitOfWeight unitOfWeight;
    private double price; // valor mayor a 0

    public static ElementDto toPersonDto(ElementModel element) {
        ElementDto elementDto = new ElementDto();
        elementDto.setId(element.getId());
        elementDto.setName(element.getName());
        elementDto.setDescription(element.getDescription());
        elementDto.setUnitOfWeight(element.getUnitOfWeight());
        elementDto.setPrice(element.getPrice());

        return elementDto;
    }
}
