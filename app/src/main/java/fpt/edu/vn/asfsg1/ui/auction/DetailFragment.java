package fpt.edu.vn.asfsg1.ui.auction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fpt.edu.vn.asfsg1.R;
import fpt.edu.vn.asfsg1.databinding.FragmentDetailBinding;

public class DetailFragment extends Fragment {
    FragmentDetailBinding binding;
    TextView percent, type, color, weight, dimension, original, manufacture_date, material;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        percent = view.findViewById(R.id.tvPercent);
        percent.setText(getArguments().getString("percent",""));

        type = view.findViewById(R.id.tvType);
        type.setText(getArguments().getString("type",""));

        color = view.findViewById(R.id.tvColor);
        color.setText(getArguments().getString("color",""));

        weight = view.findViewById(R.id.tvWeight);
        weight.setText(getArguments().getString("weight",""));

        dimension = view.findViewById(R.id.tvDimension);
        dimension.setText(getArguments().getString("dimension",""));

        original = view.findViewById(R.id.tvOrigin);
        original.setText(getArguments().getString("original",""));

        manufacture_date = view.findViewById(R.id.tvManufactureDate);
        manufacture_date.setText(getArguments().getString("manufacture_date",""));

        material = view.findViewById(R.id.tvMaterial);
        material.setText(getArguments().getString("material",""));
    }
}
