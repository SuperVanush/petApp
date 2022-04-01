package NewCalculator;

import com.example.demo.dao.Storage;
import com.example.demo.exception.UserListException;

import java.util.ArrayList;
import java.util.List;


public class CalculatorStorage implements Storage<Integer> {
    private final List<Integer> listResult = new ArrayList<>();

    @Override
    public int add(Integer result) {
        listResult.add(result);
        int lastResult = listResult.get(listResult.size() - 1);
        return lastResult;
    }

    @Override
    public Integer findById(int id) throws UserListException {
        return null;
    }

    @Override
    public void remove(int id) throws UserListException {
    }

    @Override
    public void printAll() {
        listResult.forEach(System.out::println);
    }

    @Override
    public List<Integer> getListOfElements() {
        return listResult;
    }
}
