import java.util.List;

public class FileAccountManager implements AccountManager {

    @Override
    public void register(Account newAccount) throws AccountAlreadyExistsException {
        List<Account> accounts = FileService.getInstance().readFile();

        for (Account account : accounts) {
            if (account.getEmail().equals(newAccount.getEmail())) {
                throw new AccountAlreadyExistsException("Данный email уже зарегистрирован.");
            }
        }

        FileService.getInstance().writeInFile(newAccount + ";\n", true);
        System.out.println("Регистрация выполнена успешно!");
    }

    @Override
    public Account login(String email, String password) throws AccountBlockedException, WrongCredentialsException {
        List<Account> accounts = FileService.getInstance().readFile();

        for (Account account : accounts) {
            if (account.getEmail().equals(email) && account.getPassword().equals(password)) {
                if (account.getBlocked()) {
                    throw new AccountBlockedException("Аккаунт заблокирован.");
                } else if (FailedLoginCounter.getCounter(email) >= 5) {
                    System.out.println("Превышено количество авторизаций. Аккаунт заблокирован.");
                    this.removeAccount(account.getEmail(), account.getPassword());
                    FileService.getInstance().writeInFile(new Account(account.getFullName(),
                            account.getBirthday(), account.getEmail(), account.getPassword(),
                            true) + ";\n", true);
                    return null;
                }
                return account;

            } else if (account.getEmail().equals(email)) {
                FailedLoginCounter.getInstance(email);
                throw new WrongCredentialsException("Данные введены неверно.");
            }
        }
        throw new WrongCredentialsException("Данные введены неверно.");
    }

    @Override
    public void removeAccount(String email, String password) throws WrongCredentialsException {
        List<Account> accounts = FileService.getInstance().readFile();
        for (Account account : accounts) {

            if (account.getEmail().equals(email) && account.getPassword().equals(password)) {
                accounts.remove(account);
                FileService.getInstance().writeInFile("", false);

                for (Account updatedAccount : accounts) {
                    FileService.getInstance().writeInFile(updatedAccount + ";\n", true);
                }
                return;
            }
        }
        throw new WrongCredentialsException("Данные введены неверно.");
    }
}

