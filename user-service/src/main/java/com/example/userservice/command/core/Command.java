package com.example.userservice.command.core;

public interface Command<ArgumentT, ResultT> {
    ResultT execute(ArgumentT argumentT);
}
