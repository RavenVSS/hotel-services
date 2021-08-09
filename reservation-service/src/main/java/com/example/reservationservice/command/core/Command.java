package com.example.reservationservice.command.core;

public interface Command<ArgumentT, ResultT> {
    ResultT execute(ArgumentT argumentT);
}
