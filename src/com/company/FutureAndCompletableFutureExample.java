package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class FutureAndCompletableFutureExample {


    private static List<Employee> employeeList = new ArrayList<>();

    static {
        employeeList.add(new Employee("John", "se", 4, "Zurich", 300));
        employeeList.add(new Employee("Ace", "ase", 2, "Amsterdam", 200));
        employeeList.add(new Employee("Keith", "ita", 5, "Barbados", 400));
        employeeList.add(new Employee("Mohammad", "ta", 14, "Zurich", 500));
        employeeList.add(new Employee("Austin", "md", 22, "Florida", 700));
        employeeList.add(new Employee("Stephen", "ceo", 25, "Citadel", 950));
    }

    //https://www.callicoder.com/java-8-completablefuture-tutorial/#:~:text=Future%20vs%20CompletableFuture,result%20of%20an%20asynchronous%20computation.
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        //You can use CompletableFuture.complete() method to manually complete a Future
        //All the clients waiting for this Future will get the specified result.
        // And, Subsequent calls to completableFuture.complete() will be ignored.

        completableFuture.complete("Future's Result");


        //running async task that doesn't return anything,we use static method runAsync, and we pass runnable inside it

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //separate thread coming from  threadForkJoinPool.commonPool()
            System.out.println("I will run in separate thread than main thread" + Thread.currentThread().getName());
        });

        Stream.generate(() -> Math.random() * 200).limit(5).forEach(i -> {
            System.out.println(i + " " + Thread.currentThread().getName());
        });
        // Block and wait for the future to complete
        voidCompletableFuture.get();

        Stream.iterate(0, i -> i + 2).limit(5).forEach(i -> {
            System.out.println(i + " " + Thread.currentThread().getName());
        });


        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Result of asynchronous computation" + Thread.currentThread().getName();
        });

        String result = supplyAsync.get();
        System.out.println(result);


        //create a completablefuture
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ;
            return "Hello John";
        }).thenApply(r -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return r + " How are you John";
        });

        System.out.println(stringCompletableFuture.get());

        //If you don’t want to return anything from your callback function and just want to run some piece of code
        // after the completion of the Future, then you can use thenAccept() and thenRun() methods.
        // These methods are consumers and are often used as the last callback in the callback chain.

        CompletableFuture<Void> voidCompletableFuture1 = CompletableFuture.supplyAsync(() -> {
            return "John";
        }).thenAccept(r -> System.out.println("Bye " + r));


        //While thenAccept() has access to the result of the CompletableFuture on which it is attached,
        // thenRun() doesn’t even have access to the Future’s result. It takes a Runnable and returns CompletableFuture<Void>

        CompletableFuture<Void> no_problem_enjoy = CompletableFuture.supplyAsync(() -> {
            return "Going To My Place";
        }).thenRun(() -> System.out.println("No Problem Enjoy"));

        //System.out.println(no_problem_enjoy.get());//gives null


        //// thenApply() variants
        //<U> CompletableFuture<U> thenApply(Function<? super T,? extends U> fn)
        //<U> CompletableFuture<U> thenApplyAsync(Function<? super T,? extends U> fn)
        //<U> CompletableFuture<U> thenApplyAsync(Function<? super T,? extends U> fn, Executor executor)
        //These async callback variations help you further parallelize your computations by executing the callback tasks in a separate thread.

        //Combining two CompletableFutures together
        CompletableFuture<CompletableFuture<Integer>> austin = getEmployeeDetails("Austin").thenApply(user -> {
            return getCreditRating(user);
        });

        System.out.println(austin.get().get());

        CompletableFuture<Integer> austin1 = getEmployeeDetails("Austin").thenCompose(user -> getCreditRating(user));
        System.out.println(austin1.get());



        //combine two independent futures using thenCombine
//retrieving weight

        System.out.println("retrieving weight");
        CompletableFuture<Double> weightInKgFuture=CompletableFuture.supplyAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 65.0;
        });

        //retrieving height
        System.out.println("retrieving height");
        CompletableFuture<Double> heightInFuture=CompletableFuture.supplyAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 177.9;
        });


        System.out.println("Calculating BMI");

        CompletableFuture<Double> combinedFuture = weightInKgFuture.thenCombine(heightInFuture, (weight, height) -> {
            Double heightinMeter = height / 100;
            return weight / (heightinMeter * heightinMeter);
        });

        System.out.println(combinedFuture.get());

    }

    private static CompletableFuture<Integer> getCreditRating(Employee user) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 5;
        });
    }

    public static CompletableFuture<Employee> getEmployeeDetails(String uid) throws InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return employeeList.stream().filter(e -> e.getName().equals(uid)).findFirst().get();

        });
    }


}