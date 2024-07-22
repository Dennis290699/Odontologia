package model;

import java.util.List;

public interface PacienteObserver {
    void update(List<Paciente> pacientes);
}
