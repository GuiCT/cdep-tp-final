package services;

import interfaces.ITask;

public class TaskTest implements ITask<String> {
    @Override
    public String execute() {
        return "Hello World!";
    }
}
