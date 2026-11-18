package be.sos.service;

import be.sos.model.Sandwich;
import be.sos.model.TypeSandwich;
import be.sos.repository.SandwichFileRepository;
import be.sos.repository.SandwichRepository;
import be.sos.repository.TypeSandwichFileRepository;
import be.sos.repository.TypeSandwichRepository;

import java.util.List;

public class TypeSandwichService {
    private static final TypeSandwichRepository typeSandwichRepository = TypeSandwichFileRepository.getInstance();

    public static List<TypeSandwich> getAllTypeSandwiches() {
        return typeSandwichRepository.findAll();
    }
}
