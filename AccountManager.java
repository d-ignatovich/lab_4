public interface AccountManager {

    void register(Account newAccount) throws AccountAlreadyExistsException;

    Account login(String email, String password) throws AccountBlockedException, WrongCredentialsException;

    void removeAccount(String email, String password) throws WrongCredentialsException;

}