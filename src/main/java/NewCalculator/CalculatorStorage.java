package NewCalculator;

import com.example.demo.dao.Storage;
import com.example.demo.exception.UserListException;

import java.util.List;


public class CalculatorStorage implements Storage <List <Integer>> {

    @Override
    public int add(List<Integer> integers) {
        return 0;
    }

    @Override
    public List<Integer> findById(int id) throws UserListException {
        return null;
    }

    @Override
    public void remove(int id) throws UserListException {

    }

    @Override
    public void printAll() {

    }

    @Override
    public List<List<Integer>> getListOfElements() {
        return null;
    }
}
