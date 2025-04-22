package com.example.app.cli;

import com.example.app.models.BaseEntity;
import java.util.List;

public interface DataUI<T extends BaseEntity> {
    default public void printData(T data) {}
    default public void printData(List<T> data, int from, int to) {}
}