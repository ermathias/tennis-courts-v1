import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GuestMapper {
    List<GuestDTO> map(List<Guest> source);

    GuestDTO map(Guest source);

    @InheritInverseConfiguration
    Guest map(GuestDTO source);

    List<GuestDTO> map(List<Guest> source);
}