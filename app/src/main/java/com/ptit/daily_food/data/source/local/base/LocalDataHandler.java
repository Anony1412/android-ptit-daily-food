package com.ptit.daily_food.data.source.local.base;

public interface LocalDataHandler<P, T> {

    T execute(P params) throws Exception;
}
