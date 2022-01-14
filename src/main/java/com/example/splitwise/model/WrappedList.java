package com.example.splitwise.model;

import java.io.Serializable;
import java.util.List;

public interface WrappedList<T> extends Serializable {
       List<T> getWrappedItems();
}
