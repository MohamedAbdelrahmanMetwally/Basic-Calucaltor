package com.example.basiccalculator.Main.viewmodel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.basiccalculator.Main.utils.Calculator;
import com.example.basiccalculator.R;
public class MainViewModel extends ViewModel {
    private MutableLiveData<String> text = new MutableLiveData<>();
    public LiveData<String> getText(int id, String currentText)  {
        if (id == R.id.btn0) text.setValue(currentText+"0");
        else if (id == R.id.btn1) text.setValue(currentText+"1");
        else if (id == R.id.btn2) text.setValue(currentText+"2");
        else if (id == R.id.btn3) text.setValue(currentText+"3");
        else if (id == R.id.btn4) text.setValue(currentText+"4");
        else if (id == R.id.btn5) text.setValue(currentText+"5");
        else if (id == R.id.btn6) text.setValue(currentText+"6");
        else if (id == R.id.btn7) text.setValue(currentText+"7");
        else if (id == R.id.btn8) text.setValue(currentText+"8");
        else if (id == R.id.btn9) text.setValue(currentText+"9");
        else if (id == R.id.btnPlus) text.setValue(currentText+" + ");
        else if (id == R.id.btnMuliply) text.setValue(currentText+" * ");
        else if (id == R.id.btnDivision) text.setValue(currentText+" / ");
        else if (id == R.id.btnMinus) {
            if (currentText.isEmpty() || currentText.endsWith("(")) {
                text.setValue(currentText+"-");
            } else if ("+-*/".contains(currentText.substring(currentText.length() - 1))) {
                if (!currentText.endsWith("-")) {
                    text.setValue(currentText+"-");
                }
            } else {
                text.setValue(currentText+" - ");
            }
        }
        else if (id == R.id.btnEqual) {
            Calculator c = new Calculator();
            if (!currentText.isEmpty()) {
                text.setValue("" + c.evaluateExpression(currentText));
            }
        }
        else if (id == R.id.btnBack) {
            if (!currentText.isEmpty()) {
                text.setValue(currentText.substring(0, currentText.length() - 1));
            }
        }
        else {
            text.setValue("");
        }
        return text;
    }
}