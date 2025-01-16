package fpt.edu.vn.asfsg1.ui.wallet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import fpt.edu.vn.asfsg1.R;

public class DepositFragment extends Fragment {
    private Spinner paymentMethodSpinner;
    private EditText amountInput, noteInput;
    private TextView depositButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deposit, container, false);

        // Khởi tạo các thành phần giao diện
        paymentMethodSpinner = view.findViewById(R.id.paymentMethodSpinner);
        amountInput = view.findViewById(R.id.amountInput);
        noteInput = view.findViewById(R.id.noteInput);
        depositButton = view.findViewById(R.id.depositButton);

        // Cài đặt danh sách phương thức thanh toán
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.payment_methods,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentMethodSpinner.setAdapter(adapter);

        // Sự kiện click vào nút nạp tiền
        depositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String paymentMethod = paymentMethodSpinner.getSelectedItem().toString();
                String amount = amountInput.getText().toString().trim();
                String note = noteInput.getText().toString().trim();

                if (amount.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập số tiền!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Giả lập gọi API nạp tiền
                Toast.makeText(getContext(), "Nạp tiền thành công!", Toast.LENGTH_SHORT).show();

                // Reset form
                amountInput.setText("");
                noteInput.setText("");
                paymentMethodSpinner.setSelection(0);
            }
        });

        return view;
    }
}