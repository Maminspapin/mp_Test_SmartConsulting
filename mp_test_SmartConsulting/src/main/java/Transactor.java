import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

public class Transactor {

    /*
    Переменные
    */

    private static Integer transactionCount = 50;
    private Account[] accounts;
    private static int countProc = Runtime.getRuntime().availableProcessors();

    /*
    Вывести сообщение
    */

    private static void showMessage(String str) {
        System.out.println(str);
    }

    /*
    Получить данные
    */

    private static float getConsolInfo() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextFloat();
    }

    /*
    Ожидание главным потоком окончания транзакций
    */

    private static void waitAllTransactionsFinished() {

        Thread mainThread = Thread.currentThread();

        while (transactionCount>0) {
            showMessage("Главный поток ожидает...");
            try {
                mainThread.join(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        showMessage("Программа завершена.");
    }

    /*
    Запустить потоки
    */

    private static ResultViewer startThreads(Account[] accounts) {

        final Logger transactionLogger = Logger.getLogger(Transactor.class);

        List<Transaction> transactions = new ArrayList<>();

        Runnable task = () -> { // вошли в метод (потоки, не блокируются)
            try {
                while (transactionCount > 0) {
                    Thread currentThread = Thread.currentThread();

                    int sleepTime = 1 + (int) (Math.random() * 2);
                    TimeUnit.SECONDS.sleep(sleepTime); // поток заснул (1-2 сек.)...

                    synchronized (transactionCount) { // здесь доступ до общего ресурса только для одного потока (первого вошедшего в блок), выполняет код ниже
                        if (transactionCount > 0) {

                            int randomIdAccountFrom = (int) (Math.random() * accounts.length);
                            transactionLogger.info("Определен номер счета, с которого будут списаны средства: " + randomIdAccountFrom);
                            transactionLogger.info("На счете " + randomIdAccountFrom + " средств " + accounts[randomIdAccountFrom].getMoney());

                            int randomIdAccountTo = (int) (Math.random() * accounts.length);
                            transactionLogger.info("Определен номер счета, на который будут зачислены средства : " + randomIdAccountTo);
                            transactionLogger.info("На счете " + randomIdAccountTo + " средств " + accounts[randomIdAccountTo].getMoney());

                            float randomAmount = (float) Math.random() * accounts[randomIdAccountFrom].getMoney();
                            transactionLogger.info("Определена сумма средств для перевода : " + randomAmount);

                            showMessage("Работает поток: " + currentThread);

                            showMessage("Осталось транзакций: " + transactionCount);
                            accounts[randomIdAccountFrom].setMoney(accounts[randomIdAccountFrom].getMoney() - randomAmount);
                            transactionLogger.info("На счете " + randomIdAccountFrom + " средств после списания: " + accounts[randomIdAccountFrom].getMoney());

                            accounts[randomIdAccountTo].setMoney(accounts[randomIdAccountTo].getMoney() + randomAmount);
                            transactionLogger.info("На счете " + randomIdAccountTo + " средств после зачисления: " + accounts[randomIdAccountTo].getMoney());

                            transactions.add(new Transaction(new Date(), randomIdAccountFrom, randomIdAccountTo, randomAmount));

                            transactionCount--;
                            transactionLogger.info("Транзакция выполнена.\n");
                            showMessage("Поток " + currentThread.getName() + " отработал.");
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        for (int i = 1; i <= countProc; i++) {
            Thread newThread = new Thread(task,"Maker " + i);
            newThread.start();
            showMessage("Запущен поток: " + newThread.getName());
        }

        return new ResultViewer(accounts, transactions);
    }

    /*
    Выполнить транзакцию
    */

    public void transact() {

        showMessage("Введите колличество счетов");
        int accountCount = (int) getConsolInfo();
        if (accountCount < 1) {
            showMessage("Данные некорректны... Выход из программы.");
            return;
        }

        accounts = new Account[accountCount];

        showMessage("Введите начальную сумму на счетах");
        float accountDefaultAmount = getConsolInfo();
        if (accountDefaultAmount > Float.MAX_VALUE || accountDefaultAmount < 0) {
            showMessage("Данные некорректны... Выход из программы.");
            return;
        }

        showMessage("Создано счетов: " + accountCount + ", сумма по-умолчанию: " + accountDefaultAmount + "\n");

        for (int i = 0; i < accountCount; i++) {
            accounts[i] = new Account(String.valueOf(i), accountDefaultAmount);
            showMessage("Счет: " + accounts[i].getId() + ", сумма: " + accounts[i].getMoney());
        }

        ResultViewer result = startThreads(accounts);

        waitAllTransactionsFinished();

        result.showResult();
    }
}

