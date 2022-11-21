

public class Main {
    public static void main(String[] args) {
        FileAccountManager file = new FileAccountManager();

        //Регистрация нового аккаунта.
        Account account1 = new Account("Диана", "25.02.2002", "ignatovich.di@ya.ru", "123456");
        try {
            file.register(account1);
        } catch (AccountAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(FileService.getInstance().readFile());

        //Проверка метода FailedLoginCounter (такой почты нет, метод не должен вести подсчет).
        for (int i = 0; i < 5; i++) {
            try {
                System.out.println(file.login("di.@mail.ru", "1234"));

            } catch (AccountBlockedException | WrongCredentialsException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(FailedLoginCounter.getCounter("di.@mail.ru"));

        //Попытка входа в аккаунт.
        try {
            System.out.println(file.login("ignatovich.di@ya.ru", "123456"));
            System.out.println(FailedLoginCounter.getCounter("ignatovich.di@ya.ru"));
        } catch (AccountBlockedException | WrongCredentialsException e) {
            System.out.println(e.getMessage());
        }

        // Проверка метода FailedLoginCounter.
        for (int i = 0; i < 5; i++) {
            try {
                System.out.println(file.login("ignatovich.di@ya.ru", "1234"));

            } catch (AccountBlockedException | WrongCredentialsException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(FailedLoginCounter.getCounter("ignatovich.di@ya.ru"));

        //Попытка входа в заблокиированный аккаунт.
        try {
            System.out.println(file.login("ignatovich.di@ya.ru", "123456"));
        } catch (AccountBlockedException | WrongCredentialsException e) {
            System.out.println(e.getMessage());
        }
    }
}