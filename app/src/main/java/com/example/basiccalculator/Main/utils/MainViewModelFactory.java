package com.example.basiccalculator.Main.utils;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.basiccalculator.Main.viewmodel.MainViewModel;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    public MainViewModelFactory(Application application) {
        this.application = application;
    }
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
