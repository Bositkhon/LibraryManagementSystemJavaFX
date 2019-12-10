package interfaces;

import java.util.List;
import java.util.Optional;

public interface DataAccessInterface<T> {

    T getByID(int id);

    List <T> getAll();

    boolean save();

    boolean delete();

}
