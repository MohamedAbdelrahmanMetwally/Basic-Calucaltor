package com.example.basiccalculator.Main.ui;
import java.util.Stack;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.basiccalculator.Main.utils.Calculator;
import com.example.basiccalculator.Main.utils.MainViewModelFactory;
import com.example.basiccalculator.Main.viewmodel.MainViewModel;
import com.example.basiccalculator.R;
import com.example.basiccalculator.databinding.ActivityMainBinding;
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MainViewModelFactory factory = new MainViewModelFactory(getApplication());
        viewModel = factory.create(MainViewModel.class);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @SuppressLint("NonConstantResourceId")
    public void action(View v) {
        viewModel.getText(v.getId(), binding.txtView.getText().toString()).observe(this, text -> {
            binding.txtView.setText(text);
        });
    }
}