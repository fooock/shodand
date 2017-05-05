package com.fooock.shodand.cli;

import com.fooock.shodand.data.DefaultValidationRepository;
import com.fooock.shodand.domain.ApiKey;
import com.fooock.shodand.domain.interactor.ValidateApiKey;
import com.fooock.shodand.domain.model.Account;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import io.reactivex.observers.DisposableObserver;

/**
 *
 */
public class Main {

    private static final String VERSION = "0.1.0";
    private static final String HEADER =
            " __ _               _                 _        _ _ \n" +
                    "/ _\\ |__   ___   __| | __ _ _ __   __| |   ___| (_)\n" +
                    "\\ \\| '_ \\ / _ \\ / _` |/ _` | '_ \\ / _` |  / __| | |\n" +
                    "_\\ \\ | | | (_) | (_| | (_| | | | | (_| | | (__| | |\n" +
                    "\\__/_| |_|\\___/ \\__,_|\\__,_|_| |_|\\__,_|  \\___|_|_|\n" +
                    "                                                   ";

    public static void main(String[] args) {
        header();
        // check program arguments
        if (args.length == 0) {
            help();
            return;
        }
        // read api key
        final String configurationFile = args[0];
        final String apiKey = readApiKey(configurationFile);
        if (apiKey.isEmpty()) {
            System.out.println("[-] Program can't start without API key");
            return;
        }
        // try to validate it
        validateApiKey(apiKey);
    }

    /**
     * Read the configuration file to get the user API key
     *
     * @param file Configuration file name
     * @return User Shodan API key
     */
    private static String readApiKey(String file) {
        System.out.println("[+] Read API key from " + file);
        File confFile = new File(file);
        if (!confFile.exists()) {
            System.out.println("[-] Configuration file [" + file + "] not exists");
            return "";
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(confFile));
            return bufferedReader.readLine();
        } catch (FileNotFoundException e) {
            System.out.println("[-] Error reading configuration file");
        } catch (IOException e) {
            System.out.println("[-] Unexpected error! \n" + e);
        }
        return "";
    }

    /**
     * Try to validate the API key and if success, start the application
     *
     * @param apiKey User Shodan API key
     */
    private static void validateApiKey(String apiKey) {
        ValidateApiKey validateApiKey = new ValidateApiKey(
                DefaultValidationRepository.getInstance(DefaultAccountSource.getInstance()),
                DefaultMainThread.getInstance(),
                DefaultThreadExecutor.getInstance());
        System.out.println("[+] Trying to validate API key...");
        validateApiKey.execute(new DisposableObserver<Account>() {
            @Override
            public void onNext(Account account) {
                System.out.println("[+] API key is valid!");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("[-] Error validating API key: "
                        + e.getMessage());
                System.exit(0);
            }

            @Override
            public void onComplete() {
                System.out.println("[+] Starting application");
            }
        }, new ApiKey(apiKey));
    }

    /**
     * Show the application usage
     */
    private static void help() {
        System.out.println("Usage: java -jar shodand-cli.jar <config-file>");
        System.out.println("\tThe configuration file contains the user Shodan API key");
    }

    /**
     * Show the program header
     */
    private static void header() {
        System.out.println(HEADER);
        System.out.println("\t|- by: newhouse\t-| |- version: " + VERSION + " -|\n");
    }
}
