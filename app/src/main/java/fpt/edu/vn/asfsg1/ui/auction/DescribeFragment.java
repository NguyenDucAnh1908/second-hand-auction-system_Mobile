package fpt.edu.vn.asfsg1.ui.auction;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fpt.edu.vn.asfsg1.R;
import fpt.edu.vn.asfsg1.databinding.FragmentDescribeBinding;
import fpt.edu.vn.asfsg1.databinding.FragmentHomeBinding;

public class DescribeFragment extends Fragment {
    FragmentDescribeBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_describe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView descTxt = view.findViewById(R.id.tvDescription);

        // Lấy nội dung HTML từ arguments
        String htmlDescription = getArguments() != null ? getArguments().getString("description", "") : "";

        // Chuyển đổi HTML thành Spanned
        Spanned formattedDescription;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            formattedDescription = Html.fromHtml(htmlDescription, Html.FROM_HTML_MODE_LEGACY);
        } else {
            formattedDescription = Html.fromHtml(htmlDescription);
        }

        // Gán nội dung định dạng vào TextView
        descTxt.setText(formattedDescription);
        descTxt.setMovementMethod(android.text.method.LinkMovementMethod.getInstance()); // Kích hoạt các liên kết
    }
}