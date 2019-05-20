package hu.martinmarkus.basichytools.persistence.daos;

import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.persistence.PersistenceMode;
import hu.martinmarkus.configmanagerlibrary.threading.ResultListener;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class UserDao extends Dao<User> implements IUserDao {
    public UserDao(String path) {
        super(User.class, path, PersistenceMode.YAML_FILE);
    }

    @Override
    public void select(String valueId, ResultListener<User> resultListener) {
        configReader.read(valueId, resultListener);
    }

    @Override
    public void insert(String valueId, User user) {
        configWriter.write(valueId, user);
    }

    @Override
    public void insert(String valueId, User value, ResultListener<Boolean> resultListener) {
        configWriter.write(valueId, value, resultListener);
    }

    @Override
    public void update(String valueId, User value) {
        configWriter.write(valueId, value);
    }

    @Override
    public void contains(User value, ResultListener<Boolean> resultListener) {
        configReader.read(value.getName(), user -> {
            if (user != null) {
                resultListener.getResultOnFinish(true);
            } else {
                resultListener.getResultOnFinish(false);
            }
        });
    }

    @Override
    public void insertAll(List<String> valueIds, List<User> values) {
        try {
            for (int i = 0; i < valueIds.size(); i++) {
                configWriter.write(valueIds.get(i), values.get(i));
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    @Override
    public void updateAll(List<String> valueIds, List<User> values) {
        this.insertAll(valueIds, values);
    }

    // The following method are not required to implement for this project version

    @Override
    public void selectAll(ResultListener<List<User>> resultListener) {
        // Could be dangerous on big amount of user data
       throw new NotImplementedException();
    }
}
