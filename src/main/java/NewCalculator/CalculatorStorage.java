package NewCalculator;

import com.example.demo.dao.Storage;
import com.example.demo.exception.UserListException;
import com.example.demo.model.Bill;

import java.util.ArrayList;
import java.util.List;


public class CalculatorStorage implements Storage<Integer> {
    private final List<Integer> listResult = new ArrayList<>();

    @Override
    public int add(Integer result) {
        listResult.add(result);
        int idLastResult = listResult.lastIndexOf(result);
        return idLastResult;
    }

    @Override
    public Integer findById(int idLastResult) {
        int resultInList = listResult.get(idLastResult);
        return resultInList;
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
